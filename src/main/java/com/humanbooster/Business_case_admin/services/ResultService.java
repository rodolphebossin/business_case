package com.humanbooster.Business_case_admin.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.humanbooster.Business_case_admin.model.Answer;
import com.humanbooster.Business_case_admin.model.Candidat;
import com.humanbooster.Business_case_admin.model.InfoCollective;
import com.humanbooster.Business_case_admin.model.Question;
import com.humanbooster.Business_case_admin.model.TechnicalTest;
import com.humanbooster.Business_case_admin.model.TestQuestion;
import com.humanbooster.Business_case_admin.model.TestResult;

@Service
public class ResultService {
	
	@Autowired
	TestResultService testResultService;
	
	private int testScore;
	private int isEliminated;
	
	public int[] getTestScore(TechnicalTest techTest, Candidat candidat, InfoCollective infoco) {
		
		Set<TestQuestion> testQuestions = techTest.getTestQuestions();
		testScore = 0;
		isEliminated = 0;
		int correctAnswerId = -1;
		
		for(TestQuestion tq : testQuestions) {
			
			Question q = tq.getQuestion();
			List<Answer> answers = q.getAnswers();
			for (Answer a : answers) {
				if(a.getIsCorrectAnswer()) {
					correctAnswerId = a.getId();
				}
			}
			TestResult testresult = this.testResultService.getTestResultByInfocoIdAndCandidatAndTechnicalTestAndQuestionId(infoco.getId(), candidat, techTest, q.getId());
			int testAnswerId = testresult.getAnswerId();
			
			if(correctAnswerId == testAnswerId) {
				testScore++;
			} else {
				if(q.getIsEliminatory()) {
					isEliminated++;
				}
			}
		}
		
		int[] testCompleteResults = new int[2];
		testCompleteResults[0] = testScore;
		testCompleteResults[1] = isEliminated;
		
		return testCompleteResults;
	}
	

}
