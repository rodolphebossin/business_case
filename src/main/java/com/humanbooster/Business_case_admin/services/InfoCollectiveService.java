package com.humanbooster.Business_case_admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.humanbooster.Business_case_admin.model.InfoCollective;
import com.humanbooster.Business_case_admin.repository.InfoCollectiveRepository;

@Service
public class InfoCollectiveService {
	
	@Autowired InfoCollectiveRepository infocoRepository;
	
	public List<InfoCollective> getInfocos() {return this.infocoRepository.findAll();}
	
	public InfoCollective getInfoco(Integer id) {return this.infocoRepository.findInfocoById(id);}
	
	public void saveOrUpdateInfoco(InfoCollective infoco) {this.infocoRepository.save(infoco);}
	
	public void deleteInfoco(InfoCollective infoco) {this.infocoRepository.delete(infoco);}

}
