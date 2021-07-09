package com.natixis.tricount.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private Participant participantPayer;

	@ManyToMany(mappedBy = "expenses")
	private Set<Participant> participants = new HashSet<>();
	
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

	public Participant getParticipantPayer() {
		return this.participantPayer;
	}
	
	public void setParticipantPayer(Participant participant) {
		this.participantPayer = participant;
	}

	public Set<Participant> getParticipants() {
		return this.participants;
	}

	public void setParticipants(Set<Participant> participants) {
		this.participants = participants;
	}

	public void addOneParticipantBeneficiary(Participant participant) {
		this.participants.add(participant);
		participant.getExpense().add(this);
	}

	public void removeParticipants() {
		for (Participant participant : new HashSet<>(participants)) {
			removeParticipant(participant);
		}
	}

	private void removeParticipant(Participant participant) {
		this.getParticipants().remove(participant);
		participant.getExpense().remove(this);
	}

}
