package com.natixis.tricount.service;

import com.natixis.tricount.entity.Expense;
import com.natixis.tricount.entity.ExpenseList;
import com.natixis.tricount.entity.Participant;
import com.natixis.tricount.repository.ExpenseListRepository;
import com.natixis.tricount.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ExpensesOfListSrevice {

    @Autowired
    private ExpenseRepository expenseRepository;
    
    @Autowired
    private ExpenseListRepository expenseListRepository;

    public Optional<Expense> findById(Long id) { return expenseRepository.findById(id); }

	public void addExpenseOfList(Long idList, Expense expense) {
	 	ExpenseList expenseList = new ExpenseList();
    	if(idList != null ) {
    		Optional<ExpenseList> optionalExpenseList = expenseListRepository.findById(idList);
    		if (optionalExpenseList.isPresent()) 
    		{
    			expenseList = optionalExpenseList.get();
    			expense.setExpenseList(expenseList);
    	    	expenseRepository.save(expense);
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