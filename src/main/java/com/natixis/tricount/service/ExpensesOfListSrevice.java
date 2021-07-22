package com.natixis.tricount.service;

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
public class ExpensesOfListSrevice {

    @Autowired
    private ExpenseRepository expenseRepository;
    
    @Autowired
    private ExpenseListRepository expenseListRepository;
    
    @Autowired
    private ParticpantRepository participantRepository;

    public Optional<Expense> findById(Long id) { return expenseRepository.findById(id); }

	public void addExpenseOfList(Long idList, Expense expense, Long idPayeur) {
	 	ExpenseList expenseList = new ExpenseList();
    	if(idList != null ) {
    		Optional<ExpenseList> optionalExpenseList = expenseListRepository.findById(idList);
    		if (optionalExpenseList.isPresent()) 
    		{
    			System.out.println("if");
    			expenseList = optionalExpenseList.get();
    			expense.setExpenseList(expenseList);
    			expense.setParticipantPayer(participantRepository.getById(idPayeur));
    	    	expenseRepository.save(expense);
    	    	
    			// Par defaut : participant beneficiaire valorisé par payeur
    			Expense expenseBeneficiaire = expenseRepository.save(expenseRepository.getById(expense.getId()));
    			Optional<Participant> optionalBeneficiaire = participantRepository.findById(participantRepository.getById(idPayeur).getId());
    			if (optionalBeneficiaire.isPresent()) {
    				Participant beneficiaire = new Participant();
    				beneficiaire = optionalBeneficiaire.get();
    				beneficiaire.getExpense().add(expenseBeneficiaire);
    				participantRepository.save(beneficiaire);
    			}
    			
    			//expense.addOneParticipantBeneficiary(ParticipantRepository.getById(idPayeur));
    	    	//expenseRepository.save(expense);
    		}
    	}
	}
	
	public void deleteExpenseList(Long idExpense) {
		Expense expense = expenseRepository.findById(idExpense).get();
//		expense.getParticipants().clear();
		expense.removeParticipants();
		expenseRepository.save(expense);
		if (idExpense !=null) {
			expenseRepository.deleteById(idExpense);
		}
	}
	
}