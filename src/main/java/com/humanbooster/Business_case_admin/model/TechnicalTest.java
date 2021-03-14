package com.humanbooster.Business_case_admin.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="technical_test")
public class TechnicalTest {
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Basic
    @NotBlank(message ="Veuillez saisir un nom")
    @Column(name = "nom", nullable = false, length=250)
    private String nom;
    
    @NotNull(message = "Veuillez saisir une durée en secondes")
    @Column(name="duree", nullable = false)
    private Integer duree;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="test_question",
    		joinColumns = @JoinColumn(name="technical_test_id"),
    		inverseJoinColumns = @JoinColumn(name="question_id"))
	private List<Question> questions;
   
    
    public TechnicalTest() {
    	this.questions = new ArrayList<Question>();
    }
    
    public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public void addQuestion(Question question) {
		questions.add(question);
		question.getTechnicalTests().add(this);
	}
	
	public void removeQuestion(Question question) {
		questions.remove(question);
		question.getTechnicalTests().remove(this);
	}
	
	public List<Question> getQuestions(){
		return this.questions;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Integer getDuree() {
		return duree;
	}

	public void setDuree(Integer duree) {
		this.duree = duree;
	}

}
