package com.humanbooster.Business_case_admin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.humanbooster.Business_case_admin.model.ChefDeProjet;

@Repository
public interface ChefDeProjetRepository extends CrudRepository<ChefDeProjet, Integer>{
	
	@Override
	List<ChefDeProjet> findAll();

}
