package com.natixis.tricount.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Float amount;
	
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, optional = false)
    @JoinColumn(name = "expense_list_id", nullable = false)
    private ExpenseList expenseList;
	
	@ManyToOne
	@JoinColumn(name = "participant_id")
	private Participant participant;

	@ManyToMany(mappedBy = "expenses")
	private List<Participant> participants = new ArrayList<>();
	
	public Expense() {
		
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
	
	public Float getAmount() {
		return this.amount;
	}
	
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	
	public ExpenseList getExpenselist() {
		return this.expenseList;
	}
	
	public void setExpenseList(ExpenseList expenseList) {
		this.expenseList = expenseList;
	}

	public Participant getParticipant() {
		return this.participant;
	}
	
	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	public List<Participant> getParticipants() {
		return this.participants;
	}
	
	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}	
	
}
