package com.humanbooster.Business_case_admin.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="answer")
public class Answer {
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Basic
    @NotBlank(message = "Veuillez entrer le texte de la réponse")
    @Column(name = "text", nullable = false, columnDefinition="TEXT")
    private String text;
    
    @Column(columnDefinition = "boolean default false")
    private Boolean isCorrectAnswer;
    
	@OneToOne(mappedBy = "answer", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = true)
	private Media media;
    
	@ManyToOne(optional = false)
	@JoinColumn(name="question_id", referencedColumnName = "id")
	private Question question;
    
    public Answer() {
    	
    }

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getIsCorrectAnswer() {
		return isCorrectAnswer;
	}

	public void setIsCorrectAnswer(Boolean isCorrectAnswer) {
		this.isCorrectAnswer = isCorrectAnswer;
	}

	public Integer getId() {
		return id;
	}
    
    

}
