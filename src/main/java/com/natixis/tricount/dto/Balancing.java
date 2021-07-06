package com.natixis.tricount.dto;

public class Balancing implements Comparable<Balancing> {

    private Long idParticipant;
    private String firstName;
    private String lastName;
    private Float accountBalance;

    public Balancing(Long idParticipant, String firstName, String lastName, Float accountBalance) {
        this.idParticipant = idParticipant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountBalance = accountBalance;
    }

    @Override
    public int compareTo(Balancing balancing) {
        return balancing.getAccountBalance().intValue() - this.getAccountBalance().intValue();
    }

    public Long getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(Long idParticipant) {
        this.idParticipant = idParticipant;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Float getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Float accountBalance) {
        this.accountBalance = accountBalance;
    }
}
