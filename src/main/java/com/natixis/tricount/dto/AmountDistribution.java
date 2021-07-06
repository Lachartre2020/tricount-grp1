package com.natixis.tricount.dto;

public class AmountDistribution {

    private Long idPayer;
    private String firstNamePayer;
    private String lastNamePayer;
    private Float amountDistribution;
    private Long idCollector;
    private String firstNameCollector;
    private String lastNameCollector;

    public AmountDistribution(Long idPayer, String firstNamePayer, String lastNamePayer, Float amountDistribution, Long idCollector, String firstNameCollector, String lastNameCollector) {
        this.idPayer = idPayer;
        this.firstNamePayer = firstNamePayer;
        this.lastNamePayer = lastNamePayer;
        this.amountDistribution = amountDistribution;
        this.idCollector = idCollector;
        this.firstNameCollector = firstNameCollector;
        this.lastNameCollector = lastNameCollector;
    }

    public Long getIdPayer() {
        return idPayer;
    }

    public void setIdPayer(Long idPayer) {
        this.idPayer = idPayer;
    }

    public String getFirstNamePayer() {
        return firstNamePayer;
    }

    public void setFirstNamePayer(String firstNamePayer) {
        this.firstNamePayer = firstNamePayer;
    }

    public String getLastNamePayer() {
        return lastNamePayer;
    }

    public void setLastNamePayer(String lastNamePayer) {
        this.lastNamePayer = lastNamePayer;
    }

    public Float getAmountDistribution() {
        return amountDistribution;
    }

    public void setAmountDistribution(Float amountDistribution) {
        this.amountDistribution = amountDistribution;
    }

    public Long getIdCollector() {
        return idCollector;
    }

    public void setIdCollector(Long idCollector) {
        this.idCollector = idCollector;
    }

    public String getFirstNameCollector() {
        return firstNameCollector;
    }

    public void setFirstNameCollector(String firstNameCollector) {
        this.firstNameCollector = firstNameCollector;
    }

    public String getLastNameCollector() {
        return lastNameCollector;
    }

    public void setLastNameCollector(String lastNameCollector) {
        this.lastNameCollector = lastNameCollector;
    }
}
