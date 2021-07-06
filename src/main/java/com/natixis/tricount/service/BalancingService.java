package com.natixis.tricount.service;

import com.natixis.tricount.dto.AmountDistribution;
import com.natixis.tricount.dto.Balancing;
import com.natixis.tricount.entity.Expense;
import com.natixis.tricount.entity.ExpenseList;
import com.natixis.tricount.entity.Participant;
import com.natixis.tricount.repository.ExpenseListRepository;
import com.natixis.tricount.repository.ExpenseRepository;
import com.natixis.tricount.repository.ParticpantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BalancingService {

    @Autowired
    private ExpenseListRepository expenseListRepository;

    @Autowired
    private ParticpantRepository participantRepository;

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
            amountByPerson = expense.getAmount()/(expense.getParticipants().size()+1);
            Participant participant = participantRepository.getById(expense.getParticipantPayer().getId());
            participant.setBalance(participant.getBalance() + (amountExpense - amountByPerson));
            participantRepository.save(participant);
            for (Participant beneficiaire : expense.getParticipants() ) {
                participant = participantRepository.getById(beneficiaire.getId());
                participant.setBalance(participant.getBalance() - amountByPerson);
                participantRepository.save(participant);
            }

        }

    }

    public List<AmountDistribution> getAmountDistributionList(List<Balancing> balancingList) {
        List<AmountDistribution> amountDistributionList = new ArrayList<>();
        List<Balancing> positiveBalancingList = new ArrayList<>();
        List<Balancing> negativeBalancingList = new ArrayList<>();
        for (Balancing balancing : balancingList) {
            if (balancing.getAccountBalance() > 0) {
                positiveBalancingList.add(balancing);
            }
            if (balancing.getAccountBalance() < 0) {
                negativeBalancingList.add(balancing);
            }
        }
        Collections.sort(positiveBalancingList);

        Comparator<Balancing> comparator = new Comparator<Balancing>() {
            @Override
            public int compare(Balancing balancing1, Balancing balancing2) {
                return balancing1.getAccountBalance().intValue() - balancing2.getAccountBalance().intValue();
            }
        };

        Collections.sort(negativeBalancingList, comparator);

        for (Balancing balancing : positiveBalancingList) {
            float refund = balancing.getAccountBalance();
            while (refund > 0 && negativeBalancingList.size() > 0 ) {
                Balancing negativeBalancing = negativeBalancingList.get(0);
                refund = refund + negativeBalancing.getAccountBalance();
                amountDistributionList.add(new AmountDistribution(
                                                    negativeBalancing.getIdParticipant(),negativeBalancing.getFirstName(),negativeBalancing.getLastName(),
                                                    Math.abs(Math.abs(refund) - Math.abs(negativeBalancing.getAccountBalance())),
                                                    balancing.getIdParticipant(),balancing.getFirstName(),balancing.getLastName()));
                if (refund >= 0) {
                    negativeBalancingList.remove(0);
                } else {
                    negativeBalancing.setAccountBalance(Math.abs(refund) * -1);
                    negativeBalancingList.set(0,negativeBalancing);
                }

            }
        }

        return amountDistributionList;
    }

}
