package com.humanbooster.Business_case_admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.humanbooster.Business_case_admin.model.Answer;
import com.humanbooster.Business_case_admin.model.Question;
import com.humanbooster.Business_case_admin.repository.AnswerRepository;

@Service
public class AnswerService {
	
	@Autowired
	private AnswerRepository answerRepository;
	
	public List<Answer> getAnswers(){return this.answerRepository.findAll();}
	
	public List<Answer> getAnswersByQuestion(Question question){return this.answerRepository.findByQuestion(question);}
	
	public void saveOrUpdateAnswer(Answer answer) {this.answerRepository.save(answer);}
			
	public void deleteAnswer(Answer answer) {this.answerRepository.delete(answer);}

}
