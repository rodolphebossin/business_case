package com.humanbooster.Business_case_admin.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("ROLE_CHEFDEPROJET")
public class ChefDeProjet extends User{
	
	
	public ChefDeProjet (String nom, String prenom, String email, String username, String password) {
	super(nom, prenom, email, username, password);
	}
	
	@OneToMany(mappedBy = "chefDeProjet", cascade = CascadeType.ALL)
	private List<InfoCollective> infocos;
	
	public ChefDeProjet() {
		this.infocos =  new ArrayList<InfoCollective>();
	}

	public void setInfocos(List<InfoCollective> infocos) {
		this.infocos = infocos;
	}

	public List<InfoCollective> getInfocos() {
		return infocos;
	}
	
	public void addInfoco(InfoCollective infoco) {
		this.infocos.add(infoco);
	}
	
	public void removeInfoco(InfoCollective infoco) {
		this.infocos.remove(infoco);
	}

	@Override
	public String toString() {
		return this.getPrenom() + " " + this.getNom();
	}
	
	
	
	

}
