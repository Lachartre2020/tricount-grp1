package com.natixis.tricount.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.natixis.tricount.entity.Expense;
import com.natixis.tricount.entity.Participant;
import com.natixis.tricount.service.ExpensesOfListSrevice;
import com.natixis.tricount.service.ParticipantService;
import com.natixis.tricount.service.VisuUpdateExpenseService;


@Controller
public class VisuUpdateExpenseController {

    @Autowired
    private VisuUpdateExpenseService visuUpdateExpenseService ;

    @Autowired
    private ParticipantService participantService ;

    @Autowired
    private ExpensesOfListSrevice expenseOfListService ;
    
    @GetMapping("/expenses/{idExpense}")
    public String visuExpense(Model model, @PathVariable Long idExpense)
    {
         Expense expense = new Expense();
        if (idExpense != null)
        {
           Optional<Expense> OptionalExpense =expenseOfListService.findById(idExpense);
            if (OptionalExpense.isPresent()) {
                expense = OptionalExpense.get();
                model.addAttribute("expense", expense);
            }
        }
        List<Participant> participantList =visuUpdateExpenseService.participantList(visuUpdateExpenseService.idExpenseList(idExpense));
        model.addAttribute("participants",participantList); 
        return "visuUpdateExpense";
    }


    @PostMapping("/expenses/{idExpense}/save")
    public String UpdateExpense(@ModelAttribute Expense expenseRet ,Long idPayeur,  @PathVariable Long idExpense)
    {											

        if (idExpense != null)
        {
            Participant participant = new Participant();
            Optional<Expense> OptionalExpense =expenseOfListService.findById(idExpense);
        	if (OptionalExpense.isPresent()) {
        	   Optional<Participant> optionalParticipant = participantService.findById(idPayeur);
        	   participant = optionalParticipant.get();
        	   visuUpdateExpenseService.addPayeurDepense(expenseRet, participant);
            }
        }
        
        return "redirect:/lists/" + visuUpdateExpenseService.idExpenseList(idExpense) + "/expenses";
    }
    
  
}
