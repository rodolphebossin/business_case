package com.humanbooster.Business_case_admin.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;




@Entity
@Table(name="media")
public class Media {
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
	
	@Basic

    @Column(name = "file_name", nullable = true, length=250)
	private String fileName;
	

	@Column(name = "media_type", nullable = true, length= 250)
	private String mediaType;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id", referencedColumnName = "id")
	private Question question;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "answer_id", referencedColumnName = "id")
	private Answer answer;
	
	public Media() {
		
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Media [fileName=" + fileName + ", mediaType=" + mediaType + "]";
	}
	
	

}
