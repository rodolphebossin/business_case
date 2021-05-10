package com.humanbooster.Business_case_admin.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="infoco")
public class InfoCollective {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
	 
	@NotBlank(message="Veuillez saisir le nom de l'infoco")
	@Column(name="name", nullable = true)
	private String name;
	
	@Basic
//    @Future(message = "La date n'est pas valide")
    @NotNull(message ="Veuillez saisir une date")
    @Column(name = "date", nullable = true)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
	
	@NotBlank(message = "Veuillez saisir un lieu")
	@Column(name = "lieu", nullable = true)
	private String lieu;
	
	@ManyToOne(optional = true)
	@JoinColumn(name="technicalTest_id", referencedColumnName = "id")
	private TechnicalTest technicalTest;
	
	@ManyToOne(optional = true)
	@JoinColumn(name="chefDeProjet_id", referencedColumnName = "id")
	private ChefDeProjet chefDeProjet;
	
	@ManyToMany(cascade = {
	        CascadeType.PERSIST,
	        CascadeType.MERGE
	    })
	@JoinTable(name = "Infoco_candidat",
    		joinColumns = @JoinColumn(name = "infoco_id"),
    		inverseJoinColumns = @JoinColumn(name = "candidat_id")
		)
	private List<Candidat> candidats;

	public InfoCollective() {
		this.candidats = new ArrayList<Candidat>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLieu() {
		return lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	public TechnicalTest getTechnicalTest() {
		return technicalTest;
	}

	public void setTechnicalTest(TechnicalTest technicalTest) {
		this.technicalTest = technicalTest;
	}

	public ChefDeProjet getChefDeProjet() {
		return chefDeProjet;
	}

	public void setChefDeProjet(ChefDeProjet chefDeProjet) {
		this.chefDeProjet = chefDeProjet;
	}

	public List<Candidat> getCandidats() {
		return candidats;
	}

	public void setCandidats(List<Candidat> candidats) {
		this.candidats = candidats;
	}
	
	public void addCandidat(Candidat candidat) {
		this.candidats.add(candidat);
	}
	
	public void removeCandidat(Candidat candidat) {
		this.candidats.remove(candidat);
	}

	@Override
	public String toString() {
		return "InfoCollective [id=" + id + ", name=" + name + ", date=" + date + ", technicalTest=" + technicalTest
				+ ", chefDeProjet=" + chefDeProjet + "]";
	}
	
	
	

}
