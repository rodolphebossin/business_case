package com.humanbooster.Business_case_admin.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name="user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name="authorities",
		discriminatorType = DiscriminatorType.STRING)
public class User {
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Basic
    @NotBlank(message = "Veuillez entrer votre Nom")
    @Column(name = "nom", nullable = false, length=250)
    private String nom;

    
    @Basic
    @NotBlank(message = "Veuillez entrer votre Prénom")
    @Column(name = "prenom", nullable = false, length=250)
    private String prenom;
    
    @Basic
    @NotBlank(message = "Veuillez entrer votre email")
    @Email(message ="Veuillez fournir une adresse email valide")
    @Column(name="email", nullable = false, unique = true)
    private String email;
    
    @Basic
    @NotBlank(message = "Veuillez entrer un Username")
    @Column(name = "username", nullable = false, unique = true, length=250)
    private String username;
    
    @NotBlank(message = "Veuillez entrer un mot de passe")
    @Column(name="password", nullable = false)
    private String password;

	public User() {
	}

	public User(String nom, String prenom, String email, String username, String password) {
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}
	
	
    
    

}
