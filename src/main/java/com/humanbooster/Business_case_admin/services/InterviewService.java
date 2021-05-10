package com.humanbooster.Business_case_admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.humanbooster.Business_case_admin.model.Candidat;
import com.humanbooster.Business_case_admin.model.Interview;
import com.humanbooster.Business_case_admin.repository.InterviewRepository;

@Service
public class InterviewService {
	
	@Autowired
	InterviewRepository interviewRepository;
	
	public List<Interview> getInterviews(){return this.interviewRepository.findAll();}
	
	
	public void saveOrUpdateInterview(Interview interview) {this.interviewRepository.save(interview);}
	
	public Interview getInterviewByCandidatAndInfoco(Candidat candidat, int infocoId) { return this.interviewRepository.findInterviewByCandidatAndInfocoId(candidat, infocoId); }

}
