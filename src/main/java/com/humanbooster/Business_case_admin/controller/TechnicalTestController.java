package com.humanbooster.Business_case_admin.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.humanbooster.Business_case_admin.model.Question;
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
	
	@RequestMapping(value="/{test}", method = RequestMethod.GET)
	public ModelAndView testDetails(@PathVariable(name = "test", required = false) TechnicalTest technicalTest) {
		if(technicalTest == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Test non trouvé");
		}
		ModelAndView mv = new ModelAndView("techTest/techTest-detail");
		mv.addObject("test", technicalTest);
		List<Question> questions = technicalTest.getQuestions();
		mv.addObject("questions", questions);
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
	public String addTechnicalTestForm(@Valid @ModelAttribute("test") TechnicalTest test, BindingResult bindingResult) {
		if (test == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Test non trouvé");
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
	
	@RequestMapping(value="/edit/{test}", method=RequestMethod.GET)
	public ModelAndView editTestNameAndDuration(@PathVariable(name="test", required = false) TechnicalTest test) {
		ModelAndView mv = new ModelAndView("techTest/techTest-create");
		mv.addObject("test", test);
		return mv;
	}
	
	@RequestMapping(value="/edit/{test}", method=RequestMethod.POST)
	public String editTestNameAndDuration(@Valid @ModelAttribute("test") TechnicalTest test, BindingResult bindingResult){
		if (test == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Test non trouvé");
		}
		if (bindingResult.hasErrors()) {
			return "techTest/techTest-create";
		} else {
			this.technicalTestService.saveOrUpdateTechnicalTest(test);
			return "redirect:/admins/technicalTests/";
		}
	}
	
	@RequestMapping(value="/add/questions/{test}", method= RequestMethod.GET)
	public ModelAndView addQuestions(@PathVariable(name="test", required = false) TechnicalTest technicalTest, Model model) {
		return addPaginatedQuestions(technicalTest, 1, "text", "asc", model);  
	}
	
	@RequestMapping(value = "/add/questions/{test}", method = RequestMethod.POST)
	public String addSelectedQuestions(@PathVariable(name = "test", required = true) TechnicalTest technicalTest, @ModelAttribute("test") TechnicalTest test) {

		if (test == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Test non trouvée");
		} else {
			this.technicalTestService.saveOrUpdateTechnicalTest(test);
			return "redirect:/admins/technicalTests/";

		}
	}
	
	@RequestMapping(value="/add/questions/{test}/page/{pageNo}", method= RequestMethod.GET)
	public ModelAndView addPaginatedQuestions(@PathVariable(name="test", required = false) TechnicalTest technicalTest, @PathVariable(value="pageNo") int pageNo, @RequestParam("sortField") String sortField,
		    @RequestParam("sortDir") String sortDir, Model model) {
		
		int pageSize = 6; // nb de questions par page
		
		ModelAndView mv = new ModelAndView("techTest/techTest-form");
		
		Page<Question> page = questionService.getPaginatedQuestions(pageNo, pageSize, sortField, sortDir);
		List<Question> questions = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
	    model.addAttribute("nbTotalPages", page.getTotalPages());
	    model.addAttribute("nbQuestionsDeLaPage", page.getTotalElements());
	    
	    model.addAttribute("sortField", sortField);
	    model.addAttribute("sortDir", sortDir);
	    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
	    
	    model.addAttribute("questions", questions);
	    
		mv.addObject("test", technicalTest);
		String addOrEdit = "add";
		mv.addObject("addOrEdit", addOrEdit);
		
		return mv;
	}
	
	@RequestMapping(value = "/add/questions/{test}/page/{pageNo}", method = RequestMethod.POST)
	public String addSelectedQuestions(@PathVariable(name = "test", required = true) TechnicalTest technicalTest, @PathVariable(value="pageNo") int pageNo, @ModelAttribute("test") TechnicalTest test) {

		if (test == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Test non trouvée");
		} else {
			this.technicalTestService.saveOrUpdateTechnicalTest(test);
			return "redirect:/admins/technicalTests/";

		}
	}
	
	@RequestMapping(value="/edit/questions/{test}", method=RequestMethod.GET)
	public ModelAndView editTestQuestions(@PathVariable(name="test", required = false) TechnicalTest technicalTest, Model model) {
		return editTestPaginatedQuestions(technicalTest, 1, "text", "asc", model);  
	}
	
	@RequestMapping(value="/edit/questions/{test}", method = RequestMethod.POST)
	public String editNewSelectedQuestions(@PathVariable(name = "test", required = true) TechnicalTest technicalTest, @ModelAttribute("test") TechnicalTest test) {
		if (test == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Test non trouvée");
		} else {
			this.technicalTestService.saveOrUpdateTechnicalTest(test);
			return "redirect:/admins/technicalTests/";

		}
	}
	
	@RequestMapping(value="/edit/questions/{test}/page/{pageNo}", method=RequestMethod.GET)
	public ModelAndView editTestPaginatedQuestions(@PathVariable(name="test", required = false) TechnicalTest technicalTest, @PathVariable(value="pageNo") int pageNo, @RequestParam("sortField") String sortField,
		    @RequestParam("sortDir") String sortDir, Model model) {
		int pageSize = 6; // nb de questions par page
		
		ModelAndView mv = new ModelAndView("techTest/techTest-form");
		
		Page<Question> page = questionService.getPaginatedQuestions(pageNo, pageSize, sortField, sortDir);
		List<Question> questions = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
	    model.addAttribute("nbTotalPages", page.getTotalPages());
	    model.addAttribute("nbQuestionsDeLaPage", page.getTotalElements());
	    
	    model.addAttribute("sortField", sortField);
	    model.addAttribute("sortDir", sortDir);
	    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
	    
	    model.addAttribute("questions", questions);
	    
		mv.addObject("test", technicalTest);
		String addOrEdit = "edit";
		mv.addObject("addOrEdit", addOrEdit);
		
		return mv;
	}
	
	@RequestMapping(value="/edit/questions/{test}/page/{pageNo}", method = RequestMethod.POST)
	public String editNewSelectedQuestions(@PathVariable(name = "test", required = true) TechnicalTest technicalTest, @PathVariable(value="pageNo") int pageNo, @ModelAttribute("test") TechnicalTest test) {
		if (test == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Test non trouvée");
		} else {
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
