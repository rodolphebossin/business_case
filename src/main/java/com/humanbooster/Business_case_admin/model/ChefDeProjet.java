package com.humanbooster.Business_case_admin.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ROLE_CHEFDEPROJET")
public class ChefDeProjet extends User{
	
	public ChefDeProjet () {
		
	}
	
public ChefDeProjet (String nom, String prenom, String email, String username, String password) {
	super(nom, prenom, email, username, password);
	}
	


}
