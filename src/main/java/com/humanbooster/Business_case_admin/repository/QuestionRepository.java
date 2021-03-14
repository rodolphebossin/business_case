package com.humanbooster.Business_case_admin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.humanbooster.Business_case_admin.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{
	
	@Override
	List<Question> findAll();
	
	Page<Question> findAll(Pageable pageable);

}
