package com.natixis.tricount.controller;

import com.natixis.tricount.dto.Paginable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.natixis.tricount.entity.ExpenseList;
import com.natixis.tricount.service.ExpenseListService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ExpenseListController {

	@Autowired
	private ExpenseListService service;
	
	@GetMapping("/lists")
	public String getAllExpenseLists(Model model,
									 @RequestParam("page") Optional<Integer> page,
									 @RequestParam("size") Optional<Integer> size) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(15);

		Page<ExpenseList> expensiveListPage = service.findPaginated(PageRequest.of(currentPage - 1, pageSize));
		model.addAttribute("expenseList",expensiveListPage);

		int totalPages = expensiveListPage.getTotalPages();
		if (totalPages > 0) {
			Paginable paginable = new Paginable(currentPage,pageSize,totalPages);
			model.addAttribute("paginable", paginable);
		}

		return "lists";
	}

	@PostMapping("/lists")
	public String saveExpensesList(@ModelAttribute ExpenseList expenseList) {
		service.save(expenseList);
		return "redirect:/lists";
	}
}