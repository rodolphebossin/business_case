package com.humanbooster.Business_case_admin.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.humanbooster.Business_case_admin.model.Candidat;
import com.humanbooster.Business_case_admin.repository.CandidatRepository;



@Service
public class CandidatService {
	
	@Autowired
	private CandidatRepository candidatRepository;
	
	
	public List<Candidat> getCandidats() {return this.candidatRepository.findAll();}
	
	public Candidat getCandidat(Integer id) {return this.candidatRepository.findCandidatById(id);}
	
	public void saveOrUpdateCandidat(Candidat candidat) {this.candidatRepository.save(candidat);}
	
	public void deleteCandidat(Candidat candidat) {this.candidatRepository.delete(candidat);}
	
    public long getCandidatsCount() {return this.candidatRepository.count();}
 
    public Page<Candidat> getPaginatedCandidats(int pageNo, int pageSize, String sortField, String sortDirection) {
    	
    	Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
    	     Sort.by(sortField).descending();
    	
    	Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
    	return this.candidatRepository.findAll(pageable);}

	

}
