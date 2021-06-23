package com.natixis.tricount.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Participant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String firstName;
	
	@ManyToOne
	@JoinColumn(name = "expense_List_id")
	private ExpenseList expenseList;
	
	@ManyToMany
	@JoinTable(name = "beneficiaire",
			joinColumns = @JoinColumn(name = "participant_id"),
			inverseJoinColumns = @JoinColumn(name = "expense_id"))
	private List<Expense> expenses = new ArrayList<>();
	
	public Participant() {
		
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
		
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}	
	
	public ExpenseList getExpenselist() {
		return this.expenseList;
	}
	
	public void setExpenseList(ExpenseList expenseList) {
		this.expenseList = expenseList;
	}
	
	public List<Expense> getExpense() {
		return this.expenses;
	}
	
	public void setExpense(List<Expense> expenses) {
		this.expenses = expenses;
	}
	
}
