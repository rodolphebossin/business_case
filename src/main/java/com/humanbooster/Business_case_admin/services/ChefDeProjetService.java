package com.humanbooster.Business_case_admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.humanbooster.Business_case_admin.model.ChefDeProjet;
import com.humanbooster.Business_case_admin.repository.ChefDeProjetRepository;


@Service
public class ChefDeProjetService {
	
	@Autowired
	private ChefDeProjetRepository chefDeProjetRepository;
	
	public List<ChefDeProjet> getChefsDeProjet(){return this.chefDeProjetRepository.findAll();}
	
	public void saveOrUpdateChefDeProjet(ChefDeProjet chefDeProjet) {this.chefDeProjetRepository.save(chefDeProjet);}
			
	public void deleteChefDeProjet(ChefDeProjet chefDeProjet) {this.chefDeProjetRepository.delete(chefDeProjet);}
}