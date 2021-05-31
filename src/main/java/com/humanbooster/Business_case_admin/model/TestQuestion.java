package com.humanbooster.Business_case_admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "test_questions", 
uniqueConstraints=
@UniqueConstraint(columnNames={"question_id", "display_id"}))

public class TestQuestion {
	
	@Id
	@Column(name = "test_question_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "technical_test_id ")
	private TechnicalTest technicalTest;
	
	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;
	
	@Column(name = "display_id")
	private int displayId;

	public TestQuestion() {

	}

	public TestQuestion(TechnicalTest technicalTest, Question question, int displayId) {
		this.technicalTest = technicalTest;
		this.question = question;
		this.displayId = displayId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TechnicalTest getTechnicalTest() {
		return technicalTest;
	}

	public void setTechnicalTest(TechnicalTest technicalTest) {
		this.technicalTest = technicalTest;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public int getDisplayId() {
		return displayId;
	}

	public void setDisplayId(int displayId) {
		this.displayId = displayId;
	}
	
	

	
	
	

}
