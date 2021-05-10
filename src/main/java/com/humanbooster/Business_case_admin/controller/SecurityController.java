package com.humanbooster.Business_case_admin.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.humanbooster.Business_case_admin.model.Candidat;
import com.humanbooster.Business_case_admin.model.InfoCollective;
import com.humanbooster.Business_case_admin.model.TechnicalTest;



@Controller
@RequestMapping(path= "/")
@SessionAttributes({"infoco", "techTest"})
public class SecurityController {
	
	private boolean isAuthenticated() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || AnonymousAuthenticationToken.class.
	      isAssignableFrom(authentication.getClass())) {
	        return false;
	    }
	    return authentication.isAuthenticated();
	}
	
	
	@RequestMapping(value="/login", method= RequestMethod.GET)
	public ModelAndView login(@RequestParam(required = false) InfoCollective infoco, @RequestParam(required = false) TechnicalTest techTest) {	
		ModelAndView mv = new ModelAndView("security/login");
		mv.addObject("infoco", infoco);
		mv.addObject("techTest", techTest);
		return mv;
	}
	

}
