package com.humanbooster.Business_case_admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.humanbooster.Business_case_admin.model.Answer;
import com.humanbooster.Business_case_admin.model.Media;
import com.humanbooster.Business_case_admin.model.Question;
import com.humanbooster.Business_case_admin.model.QuestionDomain;
import com.humanbooster.Business_case_admin.services.MediaStorageServiceImpl;
import com.humanbooster.Business_case_admin.services.QuestionDomainService;
import com.humanbooster.Business_case_admin.services.QuestionService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Controller
@CrossOrigin("http://localhost:8080")
@RequestMapping(path="/admins/questions")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuestionDomainService domainService;
	
	@Autowired
	private  MediaStorageServiceImpl storageService;

	
	Logger logger = LogManager.getLogger(QuestionController.class);
	
	
	@RequestMapping(value = "/", method= RequestMethod.GET)
    public ModelAndView question(Model model) {
		return getPaginatedQuestions(1, "text", "asc", model);  
    }
	
	/*
	 * @RequestMapping(value="/", method= RequestMethod.GET) public ModelAndView
	 * question() { List<Question> questions = this.questionService.getQuestions();
	 * ModelAndView mv = new ModelAndView("question/question-list");
	 * mv.addObject("questions", questions); return mv; }
	 */
	
	@RequestMapping(value="/page/{pageNo}", method= RequestMethod.GET)
	public ModelAndView getPaginatedQuestions(@PathVariable(value="pageNo") int pageNo, @RequestParam("sortField") String sortField,
		    @RequestParam("sortDir") String sortDir, Model model) {
		
		int pageSize = 6; // nb de questions par page
		
		ModelAndView mv = new ModelAndView("question/question-list");
		
		Page<Question> page = questionService.getPaginatedQuestions(pageNo, pageSize, sortField, sortDir);
		List<Question> questions = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
	    model.addAttribute("nbTotalPages", page.getTotalPages());
	    model.addAttribute("nbQuestionsDeLaPage", page.getTotalElements());
	    
	    model.addAttribute("sortField", sortField);
	    model.addAttribute("sortDir", sortDir);
	    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
	    
	    model.addAttribute("questions", questions);
	    
	    return mv;	
	}
	
	@RequestMapping(value="/add", method= RequestMethod.GET)
	public ModelAndView addQuestionForm() {
		Question question = new Question();
		Media media = new Media();
		List<QuestionDomain> domains = this.domainService.getQuestionDomains();
		ModelAndView mv = new ModelAndView("question/question-form");
		mv.addObject("question", question);
		mv.addObject("domains", domains);
		mv.addObject("media", media);
		return mv;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveQuestion(@Valid Question question, BindingResult bindingResult, Media media,
			HttpServletRequest request, @RequestParam MultipartFile file, ModelMap map) {
		if (question == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question non trouvée");
		}

		if (bindingResult.hasErrors()) {
			List<QuestionDomain> domains = this.domainService.getQuestionDomains();
			map.addAttribute("domains", domains);
			return "question/question-form";
		} else {
			if (!file.isEmpty()) {
				String newFileName = this.storageService.renameUploadedFile(file);
				this.storageService.save(file, newFileName);

				media.setMediaType(this.storageService.sortMediabasedOnType(file));
				media.setFileName(newFileName);
				media.setQuestion(question);
				question.setMedia(media);
			}

			this.questionService.saveOrUpdateQuestion(question);
			return "redirect:/admins/questions/";
		}
	}
	
	@RequestMapping(value="/edit/{question}", method=RequestMethod.GET)
	public ModelAndView editQuestion(@PathVariable(name="question", required = false) Question question) {
		ModelAndView mv = new ModelAndView("question/question-form");
		mv.addObject("question", question);
		Media media = question.getMedia();
		mv.addObject("media", media);
		List<QuestionDomain> domains = this.domainService.getQuestionDomains();
		mv.addObject("domains", domains);
		return mv;
	}
	
	@RequestMapping(value="/edit/{question}", method = RequestMethod.POST)
	public String editQuestion(@Valid Question question, BindingResult bindingResult, Media media, HttpServletRequest request, @RequestParam MultipartFile file, ModelMap map) 
	{ 
		if(question == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question non trouvée");
		}
		
		if(bindingResult.hasErrors()) {
			List<QuestionDomain> domains = this.domainService.getQuestionDomains();
			map.addAttribute("domains", domains);
			return "question/question-form";
		} else {
			if (!file.isEmpty()) {
				String newFileName = this.storageService.renameUploadedFile(file);
				this.storageService.save(file, newFileName);

				media.setMediaType(this.storageService.sortMediabasedOnType(file));
				media.setFileName(newFileName);
				media.setQuestion(question);
				if(question.getMedia() != null) {
					media.setId(question.getMedia().getId());
				}
				question.setMedia(media);
			}

			this.questionService.saveOrUpdateQuestion(question);
			return "redirect:/admins/questions/";
		}
	}
	
	@RequestMapping(value="/delete/{question}", method = RequestMethod.GET)
	public String deleteQuestion(@PathVariable(name="question", required = false) Question question, RedirectAttributes alert) {
		if(question == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question non trouvée");
		} else {
			if(!question.getTechnicalTests().isEmpty()) {
				alert.addFlashAttribute("error", "impossible de supprimer la question, elle est utilisée dans un ou plusieurs test(s) ");
				return "redirect:/admins/questions/";
			} else {
			this.questionService.deleteQuestion(question);
			return "redirect:/admins/questions/";
			}
		}
	}
	
	@RequestMapping(value="/{question}", method = RequestMethod.GET)
	public ModelAndView questionDetail(@PathVariable (name="question", required = false) Question question) {
		if(question == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question non trouvée");
		} else {
			ModelAndView mv = new ModelAndView("question/question-detail");
			mv.addObject("question", question);
			Media media = question.getMedia();
			mv.addObject("media", media);
			List<Answer> answers = question.getAnswers();
			mv.addObject("answers", answers);
			
			return mv;
		}
	}

}
