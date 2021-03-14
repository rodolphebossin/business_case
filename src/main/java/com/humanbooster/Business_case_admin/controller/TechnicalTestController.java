package com.humanbooster.Business_case_admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.humanbooster.Business_case_admin.model.Media;
import com.humanbooster.Business_case_admin.model.Question;
import com.humanbooster.Business_case_admin.model.QuestionDomain;
import com.humanbooster.Business_case_admin.model.TechnicalTest;
import com.humanbooster.Business_case_admin.services.QuestionService;
import com.humanbooster.Business_case_admin.services.TechnicalTestService;

@Controller
@CrossOrigin("http://localhost:8080")
@RequestMapping(path="/admins/technicalTests")
public class TechnicalTestController {
	
	@Autowired
	private TechnicalTestService technicalTestService;
	
	@Autowired
	private QuestionService questionService;
	
	@RequestMapping(value="/", method= RequestMethod.GET)
	public ModelAndView technicalTests() {
		List<TechnicalTest> technicalTests = this.technicalTestService.getTechnicalTests();
		ModelAndView mv = new ModelAndView("techTest/techTest-list");
		mv.addObject("technicalTests", technicalTests);
		return mv;
	}
	
	@RequestMapping(value="/add", method= RequestMethod.GET)
	public ModelAndView addTechnicalTestForm() {
		TechnicalTest test = new TechnicalTest();
		ModelAndView mv = new ModelAndView("techTest/techTest-create");
		mv.addObject("test", test);
		return mv;
	}
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addTechnicalTestForm(@Valid TechnicalTest test, BindingResult bindingResult) {
		if (test == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question non trouvée");
		}

		if (bindingResult.hasErrors()) {
			return "techTest/techTest-create";
		} else {
			this.technicalTestService.saveOrUpdateTechnicalTest(test);
			int testId = test.getId();
			String url = "redirect:/admins/technicalTests/add/questions/" + testId;
			return url;
		}
	}
	
	@RequestMapping(value="/add/questions/{test}", method= RequestMethod.GET)
	public ModelAndView addQuestions(@PathVariable(name="test", required = false) TechnicalTest technicalTest) {
		ModelAndView mv = new ModelAndView("techTest/techTest-form");
		List<Question> selectedQuestions = new ArrayList<>();
		List<Question> questions = this.questionService.getQuestions();
		mv.addObject("test", technicalTest);
		mv.addObject("selectedQuestions", selectedQuestions);
		mv.addObject("questions", questions);
		return mv;
	}
	
	@RequestMapping(value = "/add/questions/{test}", method = RequestMethod.POST)
	public String addQuestions(@PathVariable(name = "test", required = true) TechnicalTest test,
			HttpServletRequest request) {

		List<Question> questions = questionService.getQuestions();

		if (test == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question non trouvée");
		} else {
			for (Question question : questions) {
				if (request.getParameterMap().containsKey("selectedQuestions[" + question.getId() + "]")) {
					test.addQuestion(question);
				}
			}

			this.technicalTestService.saveOrUpdateTechnicalTest(test);

			return "redirect:/admins/technicalTests/";

		}
	}
	
	@RequestMapping(value="/edit/{test}", method=RequestMethod.GET)
	public ModelAndView editTest(@PathVariable(name="test", required = false) TechnicalTest technicalTest) {
		ModelAndView mv = new ModelAndView("techTest/techTest-form");
		mv.addObject("test", technicalTest);
		List<Question> selectedQuestions = technicalTest.getQuestions();
		List<Question> questions = this.questionService.getQuestions();
		mv.addObject("selectedQuestions", selectedQuestions);
		mv.addObject("questions", questions);
		
		return mv;
	}
	
	@RequestMapping(value="/edit/{test}", method = RequestMethod.POST)
	public String editQuestion(@PathVariable(name = "test", required = true) TechnicalTest test,
			HttpServletRequest request) {
		List<Question> questions = questionService.getQuestions();
		
		if(test == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ce test n'existe pas");
		} else {
			for (Question question : questions) {
				if (request.getParameterMap().containsKey("selectedQuestions[" + question.getId() + "]")) {
					test.addQuestion(question);
				}
			}

			this.technicalTestService.saveOrUpdateTechnicalTest(test);

			return "redirect:/admins/technicalTests/";

		}
	}
	

	@RequestMapping(value="/delete/{test}", method = RequestMethod.GET)
	public String deleteTest(@PathVariable(name="test", required = false) TechnicalTest test) {
		if(test == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Test non trouvé");
		} else {
			this.technicalTestService.deleteTechnicalTest(test);
			return "redirect:/admins/technicalTests/";
		}
	}
	

}
