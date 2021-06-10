package com.natixis.tricount.controller;

import com.natixis.tricount.entity.ExpenseList;
import com.natixis.tricount.service.CreateUpdateListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class CreateUpdateListController
{
    @Autowired
    private CreateUpdateListService createUpdateListService ;

    @GetMapping("/lists/{idList}")
    public String createUpdateList(Model model, @PathVariable(required = false) Long idList)
    {
        ExpenseList expenseList = new ExpenseList();
        if (idList != null)
        {
            Optional<ExpenseList> OptionalExpenseList =createUpdateListService.findById(idList);
            if (OptionalExpenseList.isPresent()) {
                expenseList = OptionalExpenseList.get();
            }
        }
        model.addAttribute("expenseList", expenseList);
        return "createUpdateList";
    }

    @PostMapping("/lists/{idList}")
    public String createUpdateList(@ModelAttribute ExpenseList expenseList) {
        createUpdateListService.save(expenseList);
        return "redirect:/lists/"+expenseList.getId();
    }

    @GetMapping("/listss")
    public String createUpdateListGet(Model model)
    {
        ExpenseList expenseList = new ExpenseList();
        model.addAttribute("expenseList", expenseList);
        return "createUpdateList";
    }

//    @PostMapping("/lists")
//    public String createList (ExpenseList expenseList)
//    {
//        expenseList.getName();
//
//        createUpdateListService.save(expenseList);
//
//        return "createUpdateList";
//    }
}
