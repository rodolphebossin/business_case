package com.humanbooster.Business_case_admin.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ROLE_ADMIN")
public class Admin extends User{
	

	public Admin(String nom, String prenom, String email, String username, String password) {
		super(nom, prenom, email, username, password);
	}

	public Admin() {
	}
	
	

}
