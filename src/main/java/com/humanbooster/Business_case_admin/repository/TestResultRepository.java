package com.humanbooster.Business_case_admin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.humanbooster.Business_case_admin.model.Candidat;
import com.humanbooster.Business_case_admin.model.TechnicalTest;
import com.humanbooster.Business_case_admin.model.TestResult;

@Repository
public interface TestResultRepository extends CrudRepository<TestResult, Integer> {
	
	@Override
	public List<TestResult> findAll();
	
	public TestResult findTestResultByInfocoIdAndCandidatAndTechnicalTestAndQuestionId(int infoco, Candidat candidat, TechnicalTest technicalTest, int question);

	public List<TestResult> findTestResultsByCandidatAndTechnicalTest(Candidat candidat, TechnicalTest technicalTest);

}
