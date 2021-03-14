package com.humanbooster.Business_case_admin.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@DiscriminatorValue("ROLE_CANDIDAT")
public class Candidat extends User{
	
    @Basic
    @Past(message = "Date de naissance invalide")
    @NotNull(message ="Veuillez saisir une date de naissance")
    @Column(name = "date_birth", nullable = true)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateBirth;

    
    @Basic
    @NotBlank(message ="Veuillez renseigner votre identifiant pôle emploi")
    @Column(name = "identifiant_pe", nullable = true, length=250, unique =true)
    private String identifiantPe;
    
    @Basic
    @Column(name = "adresse", nullable = true, length=250)
    @NotBlank(message ="Veuillez saisir une adresse")
    private String adresse;

    
    @Basic
    @Pattern(regexp="^(([0-8][0-9])|(9[0-5]))[0-9]{3}$", message = "Code postal invalide")
    @NotBlank(message ="Veuillez saisir un code postal")
    @Column(name = "code_postal", nullable = true, length=250)
    private String codePostal;
    
    @Basic
    @NotBlank(message ="Veuillez saisir une ville")
    @Column(name = "ville", nullable = true, length=250)
    private String ville;
    
	public Candidat() {
	}

	public Candidat(String nom, String prenom, String email, String username, String password, Date dateBirth, String identifiantPe, String adresse, String codePostal, String ville) {
		super(nom, prenom, email, username, password);
		this.dateBirth = dateBirth;
		this.identifiantPe = identifiantPe;
		this.adresse = adresse;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	public String getAdresse() {
		return adresse;
	}


	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}


	public String getCodePostal() {
		return codePostal;
	}


	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}


	public String getVille() {
		return ville;
	}


	public void setVille(String ville) {
		this.ville = ville;
	}


	public Date getDateBirth() {
		return dateBirth;
	}


	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}


	public String getIdentifiantPe() {
		return identifiantPe;
	}


	public void setIdentifiantPe(String identifiantPe) {
		this.identifiantPe = identifiantPe;
	}
    
    
    
}
