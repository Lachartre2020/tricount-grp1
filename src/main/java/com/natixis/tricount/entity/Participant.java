package com.natixis.tricount.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "beneficiaire",
			joinColumns = @JoinColumn(name = "participant_id"),
			inverseJoinColumns = @JoinColumn(name = "expense_id"))
	private Set<Expense> expenses = new HashSet<>();

	@OneToMany(mappedBy = "participantPayer")
	private List<Expense> expensesOfPayer;

	@Transient
	private Float balance;
	
	public Participant() {
		this.balance = 0F;
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
	
	public Set<Expense> getExpense() {
		return this.expenses;
	}
	
	public void setExpense(Set<Expense> expenses) {
		this.expenses = expenses;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public List<Expense> getExpensesOfPayer() {
		return expensesOfPayer;
	}

	public void setExpensesOfPayer(List<Expense> expensesOfPayer) {
		this.expensesOfPayer = expensesOfPayer;
	}
}
