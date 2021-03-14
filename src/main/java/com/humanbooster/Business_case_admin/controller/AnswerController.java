package com.humanbooster.Business_case_admin.controller;

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

import com.humanbooster.Business_case_admin.model.Answer;
import com.humanbooster.Business_case_admin.model.Media;
import com.humanbooster.Business_case_admin.model.Question;
import com.humanbooster.Business_case_admin.services.AnswerService;
import com.humanbooster.Business_case_admin.services.MediaStorageServiceImpl;

@Controller
@CrossOrigin("http://localhost:8080")
@RequestMapping(path="/admins/answers")
public class AnswerController {
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private  MediaStorageServiceImpl storageService;
	
	@RequestMapping(value="/{question}", method= RequestMethod.GET)
	public ModelAndView question(@PathVariable(name="question", required = false) Question question) {
		List<Answer> answers = this.answerService.getAnswersByQuestion(question);
		ModelAndView mv = new ModelAndView("answer/answer-list");
		mv.addObject("question", question);
		mv.addObject("answers", answers);
		return mv;
	}
	
	@RequestMapping(value="/add/{question}", method= RequestMethod.GET)
	public ModelAndView addAnswerForm(@PathVariable(name="question", required = false) Question question) {
		Answer answer = new Answer();
		Media media = new Media();
		answer.setQuestion(question);
		ModelAndView mv = new ModelAndView("answer/answer-form");
		mv.addObject("answer", answer);
		mv.addObject("media", media);
		mv.addObject("question", question);
		return mv;
	}
	
	@RequestMapping(value = "/add/{question}", method = RequestMethod.POST)
	public String saveAnswer(@PathVariable(name="question", required = false) Question question, @Valid Answer answer, Media media, BindingResult bindingResult,
			HttpServletRequest request, @RequestParam MultipartFile file) {
		if (answer == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Réponse non trouvée");
		}

		if (bindingResult.hasErrors()) {
			return "answer/answer-form";
		} else {
			if (!file.isEmpty()) {
				String newFileName = this.storageService.renameUploadedFile(file);
				this.storageService.save(file, newFileName);

				media.setMediaType(this.storageService.sortMediabasedOnType(file));
				media.setFileName(newFileName);
				media.setQuestion(null);
				media.setAnswer(answer);
				answer.setMedia(media);
			}

			this.answerService.saveOrUpdateAnswer(answer);
			return "redirect:/admins/answers/{question}";
		}
	}
	
	@RequestMapping(value="/edit/{answer}", method=RequestMethod.GET)
	public ModelAndView editAnswer(@PathVariable(name="answer", required = false) Answer answer) {
		ModelAndView mv = new ModelAndView("answer/answer-form");
		mv.addObject("answer", answer);
		Media media = answer.getMedia();
		mv.addObject("media", media);
		Question question = answer.getQuestion();
		mv.addObject("question", question);
		return mv;
	}
	
	@RequestMapping(value="/edit/{answer}", method = RequestMethod.POST)
	public String editQuestion(@Valid Answer answer, Media media, BindingResult bindingResult, HttpServletRequest request, @RequestParam MultipartFile file) 
		{ 
			if(answer == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Réponse non trouvée");
			}
			
			if(bindingResult.hasErrors()) {
				return "answer/answer-form";
			} else {
				if (!file.isEmpty()) {
					String newFileName = this.storageService.renameUploadedFile(file);
					this.storageService.save(file, newFileName);

					media.setMediaType(this.storageService.sortMediabasedOnType(file));
					media.setFileName(newFileName);
					media.setAnswer(answer);
					if(answer.getMedia() != null) {
						media.setId(answer.getMedia().getId());
					}
					answer.setMedia(media);
				}

				this.answerService.saveOrUpdateAnswer(answer);
				int question = answer.getQuestion().getId();
				String url = "redirect:/admins/answers/" + question;
				return url;
			}
		}
	
	@RequestMapping(value="/delete/{answer}", method = RequestMethod.GET)
	public String deleteAnswer(@PathVariable(name="answer", required = false) Answer answer) {
		if(answer == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Réponse non trouvée");
		} else {
			int question = answer.getQuestion().getId();
			String url = "redirect:/admins/answers/" + question;
			this.answerService.deleteAnswer(answer);
			return url;
		}
	}
	


}
