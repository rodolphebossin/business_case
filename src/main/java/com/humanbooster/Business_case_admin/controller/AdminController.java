package com.humanbooster.Business_case_admin.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.humanbooster.Business_case_admin.model.Admin;
import com.humanbooster.Business_case_admin.model.User;
import com.humanbooster.Business_case_admin.repository.UserRepository;
import com.humanbooster.Business_case_admin.services.AdminService;


@Controller
@RequestMapping(path="/admins")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@RequestMapping(value="/welcome", method= RequestMethod.GET)
		public ModelAndView adminWelcome(Authentication authentication) {
		User user= userRepository.findByUsername(authentication.getName());
		ModelAndView mv = new ModelAndView("admin/admin-welcome");
		mv.addObject("admin", user);
		return mv;
	}
	
	
	@RequestMapping(value="/", method= RequestMethod.GET)
	public ModelAndView admin() {
		List<Admin> admins = this.adminService.getAdmins();
		ModelAndView mv = new ModelAndView("admin/admin-list");
		mv.addObject("admins", admins);
		return mv;
	}
	
	/* @RequestMapping(value="/{admin}", method= RequestMethod.GET)
	public ModelAndView adminDetail(@PathVariable(name="admin", required = false) Admin admin) {
		
		if(admin == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin non trouvé");
		}
		
		ModelAndView mv = new ModelAndView("admin/admin-detail");
		mv.addObject("admin", admin);
		return mv;
	} */
	
	@RequestMapping(value="/add", method= RequestMethod.GET)
	public ModelAndView addAdminForm() {
		Admin admin = new Admin();
		ModelAndView mv = new ModelAndView("admin/admin-form");
		mv.addObject("admin", admin);
		return mv;
	}
	
	@RequestMapping(value="/add", method= RequestMethod.POST)
	public String saveAdmin(@Valid Admin admin, BindingResult bindingResult) {
		
		if(admin == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin non trouvé");
		}
		
		if(bindingResult.hasErrors()) {
			return "admin/admin-form";
		} else {
			String cryptedPassword = passwordEncoder.encode(admin.getPassword());
			admin.setPassword(cryptedPassword);
			this.adminService.saveOrUpdateAdmin(admin);
			return "redirect:/admins/";
		}
	}
	
	@RequestMapping(value="/edit/{admin}", method=RequestMethod.GET)
	public ModelAndView editAdmin(@PathVariable(name="admin", required = false) Admin admin) {
		ModelAndView mv = new ModelAndView("admin/admin-form");
		mv.addObject("admin", admin);
		return mv;
	}
	
	@RequestMapping(value="/edit/{admin}", method = RequestMethod.POST)
	public String editAdmin(@Valid Admin admin, BindingResult bindingResult) {
		if(admin == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin non trouvé");
		}
		
		if(bindingResult.hasErrors()) {
			return "admin/admin-form";
		} else {
			String cryptedPassword = passwordEncoder.encode(admin.getPassword());
			admin.setPassword(cryptedPassword);
			this.adminService.saveOrUpdateAdmin(admin);
			return "redirect:/admins/";
		}
	}
	
	@RequestMapping(value="/delete/{admin}", method = RequestMethod.GET)
	public String deleteAdmin(@PathVariable(name="admin", required = false) Admin admin) {
		if(admin == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin non trouvé");
		} else {
			this.adminService.deleteAdmin(admin);
			return "redirect:/admins/";
		}
	}
		

}