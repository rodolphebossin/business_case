package com.humanbooster.Business_case_admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;


import com.humanbooster.Business_case_admin.model.QuestionDomain;
import com.humanbooster.Business_case_admin.services.QuestionDomainService;

@Controller
@RequestMapping(path="/admins/domains")
public class QuestionDomainController {
	
	@Autowired
	private QuestionDomainService domainService;
	
	@RequestMapping(value="/add", method= RequestMethod.GET)
	public ModelAndView addDomainForm() {
		QuestionDomain domain = new QuestionDomain();
		ModelAndView mv = new ModelAndView("domain/domain-form");
		mv.addObject("domain", domain);
		return mv;
	}
	
	@RequestMapping(value="/add", method= RequestMethod.POST)
	public String saveQuestion(@Valid QuestionDomain domain, BindingResult bindingResult, HttpServletRequest request) {
		if(domain == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Domaine non trouvé");
		}
		
		if(bindingResult.hasErrors()) {
			return "domain/domain-form";
		} else {
			this.domainService.saveOrUpdateDomain(domain);
			return "redirect:/admins/questions/";
		}
	}

}
