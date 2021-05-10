package com.humanbooster.Business_case_admin.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
    
    @ManyToMany(mappedBy = "candidats")
	private List<InfoCollective> infocos;
    
    @OneToMany(mappedBy = "candidat")
    private Set<TestResult> testResults = new HashSet<>();
    
	@OneToMany(mappedBy = "candidat", cascade = CascadeType.ALL)
	private List<Interview> interviews;
    
    
	public Candidat() {
		this.infocos = new ArrayList<>();
		this.interviews =  new ArrayList<Interview>();
	}

	public Candidat(String nom, String prenom, String email, String username, String password, Date dateBirth, String identifiantPe, String adresse, String codePostal, String ville) {
		super(nom, prenom, email, username, password);
		this.dateBirth = dateBirth;
		this.identifiantPe = identifiantPe;
		this.adresse = adresse;
		this.codePostal = codePostal;
		this.ville = ville;
	}
	
	public void addInterview(Interview interview) {
		this.interviews.add(interview);
	}
	
	public void removeAnswer(Interview interview) {
		this.interviews.remove(interview);
	}

	public List<Interview> getInterviews() {
		return interviews;
	}

	public void setInterviews(List<Interview> interviews) {
		this.interviews = interviews;
	}

	public Set<TestResult> getTestResults() {
		return testResults;
	}

	public void setTestResults(Set<TestResult> testResults) {
		this.testResults = testResults;
	}

	public List<InfoCollective> getInfocos() {
		return infocos;
	}

	public void setInfocos(List<InfoCollective> infocos) {
		this.infocos = infocos;
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
