package com.natixis.tricount.controller;

import com.natixis.tricount.dto.Paginable;
import com.natixis.tricount.entity.Expense;
import com.natixis.tricount.entity.ExpenseList;
import com.natixis.tricount.entity.Participant;
import com.natixis.tricount.service.ExpenseListService;
import com.natixis.tricount.service.ExpensesOfListSrevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ExpensesController {

    @Autowired
    private ExpenseListService expenseListService;
    
    @Autowired
    private ExpensesOfListSrevice expensesOfListSrevice;

    @GetMapping("/lists/{idList}/expenses")
    public String getExpensesListPage(Model model, 
    					@PathVariable Long idList,
	 					@RequestParam("page") Optional<Integer> page,
	 					@RequestParam("size") Optional<Integer> size) {
	 					
    	System.out.println("expenses");
	 	int currentPage = page.orElse(1);
	 	int pageSize = size.orElse(8);
	 						
        if (idList != null) {
            Optional<ExpenseList> optionalExpenseList = expenseListService.findById(idList);
            ExpenseList expenseList = new ExpenseList();
            List<Participant> participants = new ArrayList<>();
            if (optionalExpenseList.isPresent()) {
                expenseList = optionalExpenseList.get();
                System.out.println(" Nom de la liste " + expenseList.getName());
                Page<Expense> expenses = expensesOfListSrevice.findExpenseByIdPaginated(expenseList.getId(), PageRequest.of(currentPage - 1, pageSize));
                System.out.println("dépenses : " + expenses.getSize());
                participants = optionalExpenseList.get().getParticipants();
                model.addAttribute("expenseList",expenseList);
                model.addAttribute("expenses",expenses);
                model.addAttribute("participants",participants);
                 
        		int totalPages = expenses.getTotalPages();
            	System.out.println("Total pages : " + totalPages);
        		
        			Paginable paginable = new Paginable(currentPage,pageSize,totalPages);
        			model.addAttribute("paginable2", paginable);

        		
                return "expensesOfList";
            }
        }
		
		Paginable paginable = new Paginable(currentPage,pageSize,1);
		model.addAttribute("paginable", paginable);
        return "redirect:/lists";
    }
    
    @PostMapping("/lists/{idList}/expense")
    public String addExpenseOfList(@PathVariable Long idList,@ModelAttribute Expense expense, Long idPayeur) {
    	if (idList != null) {
    		expensesOfListSrevice.addExpenseOfList(idList, expense, idPayeur);	
    		return "redirect:/expenses/" + expense.getId();
    	}
    	return "redirect:/lists/" + idList + "/expenses";
    }

    
    @PostMapping("/expenses/delete/{idList}/{idExpense}")
    public String deleteExpenseOfList(@PathVariable Long idExpense, @PathVariable Long idList) {
    	System.out.println("delete");	
    	if (idExpense !=null && idList !=null) {
        		expensesOfListSrevice.deleteExpenseList(idExpense);
        		return "redirect:/lists/{idList}/expenses";
    	}
    	return "redirect:/lists/{idList}/expenses";
    }
}