package com.humanbooster.Business_case_admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.humanbooster.Business_case_admin.model.Question;
import com.humanbooster.Business_case_admin.repository.QuestionRepository;

@Service
public class QuestionService {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	public List<Question> getQuestions(){return this.questionRepository.findAll();}
	
	public void saveOrUpdateQuestion(Question question) {this.questionRepository.save(question);}
			
	public void deleteQuestion(Question question) {this.questionRepository.delete(question);}
	
	public long getQuestionsCount() {return this.questionRepository.count();}
	 
	public Page<Question> getPaginatedQuestions(int pageNo, int pageSize, String sortField, String sortDirection) {
	    	
	    	Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
	    	     Sort.by(sortField).descending();
	    	
	    	Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
	    	return this.questionRepository.findAll(pageable);
	}


}
