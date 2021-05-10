package com.humanbooster.Business_case_admin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.humanbooster.Business_case_admin.model.InfoCollective;

public interface InfoCollectiveRepository extends CrudRepository<InfoCollective, Integer>{
	
	@Override
	List<InfoCollective> findAll();
	
	InfoCollective findInfocoById(Integer id);

}
