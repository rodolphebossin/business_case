package com.humanbooster.Business_case_admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.humanbooster.Business_case_admin.model.Candidat;
import com.humanbooster.Business_case_admin.model.TechnicalTest;
import com.humanbooster.Business_case_admin.model.TestResult;
import com.humanbooster.Business_case_admin.repository.TestResultRepository;

@Service
public class TestResultService {
	
	@Autowired TestResultRepository testResultRepository;
	
	public void saveOrUpdateTestResult(TestResult testResult) {this.testResultRepository.save(testResult);}
	
	public TestResult getTestResultByInfocoIdAndCandidatAndTechnicalTestAndQuestionId(int infoco, Candidat candidat, TechnicalTest techTest, int question) {return this.testResultRepository.findTestResultByInfocoIdAndCandidatAndTechnicalTestAndQuestionId(infoco, candidat, techTest, question);}

	public List<TestResult> getTestResultsByCandidatAndTechnicalTest(Candidat candidat, TechnicalTest technicalTest) { return this.testResultRepository.findTestResultsByCandidatAndTechnicalTest(candidat, technicalTest); }
}
