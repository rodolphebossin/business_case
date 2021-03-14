package com.humanbooster.Business_case_admin.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name="question")
public class Question {
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Basic
    @NotBlank(message = "Veuillez entrer le libellé de la question")
    @Column(name = "text", nullable = false, columnDefinition="TEXT")
    private String text;
    
    @Column(columnDefinition = "boolean default false")
    private Boolean isEliminatory;
    
	@OneToOne(mappedBy = "question", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = true)
	private Media media;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="question_domain_id", referencedColumnName = "id")
	private QuestionDomain questionDomain;
	
	@ManyToMany(mappedBy = "questions")
	private List<TechnicalTest> technicalTests;
	
	@Enumerated(EnumType.STRING)
    private Level level;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
	private List<Answer> answers;

    
    public Question() {
    	this.answers =  new ArrayList<Answer>();
    	this.technicalTests = new ArrayList<TechnicalTest>();
    }

	public List<TechnicalTest> getTechnicalTests() {
		return technicalTests;
	}

	public void setTechnicalTests(List<TechnicalTest> technicalTests) {
		this.technicalTests = technicalTests;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public List<Answer> getAnswers() {
		return answers;
	}
	
	public void addAnswer(Answer answer) {
		this.answers.add(answer);
	}
	
	public void removeQuestion(Answer answer) {
		this.answers.remove(answer);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public QuestionDomain getQuestionDomain() {
		return questionDomain;
	}

	public void setQuestionDomain(QuestionDomain questionDomain) {
		this.questionDomain = questionDomain;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getId() {
		return id;
	}

	public Boolean getIsEliminatory() {
		return isEliminatory;
	}

	public void setIsEliminatory(Boolean isEliminatory) {
		this.isEliminatory = isEliminatory;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}


}
