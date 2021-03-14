package com.humanbooster.Business_case_admin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.humanbooster.Business_case_admin.model.User;



public interface UserRepository extends CrudRepository<User, Integer>{
	
	@Override
	List<User> findAll();

	User findByUsername(String username);


}
