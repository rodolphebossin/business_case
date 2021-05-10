package com.humanbooster.Business_case_admin.model;

import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="interviews")
public class Interview {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
	
	@Basic
	@Column(name = "date_time", nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime dateTimeInterview;
	
	@Column(name = "is_accepted", nullable = true)
	private Boolean isAccepted;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="candidat_id", referencedColumnName = "id")
	private Candidat candidat;
	
	@Column(name = "infoco_id", nullable = false)
	private Integer infocoId;
	
	public Interview() {
		
	}

	public Integer getInfocoId() {
		return infocoId;
	}

	public void setInfocoId(Integer infocoId) {
		this.infocoId = infocoId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getDateTimeInterview() {
		return dateTimeInterview;
	}

	public void setDateTimeInterview(LocalDateTime dateTimeInterview) {
		this.dateTimeInterview = dateTimeInterview;
	}

	public Boolean getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public Candidat getCandidat() {
		return candidat;
	}

	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}

	@Override
	public String toString() {
		return "Interview [id=" + id + ", dateTimeInterview=" + dateTimeInterview + ", isAccepted=" + isAccepted
				+ ", candidat=" + candidat + ", infocoId=" + infocoId + "]";
	}
	
	

}
