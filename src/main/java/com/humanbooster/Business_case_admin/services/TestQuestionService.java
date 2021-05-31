package com.humanbooster.Business_case_admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.humanbooster.Business_case_admin.model.Question;
import com.humanbooster.Business_case_admin.model.TechnicalTest;
import com.humanbooster.Business_case_admin.repository.TestQuestionRepository;

@Service
public class TestQuestionService {
	
	@Autowired TestQuestionRepository testQuestionRepo;

	public List<Question> getQuestionsByTechTest(TechnicalTest technicalTest) { return this.testQuestionRepo.findQuestionsByTechnicalTestOrderByDisplayIdAsc(technicalTest); };
	
	public Question getQuestionByDisplayId(Integer dId) { return this.testQuestionRepo.getQuestionByDisplayId(dId); }
}
