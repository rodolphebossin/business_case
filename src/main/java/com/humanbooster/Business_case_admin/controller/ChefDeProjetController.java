package com.humanbooster.Business_case_admin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.humanbooster.Business_case_admin.model.ChefDeProjet;
import com.humanbooster.Business_case_admin.model.TechnicalTest;
import com.humanbooster.Business_case_admin.services.ChefDeProjetService;
import com.humanbooster.Business_case_admin.services.WordExportService;

@Controller
@RequestMapping(path="/admins/chefsdeprojet")
public class ChefDeProjetController {
	
	@Autowired
	private ChefDeProjetService chefDeProjetService;
	
	@Autowired
	private WordExportService wordService;
	
	@RequestMapping(value="/", method= RequestMethod.GET)
	public ModelAndView chefDeProjet() {
		List<ChefDeProjet> chefsDeProjet = this.chefDeProjetService.getChefsDeProjet();
		ModelAndView mv = new ModelAndView("chefDeProjet/chefDeProjet-list");
		mv.addObject("chefsDeProjet", chefsDeProjet);
		return mv;
	}
	
	@RequestMapping(value="/{chefDeProjet}", method= RequestMethod.GET)
	public ModelAndView chefDeProjetDetail(@PathVariable(name="chefDeProjet", required = false) ChefDeProjet chefDeProjet) {
		
		if(chefDeProjet == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chef de projet non trouvé");
		}
		
		ModelAndView mv = new ModelAndView("chefDeProjet/chefDeProjet-detail");
		mv.addObject("chefDeProjet", chefDeProjet);
		return mv;
	}
	
	@RequestMapping(value="/add", method= RequestMethod.GET)
	public ModelAndView addChefDeProjetForm() {
		ChefDeProjet chefDeProjet = new ChefDeProjet();
		ModelAndView mv = new ModelAndView("chefDeProjet/chefDeProjet-form");
		mv.addObject("chefDeProjet", chefDeProjet);
		return mv;
	}
	
	@RequestMapping(value="/add", method= RequestMethod.POST)
	public String saveChefDeProjet(@Valid ChefDeProjet chefDeProjet, BindingResult bindingResult) {
		
		if(chefDeProjet == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chef de projet non trouvé");
		}
		
		if(bindingResult.hasErrors()) {
			return "chefDeProjet/chefDeProjet-form";
		} else {
			this.chefDeProjetService.saveOrUpdateChefDeProjet(chefDeProjet);
			return "redirect:/admins/chefsdeprojet/";
		}
	}
	
	@RequestMapping(value="/edit/{chefDeProjet}", method=RequestMethod.GET)
	public ModelAndView editChefDeProjet(@PathVariable(name="chefDeProjet", required = false) ChefDeProjet chefDeProjet) {
		ModelAndView mv = new ModelAndView("chefDeProjet/chefDeProjet-form");
		mv.addObject("chefDeProjet", chefDeProjet);
		return mv;
	}
	
	@RequestMapping(value="/edit/{chefDeProjet}", method = RequestMethod.POST)
	public String editChefDeProjet(@Valid ChefDeProjet chefDeProjet, BindingResult bindingResult) {
		if(chefDeProjet == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chef de projet non trouvé");
		}
		
		if(bindingResult.hasErrors()) {
			return "chefDeProjet/chefDeProjet-form";
		} else {
			this.chefDeProjetService.saveOrUpdateChefDeProjet(chefDeProjet);
			return "redirect:/admins/chefsdeprojet";
		}
	}
	
	@RequestMapping(value="/delete/{chefDeProjet}", method = RequestMethod.GET)
	public String deleteChefDeProjet(@PathVariable(name="chefDeProjet", required = false) ChefDeProjet chefDeProjet) {
		if(chefDeProjet == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chjef de projet non trouvé");
		} else {
			this.chefDeProjetService.deleteChefDeProjet(chefDeProjet);
			return "redirect:/admins/chefsdeprojet";
		}
	}
	
	@RequestMapping(value="/word", method = RequestMethod.GET)
	public void word(HttpServletResponse response, 
			@RequestParam(required = false) TechnicalTest techTest) throws IOException {
		
		this.wordService.generatorWord(techTest);
		
		InputStream inputStream = new FileInputStream(new File("src/main/resources/static/word/techTest-export.docx"));
		IOUtils.copy(inputStream, response.getOutputStream());
		
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachement; filename-export_techTest" + currentDateTime + ".docx";
		response.setHeader(headerKey, headerValue);
		
		response.flushBuffer();
		
		
	}
		

}