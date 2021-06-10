package com.natixis.tricount.controller;

import com.natixis.tricount.entity.Expense;
import com.natixis.tricount.entity.ExpenseList;
import com.natixis.tricount.service.ExpenseListService;
import com.natixis.tricount.service.ExpensesOfListSrevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ExpensesController {

    @Autowired
    private ExpenseListService expenseListService;


    @GetMapping("/lists/{idList}/expenses")
    public String getExpensesListPage(Model model, @PathVariable Long idList) {

        if (idList != null) {
            Optional<ExpenseList> optionalExpenseList = expenseListService.findById(idList);
            ExpenseList expenseList = new ExpenseList();
            List<Expense> expenses = new ArrayList<>();
            if (optionalExpenseList.isPresent()) {
                expenseList = optionalExpenseList.get();
                expenses = optionalExpenseList.get().getExpenses();
                model.addAttribute("expenseList",expenseList);
                model.addAttribute("expensesOfList",expenses);
                return "expensesOfList";
            }

        }

        return "redirect:/lists";
    }

}
