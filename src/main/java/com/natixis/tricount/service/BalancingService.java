package com.natixis.tricount.service;

import com.natixis.tricount.dto.AmountDistribution;
import com.natixis.tricount.dto.Balancing;
import com.natixis.tricount.dto.EmailAddress;
import com.natixis.tricount.entity.Expense;
import com.natixis.tricount.entity.ExpenseList;
import com.natixis.tricount.entity.Participant;
import com.natixis.tricount.mail.Mail;
import com.natixis.tricount.repository.ExpenseListRepository;
import com.natixis.tricount.repository.ExpenseRepository;
import com.natixis.tricount.repository.ParticpantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

@Service
public class BalancingService {

    @Autowired
    private ExpenseListRepository expenseListRepository;

    @Autowired
    private ParticpantRepository participantRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private Mail mail;

    public List<Balancing> getBalacingPage(Long idList) {
        List<Expense> expenses = new ArrayList<>();
        List<Balancing> balancings = new ArrayList<>();
        if(idList != null ) {
            Optional<ExpenseList> optionalExpenseList = expenseListRepository.findById(idList);
            
            if (optionalExpenseList.isPresent())
            {
                expenses = optionalExpenseList.get().getExpenses();
                calculBalancing(expenses);
                List<Participant> participants = optionalExpenseList.get().getParticipants();
                for (Participant participant: participants) {
                    balancings.add(new Balancing(participant.getId(),participant.getFirstName(),participant.getName(),participant.getBalance()));
                }
            }
        }
        return balancings;
    }

    public void calculBalancing(List<Expense> expenses) {

        float amountExpense = 0;
        float amountByPerson = 0;

        for (Expense expense: expenses) {
            amountExpense = expense.getAmount();
            amountByPerson = expense.getAmount()/(expense.getParticipants().size());
            if (expense.getParticipantPayer() != null) {
                Participant participant = participantRepository.getById(expense.getParticipantPayer().getId());
                participant.setBalance(roundedAmount(participant.getBalance() + amountExpense));
                for (Participant beneficiaire : expense.getParticipants() ) {
                    participant = participantRepository.getById(beneficiaire.getId());
                    participant.setBalance(roundedAmount(participant.getBalance() - amountByPerson));
                }

            }


        }

    }

    public Float roundedAmount(Float amount) {
        DecimalFormatSymbols dfSymbols = new DecimalFormatSymbols();
        dfSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        df.setDecimalFormatSymbols(dfSymbols);
        String result = df.format(amount);
        return new Float(result);
    }

    public List<AmountDistribution> getAmountDistributionList(List<Balancing> balancingList) {
        List<AmountDistribution> amountDistributionList = new ArrayList<>();
        List<Balancing> ascendingBalancingList = new ArrayList<>();
        List<Balancing> descendingBalancingList = new ArrayList<>();
        for (Balancing balancing : balancingList) {
            ascendingBalancingList.add(new Balancing(balancing.getIdParticipant(),balancing.getFirstName(),balancing.getLastName(),balancing.getAccountBalance()));
            descendingBalancingList.add(new Balancing(balancing.getIdParticipant(),balancing.getFirstName(),balancing.getLastName(),balancing.getAccountBalance()));
        }

        Collections.sort(descendingBalancingList);

        Comparator<Balancing> comparator = new Comparator<Balancing>() {
            @Override
            public int compare(Balancing balancing1, Balancing balancing2) {
                return balancing1.getAccountBalance().intValue() - balancing2.getAccountBalance().intValue();
            }
        };

        Collections.sort(ascendingBalancingList, comparator);

        for (Balancing balancing : descendingBalancingList) {

            float refund = balancing.getAccountBalance();
            while (refund > 0 && ascendingBalancingList.size() > 0 ) {
                Balancing ascendingBalancing = ascendingBalancingList.get(0);
                refund = refund + ascendingBalancing.getAccountBalance();

                if (ascendingBalancing.getIdParticipant() != balancing.getIdParticipant()) {
                    // un participant ne pas se payer lui meme
                    Float amountDistribution = refund<0? Math.abs(Math.abs(refund) - Math.abs(ascendingBalancing.getAccountBalance())) : Math.abs(ascendingBalancing.getAccountBalance());
                    // on ecarte les virements ?? z??ro
                    if (roundedAmount(amountDistribution) != 0) {
                        amountDistributionList.add(new AmountDistribution(
                            ascendingBalancing.getIdParticipant(), ascendingBalancing.getFirstName(), ascendingBalancing.getLastName(),
                            amountDistribution, balancing.getIdParticipant(), balancing.getFirstName(), balancing.getLastName()));
                    }
                }

                if (refund >= 0) {
                    ascendingBalancingList.remove(0);
                } else {
                    ascendingBalancing.setAccountBalance(Math.abs(refund) * -1);
                    descendingBalancingList.set(0,ascendingBalancing);
                }

            }
        }


        return amountDistributionList;
    }

    public void startBalancing(List<AmountDistribution> amountDistributionList, Long idList, EmailAddress emailAddress) {
        ExpenseList expenseList = expenseListRepository.findById(idList).get();
        List<String> recapVirement = new ArrayList<>();

        for (AmountDistribution amountDistribution:amountDistributionList) {
            Expense expense = new Expense();
            expense.setName("EQUILIBRAGE - VIREMENT de " + amountDistribution.getFirstNamePayer() + " " + amountDistribution.getLastNamePayer()
                    + " vers " + amountDistribution.getFirstNameCollector() + " " + amountDistribution.getLastNameCollector()
            );
            expense.setAmount(amountDistribution.getAmountDistribution());
            expense.setExpenseList(expenseList);
            expense.setParticipantPayer(participantRepository.findById(amountDistribution.getIdPayer()).get());
            expense.addOneParticipantBeneficiary(participantRepository.findById(amountDistribution.getIdCollector()).get());
            expenseRepository.save(expense);

            recapVirement.add("- VIREMENT de " + amountDistribution.getFirstNamePayer() + " " + amountDistribution.getLastNamePayer()
                    + " vers " + amountDistribution.getFirstNameCollector() + " " + amountDistribution.getLastNameCollector()+" d'un montant de "
            + amountDistribution.getAmountDistribution() + "???.");

            amountDistribution.getIdCollector();
        }
        mail.sendEmail(emailAddress, expenseList, recapVirement);
    }
}
