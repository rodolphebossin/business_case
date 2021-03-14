package com.humanbooster.Business_case_admin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.humanbooster.Business_case_admin.model.Admin;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Integer>{
	
	@Override
	List<Admin> findAll();

}
