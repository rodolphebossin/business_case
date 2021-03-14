package com.humanbooster.Business_case_admin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.humanbooster.Business_case_admin.model.QuestionDomain;


@Repository
public interface DomainRepository extends CrudRepository<QuestionDomain, Integer>{
	
	@Override
	List<QuestionDomain> findAll();

}
