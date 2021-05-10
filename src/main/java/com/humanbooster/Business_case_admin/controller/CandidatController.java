package com.humanbooster.Business_case_admin.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.humanbooster.Business_case_admin.model.Candidat;
import com.humanbooster.Business_case_admin.services.CandidatService;



@Controller
@CrossOrigin("http://localhost:8080")
@RequestMapping(path= "/chefsDeProjet/candidats")
public class CandidatController {
	

	
	@Autowired
	private CandidatService candidatService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@RequestMapping(value = "/", method= RequestMethod.GET)
    public ModelAndView candidat(Model model) {
		return getPaginatedCandidats(1, "nom", "asc", model);  
    }
	
	/* @RequestMapping(value="/", method= RequestMethod.GET)
	public ModelAndView candidat() {
		List<Candidat> candidats = this.candidateService.getCandidats();
		ModelAndView mv = new ModelAndView("candidat/candidat-list");
		mv.addObject("candidats", candidats);
		return mv;
	} */
	
	@RequestMapping(value="/page/{pageNo}", method= RequestMethod.GET)
	public ModelAndView getPaginatedCandidats(@PathVariable(value="pageNo") int pageNo, @RequestParam("sortField") String sortField,
		    @RequestParam("sortDir") String sortDir, Model model) {
		
		int pageSize = 3; // nb de candidats par page
		
		ModelAndView mv = new ModelAndView("candidat/candidat-list-paginated");
		
		Page<Candidat> page = candidatService.getPaginatedCandidats(pageNo, pageSize, sortField, sortDir);
		List<Candidat> candidatsList = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
	    model.addAttribute("nbTotalPages", page.getTotalPages());
	    model.addAttribute("nbCandidatsDeLaPage", page.getTotalElements());
	    
	    model.addAttribute("sortField", sortField);
	    model.addAttribute("sortDir", sortDir);
	    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
	    
	    model.addAttribute("candidatsList", candidatsList);
	    
	    return mv;	
	}
	
	@RequestMapping(value="/{candidat}", method= RequestMethod.GET)
	public ModelAndView candidatDetail(@PathVariable(name="candidat", required = false) Candidat candidat) {
		
		if(candidat == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidat non trouvé");
		}
		
		ModelAndView mv = new ModelAndView("candidat/candidat-detail");
		mv.addObject("candidat", candidat);
		return mv;
	}
	
	@RequestMapping(value="/add", method= RequestMethod.GET)
	public ModelAndView addCandidatForm() {
		Candidat candidat = new Candidat();
		ModelAndView mv = new ModelAndView("candidat/candidat-form");
		mv.addObject("candidat", candidat);
		return mv;
	}
	
	@RequestMapping(value="/add", method= RequestMethod.POST)
	public String saveCandidat(@Valid Candidat candidat, BindingResult bindingResult) {
		
		if(candidat == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidat non trouvé");
		}
		
		if(bindingResult.hasErrors()) {
			return "candidat/candidat-form";
		} else {
			String cryptedPassword = passwordEncoder.encode(candidat.getPassword());
			candidat.setPassword(cryptedPassword);
			this.candidatService.saveOrUpdateCandidat(candidat);
			return "redirect:/chefsDeProjet/candidats/";
		}
	}
	
	@RequestMapping(value="/edit/{candidat}", method= RequestMethod.GET)
	public ModelAndView editCandidat(@PathVariable(name="candidat", required = false) Candidat candidat) {
		ModelAndView mv = new ModelAndView("candidat/candidat-form");
		mv.addObject("candidat", candidat);
		return mv;
	}
	
	@RequestMapping(value="/edit/{candidat}", method= RequestMethod.POST)
	public String editCandidat(@Valid Candidat candidat, BindingResult bindingResult) {
		
		if(candidat == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidat non trouvé");
		}
		
		if(bindingResult.hasErrors()) {
			return "candidat/candidat-form";
		} else {
			this.candidatService.saveOrUpdateCandidat(candidat);
			return "redirect:/chefsDeProjet/candidats/";
		}
	}
	
	@RequestMapping(value="/delete/{candidat}", method= RequestMethod.GET)
	public String deleteCandidat(@PathVariable(name="candidat", required = false) Candidat candidat) {
		
		if(candidat == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidat non trouvé");
		} else {
			this.candidatService.deleteCandidat(candidat);
			return "redirect:/chefsDeProjet/candidats/";
		}
	}

}
