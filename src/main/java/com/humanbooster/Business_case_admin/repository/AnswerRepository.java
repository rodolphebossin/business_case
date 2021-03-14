package com.humanbooster.Business_case_admin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.humanbooster.Business_case_admin.model.Answer;
import com.humanbooster.Business_case_admin.model.Question;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Integer>{
	
	@Override
	List<Answer> findAll();
	
	List<Answer> findByQuestion(Question question);

}
