package com.humanbooster.Business_case_admin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.humanbooster.Business_case_admin.model.Candidat;



@Repository
public interface CandidatRepository extends JpaRepository<Candidat, Integer>{
	
	List<Candidat> findByPrenom(String prenom);
	
	@Override
	List<Candidat> findAll();
	
	Candidat findCandidatById(Integer id);

	Page<Candidat> findAll(Pageable pageable);

}
