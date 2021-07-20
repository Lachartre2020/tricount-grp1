package com.natixis.tricount.dto;

public class BeneficiaireWithSelection {

	public Long idBeneficiaire;
	private String name;
	private String firstName;
	private Boolean selected;
	
	public BeneficiaireWithSelection(Long idBeneficiaire, String name, String firstName, Boolean selected) {
		this.idBeneficiaire = idBeneficiaire;
		this.name = name;
		this.firstName = firstName;
		this.selected = selected;
	}
			
	public Long getIdBeneficiaire() {
		return idBeneficiaire;
	}
	
	public void setIdBeneficiaire(Long idBeneficiaire) {
		this.idBeneficiaire = idBeneficiaire;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public Boolean getSelected() {
		return selected;
	}
	
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}	
}
