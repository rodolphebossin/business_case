package com.humanbooster.Business_case_admin.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;



@Entity
@Table(name="question_domain")
public class QuestionDomain {
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Basic
    @NotBlank(message = "Veuillez entrer le domaine")
    @Column(name = "domain", nullable = false, length=250)
    private String domain;
    
	@OneToMany(mappedBy = "questionDomain", cascade = CascadeType.ALL,
	        orphanRemoval = true)
	private List<Question> questions;
    
    public QuestionDomain() {
		this.questions = new ArrayList<Question>();   	
    }
    
	public void addQuestion(Question question) {
		this.questions.add(question);
	}
	
	public void removeQuestion(Question question) {
		this.questions.remove(question);
	}
	
	public List<Question> getQuestions(){
		return this.questions;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Integer getId() {
		return id;
	}

}
