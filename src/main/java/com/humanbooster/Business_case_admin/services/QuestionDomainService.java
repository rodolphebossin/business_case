package com.humanbooster.Business_case_admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.humanbooster.Business_case_admin.model.QuestionDomain;
import com.humanbooster.Business_case_admin.repository.DomainRepository;

@Service
public class QuestionDomainService {
	
	@Autowired
	private DomainRepository domainrepository;
	
	public List<QuestionDomain> getQuestionDomains(){return this.domainrepository.findAll();}
	
	public void saveOrUpdateDomain(QuestionDomain questionDomain) {this.domainrepository.save(questionDomain);}
			
	public void deleteDomain(QuestionDomain questionDomain) {this.domainrepository.delete(questionDomain);}

}
