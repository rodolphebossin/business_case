package com.humanbooster.Business_case_admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.humanbooster.Business_case_admin.model.User;
import com.humanbooster.Business_case_admin.repository.UserRepository;

@Controller
@RequestMapping(path= "/chefsDeProjet")
public class ChefDeProjetWelcomeController {
	
	@Autowired
	private UserRepository userRepository;
	
	
	@RequestMapping(value="/welcome", method= RequestMethod.GET)
		public ModelAndView chefDeProjetWelcome(Authentication authentication) {
		User user= userRepository.findByUsername(authentication.getName());
		ModelAndView mv = new ModelAndView("chefDeProjet/chefDeProjet-welcome");
		mv.addObject("chefDeProjet", user);
		return mv;
	}

}
