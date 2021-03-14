package com.humanbooster.Business_case_admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.humanbooster.Business_case_admin.model.TechnicalTest;
import com.humanbooster.Business_case_admin.repository.TechnicalTestRepository;

@Service
public class TechnicalTestService {
	
	@Autowired
	private TechnicalTestRepository technicalTestRepository;
	
	public List<TechnicalTest> getTechnicalTests(){return this.technicalTestRepository.findAll();}
	
	public void saveOrUpdateTechnicalTest(TechnicalTest technicalTest) {this.technicalTestRepository.save(technicalTest);}
			
	public void deleteTechnicalTest(TechnicalTest technicalTest) {this.technicalTestRepository.delete(technicalTest);}


}
