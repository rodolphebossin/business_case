package com.humanbooster.Business_case_admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "test_results")
public class TestResult {
	
	@Id
	@Column(name = "test_result_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "candidat_id")
	private Candidat candidat;
	
	@ManyToOne
	@JoinColumn(name = "technical_test_id ")
	private TechnicalTest technicalTest;
	
	@Column(name = "question_id")
	private int questionId;
	
	@Column(name = "answer_id")
	private int answerId;
	
	@Column(name = "infoco_id")
	private int infocoId;
	
	
	public TestResult() {

	}
	
	public TestResult(Candidat candidat, TechnicalTest technicalTest, int questionId, int answerId, int infocoId) {
		this.candidat = candidat;
		this.technicalTest = technicalTest;
		this.questionId = questionId;
		this.answerId = answerId;
		this.infocoId = infocoId;
	}
	
	@Override
	public String toString() {
		return "TestResult [id=" + id + ", candidat=" + candidat + ", technicalTest=" + technicalTest + ", questionId="
				+ questionId + ", answerId=" + answerId + ", infocoId=" + infocoId + "]";
	}

	public int getInfocoId() {
		return infocoId;
	}

	public void setInfocoId(int infocoId) {
		this.infocoId = infocoId;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Candidat getCandidat() {
		return candidat;
	}
	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}
	public TechnicalTest getTechnicalTest() {
		return technicalTest;
	}
	public void setTechnicalTest(TechnicalTest technicalTest) {
		this.technicalTest = technicalTest;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public int getAnswerId() {
		return answerId;
	}
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
	
	

}
