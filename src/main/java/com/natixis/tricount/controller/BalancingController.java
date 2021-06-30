package com.natixis.tricount.controller;

import com.natixis.tricount.dto.AmountDistribution;
import com.natixis.tricount.dto.Balancing;
import com.natixis.tricount.entity.ExpenseList;
import com.natixis.tricount.service.BalancingService;
import com.natixis.tricount.service.ExpenseListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BalancingController {

    @Autowired
    private ExpenseListService expenseListService;

    @Autowired
    private BalancingService balancingService;

    @GetMapping("/lists/{idList}/balancing")
    public String getBalancingPage(Model model, @PathVariable Long idList) {

        if (idList != null) {

            Optional<ExpenseList> optionalExpenseList = expenseListService.findById(idList);
            ExpenseList expenseList = new ExpenseList();
            List<Balancing> balancingList = new ArrayList<>();
            List<AmountDistribution> amountDistributionList = new ArrayList<>();

            if (optionalExpenseList.isPresent()) {
                expenseList = optionalExpenseList.get();
                balancingList = balancingService.getBalacingPage(idList);
                amountDistributionList = balancingService.getAmountDistributionList(balancingList);
                model.addAttribute("expenseList",expenseList);
                model.addAttribute("balancingList", balancingList);
                model.addAttribute("whoOwesWhomList", amountDistributionList);
                return "balancing";
            }

        }
        return "redirect:/lists";
    }

}
