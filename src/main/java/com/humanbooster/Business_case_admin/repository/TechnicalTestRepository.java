package com.humanbooster.Business_case_admin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.humanbooster.Business_case_admin.model.TechnicalTest;

@Repository
public interface TechnicalTestRepository extends CrudRepository<TechnicalTest, Integer> {
	
	@Override
	List<TechnicalTest> findAll();

}
