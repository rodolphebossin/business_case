package com.humanbooster.Business_case_admin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.humanbooster.Business_case_admin.model.Question;
import com.humanbooster.Business_case_admin.model.TechnicalTest;
import com.humanbooster.Business_case_admin.model.TestQuestion;

@Repository
public interface TestQuestionRepository extends CrudRepository<TestQuestion, Integer> {
	
	@Override
	public List<TestQuestion> findAll();
	
	public List<Question> findQuestionsByTechnicalTestOrderByDisplayIdAsc(TechnicalTest technicalTest);
	
	public Question getQuestionByDisplayId(Integer dId);

}
