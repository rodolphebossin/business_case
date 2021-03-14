package com.humanbooster.Business_case_admin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.humanbooster.Business_case_admin.model.Media;

@Repository
public interface MediaRepository extends CrudRepository<Media, Integer>{
	
	@Override
	List<Media> findAll();

}
