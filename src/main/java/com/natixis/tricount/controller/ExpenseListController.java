package com.natixis.tricount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.natixis.tricount.entity.ExpenseList;
import com.natixis.tricount.service.ExpenseListService;

@Controller
public class ExpenseListController {

	@Autowired
	private ExpenseListService service;
	
	@GetMapping("/lists")
	public String getAllExpenseLists(Model model) {
		model.addAttribute("expenseList",service.findAll() );
		return "lists";
	}

	@PostMapping("/lists")
	public String saveExpensesList(@ModelAttribute ExpenseList expenseList) {
		service.save(expenseList);
		return "lists";
	}
}