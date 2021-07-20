package com.natixis.tricount.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.natixis.tricount.entity.Expense;
import com.natixis.tricount.entity.ExpenseList;
import com.natixis.tricount.entity.Participant;
import com.natixis.tricount.repository.ExpenseListRepository;
import com.natixis.tricount.repository.ExpenseRepository;


@Service
public class VisuUpdateExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    
    @Autowired
    private ExpenseListRepository expenseListRepository;

    public List<Participant> participantList(Long id_expenseList) {
        ExpenseList expenseList = expenseListRepository.findById(id_expenseList).get();
        return expenseList.getParticipants();
    }
    
    public long idExpenseList(Long id) {
    	return expenseRepository.findById(id).get().getExpenselist().getId();
    }
    
    public void addPayeurDepense(Expense expense, Participant participant, List<Participant> beneficiaireList) {
    	ExpenseList expenseList = new ExpenseList();
    	Expense  expense2 = expenseRepository.findById(expense.getId()).get();
        expense2.setName(expense.getName());
        expense2.setAmount(expense.getAmount());
        expense2.setParticipantPayer(participant); 
        if (beneficiaireList != null) {
        	for (int cptI = 0 ; cptI < beneficiaireList.size() ; cptI++) {
        		expense2.addOneParticipantBeneficiary(beneficiaireList.get(cptI));
        	}
        }
		Optional<ExpenseList> optionalExpenseList = expenseListRepository.findById(expense2.getExpenselist().getId());
		if (optionalExpenseList.isPresent()) 
		{
			expenseList = optionalExpenseList.get();
			expense2.setExpenseList(expenseList);
            expenseRepository.save(expense2);
		}
    }
    
	public Set<Participant> BeneficiaireListOfExpense(Long idExpense) {
	 	Set<Participant> beneficiaireList = new HashSet<>();
    	if(idExpense != null ) {
    		Optional<Expense> optionalExpense = expenseRepository.findById(idExpense);
    		if (optionalExpense.isPresent()) 
    		{
    			beneficiaireList = optionalExpense.get().getParticipants();
    		}
    	}
    	return beneficiaireList;
	}    
}