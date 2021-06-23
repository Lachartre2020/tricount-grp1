package com.natixis.tricount.controller;

import com.natixis.tricount.entity.ExpenseList;
import com.natixis.tricount.entity.Participant;
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
        model.addAttribute("participants", expenseList.getParticipants());
        return "createUpdateList";
    }

    @PostMapping("/lists/{idList}")
    public String createUpdateList(@ModelAttribute ExpenseList expenseList) {
        createUpdateListService.save(expenseList);
        return "redirect:/lists/"+expenseList.getId();
    }
    @PostMapping("/lists/{idList}/participant")
    public String addParticipantInList(@ModelAttribute Participant participant, @PathVariable Long idList)
    {
        createUpdateListService.addParticipantList(participant,idList);
        Optional<ExpenseList> expenseList=createUpdateListService.findById(idList);
//        expenseList.get().getParticipants().add(participant);
//        createUpdateListService.save(expenseList.get());
        return "redirect:/lists/"+expenseList.get().getId();
    }

    @GetMapping("/listss")
    public String createUpdateListGet(Model model)
    {
        ExpenseList expenseList = new ExpenseList();
        model.addAttribute("expenseList", expenseList);
        return "createUpdateList";
    }

    @PostMapping("/participant/delete/{idParticipant}/{idExpenseList}")
    public String removeParticipantInList(@PathVariable Long idParticipant,@PathVariable Long idExpenseList)
    {
        createUpdateListService.removeParticipantList(idParticipant);
//        expenseList.get().getParticipants().add(participant);
//        createUpdateListService.save(expenseList.get());
        return "redirect:/lists/"+idExpenseList;
    }
}
