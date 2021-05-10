package com.humanbooster.Business_case_admin.controller;

import java.security.Principal;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.humanbooster.Business_case_admin.model.Candidat;
import com.humanbooster.Business_case_admin.model.InfoCollective;
import com.humanbooster.Business_case_admin.model.Question;
import com.humanbooster.Business_case_admin.model.TechnicalTest;
import com.humanbooster.Business_case_admin.model.TestResult;
import com.humanbooster.Business_case_admin.services.ResultService;
import com.humanbooster.Business_case_admin.services.TestResultService;
import com.humanbooster.Business_case_admin.services.UserService;

@Controller
@CrossOrigin("http://localhost:8080")
@RequestMapping(path= "/candidats")

public class SimpleCandidatController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TestResultService testResultService;
	
	@Autowired
	private ResultService resultService;
	
	
	@RequestMapping(value="/{techTest}/{infoco}", method= RequestMethod.GET)
	public ModelAndView currentCandidat(Principal principal, @PathVariable (name= "techTest", required =false) TechnicalTest techTest,
			@PathVariable (name = "infoco", required = false) InfoCollective infoco) {
	  Candidat candidat = (Candidat) this.userService.getUserByUsername(principal.getName()); 
		  ModelAndView mv = new ModelAndView("exam/exam-start"); 
		  mv.addObject("candidat", candidat);
		  mv.addObject("infoco", infoco);
		  mv.addObject("techTest", techTest);
		  return mv;		  
	 
	}
	
	@RequestMapping(value="/{candidat}/{techTest}/{infoco}/question/1", method= RequestMethod.GET)
	public ModelAndView showTestFirstQuestion(
			@PathVariable (name= "techTest", required =false) TechnicalTest techTest, 
			@PathVariable (name= "candidat", required =false) Candidat candidat,
			@PathVariable (name = "infoco", required = false) InfoCollective infoco, Model model) {

		System.out.println(techTest.getQuestions().size());
		return showTestQuestions(techTest, candidat, infoco, 1, model);		  	 
	}
	
	@RequestMapping(value="/{candidat}/{techTest}/{infoco}/question/{questionNo}", method= RequestMethod.GET)
	public ModelAndView showTestQuestions(
			@PathVariable (name= "techTest", required =false) TechnicalTest techTest, 
			@PathVariable (name= "candidat", required =false) Candidat candidat,
			@PathVariable (name = "infoco", required = false) InfoCollective infoco,
			@PathVariable(value = "questionNo") int questionNo, Model model) {
		  ModelAndView mv = new ModelAndView("exam/exam-question");
		  
		  mv.addObject("questionNo", questionNo);
		  mv.addObject("infoco", infoco);
		 
		  TestResult testResult = new TestResult();
		  	  
		  testResult.setCandidat(candidat);
		  testResult.setTechnicalTest(techTest);
		  testResult.setInfocoId(infoco.getId());

		  List<Question> questions = techTest.getQuestions();
		  Question question = questions.get(questionNo - 1);
		  model.addAttribute("question", question);
		  testResult.setQuestionId(question.getId());
		  mv.addObject("testResult", testResult);

		  return mv;		  	 
	}
	
	@RequestMapping(value = "/{candidat}/{techTest}/{infoco}/question/{questionNo}", method = RequestMethod.POST)
	public String addTestQuestion(@PathVariable (name= "techTest", required =false) TechnicalTest techTest, 
			@PathVariable (name= "candidat", required =false) Candidat candidat,
			@PathVariable (name = "infoco", required = false) InfoCollective infoco,
			@PathVariable(value = "questionNo") int questionNo, @ModelAttribute("testResult") TestResult testResult) {
		
		  if (testResult == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Résultats non trouvés");
		  } else {
			  String url = null;
			testResult.setTechnicalTest(techTest);
			this.testResultService.saveOrUpdateTestResult(testResult);		
			if( questionNo == techTest.getQuestions().size()) {
				url = "redirect:/candidats/" + candidat.getId() + "/" + techTest.getId() + "/" + infoco.getId() + "/" + testResult.getId() + "/resultats";
			} else {
				questionNo = questionNo + 1;
				url = "redirect:/candidats/" + candidat.getId() + "/" + techTest.getId() + "/" + infoco.getId() + "/question/" + questionNo;
			}
			return url;
		  }	
	}
	
	@RequestMapping(value="/edit/{candidat}/{techTest}/{infoco}/question/{questionNo}", method= RequestMethod.GET)
	public ModelAndView editTestQuestions(
			@PathVariable (name= "techTest", required =false) TechnicalTest techTest, 
			@PathVariable (name= "candidat", required =false) Candidat candidat,
			@PathVariable (name = "infoco", required = false) InfoCollective infoco,
			@PathVariable(value = "questionNo") int questionNo, Model model) {
		  ModelAndView mv = new ModelAndView("exam/exam-question"); 
		  
		  mv.addObject("questionNo", questionNo);
		  mv.addObject("infoco", infoco);

		  List<Question> questions = techTest.getQuestions();
		  Question question = questions.get(questionNo - 1);
		  
		  TestResult testResult = this.testResultService.getTestResultByInfocoIdAndCandidatAndTechnicalTestAndQuestionId(infoco.getId(), candidat, techTest, question.getId());
		  model.addAttribute("question", question);
		  
		  mv.addObject("testResult", testResult);

		  return mv;		  	 
	}
	
	@RequestMapping(value = "/edit/{candidat}/{techTest}/{infoco}/question/{questionNo}", method = RequestMethod.POST)
	public String editTestQuestion(
			@PathVariable (name= "techTest", required =false) TechnicalTest techTest, 
			@PathVariable (name= "candidat", required =false) Candidat candidat,
			@PathVariable (name = "infoco", required = false) InfoCollective infoco,
			@PathVariable (value = "questionNo") int questionNo, 
			@ModelAttribute("testResult") TestResult testResult) {
		  if (testResult == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Résultats non trouvés");
		  } else {		
			testResult.setTechnicalTest(techTest);
			this.testResultService.saveOrUpdateTestResult(testResult);
			  
			questionNo = questionNo + 1;
			String url = "redirect:/candidats/" + candidat.getId() + "/" + techTest.getId() + "/" + infoco.getId() + "/question/" + questionNo;
			return url;
		  }	
	}
	
	@RequestMapping(value= "/{candidat}/{techTest}/{infoco}/{testResult}/resultats", method = RequestMethod.GET)
	public ModelAndView getTestResults(
			@PathVariable (name= "techTest", required =false) TechnicalTest techTest, 
			@PathVariable (name= "candidat", required =false) Candidat candidat,
			@PathVariable (name = "infoco", required = false) InfoCollective infoco,
			@PathVariable (name = "testResult", required = false) TestResult testResult) {
		 ModelAndView mv = new ModelAndView("exam/exam-result");
		 mv.addObject("candidat", candidat);
		 
		 int[] results = this.resultService.getTestScore(techTest, candidat, infoco);
		 int bonnesReponses = results[0];
		 int eliminatoire = results[1];
		 mv.addObject("bonnesReponses", bonnesReponses);
		 mv.addObject("eliminatoire", eliminatoire);
		 
		return mv;
	}


}
