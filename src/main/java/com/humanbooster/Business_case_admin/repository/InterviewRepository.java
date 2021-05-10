package com.humanbooster.Business_case_admin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.humanbooster.Business_case_admin.model.Candidat;
import com.humanbooster.Business_case_admin.model.Interview;

@Repository
public interface InterviewRepository extends CrudRepository<Interview, Integer>{
	
	@Override
	List<Interview> findAll();
	
	public Interview findInterviewByCandidatAndInfocoId(Candidat candidat, int infocoId);

}
