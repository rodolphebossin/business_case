package com.humanbooster.Business_case_admin.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.humanbooster.Business_case_admin.model.Candidat;
import com.humanbooster.Business_case_admin.model.ChefDeProjet;
import com.humanbooster.Business_case_admin.model.InfoCollective;
import com.humanbooster.Business_case_admin.model.Interview;
import com.humanbooster.Business_case_admin.model.TechnicalTest;
import com.humanbooster.Business_case_admin.services.CandidatService;
import com.humanbooster.Business_case_admin.services.ChefDeProjetService;
import com.humanbooster.Business_case_admin.services.InfoCollectiveService;
import com.humanbooster.Business_case_admin.services.InterviewService;
import com.humanbooster.Business_case_admin.services.MailService;
import com.humanbooster.Business_case_admin.services.ResultService;
import com.humanbooster.Business_case_admin.services.TechnicalTestService;
import com.humanbooster.Business_case_admin.services.TestResultService;

@Controller
@CrossOrigin("http://localhost:8080")
@RequestMapping(path = "/chefsDeProjet/infocos")
public class InfoCollectiveController {

	@Autowired
	private InfoCollectiveService infocoService;

	@Autowired
	private TechnicalTestService techtestService;

	@Autowired
	private CandidatService candidatService;

	@Autowired
	private ChefDeProjetService chefDeProjetService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private InterviewService interviewService;
	
	@Autowired
	private ResultService resultService;
	
	@Autowired 
	private TestResultService testResultService;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView infocos() {
		List<InfoCollective> infocos = this.infocoService.getInfocos();
		ModelAndView mv = new ModelAndView("infoco/infoco-list");
		mv.addObject("infocos", infocos);
		return mv;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addInfocoForm() {
		InfoCollective infoco = new InfoCollective();
		List<TechnicalTest> technicalTests = this.techtestService.getTechnicalTests();
		List<ChefDeProjet> chefsDeProjet = this.chefDeProjetService.getChefsDeProjet();
		ModelAndView mv = new ModelAndView("infoco/infoco-create");
		mv.addObject("infoco", infoco);
		mv.addObject("techTests", technicalTests);
		mv.addObject("chefsDeProjet", chefsDeProjet);
		return mv;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveInfoco(@Valid InfoCollective infoco, BindingResult bindingResult, ModelMap map) {
		if (infoco == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Infoco non trouvée");
		}

		if (bindingResult.hasErrors()) {
			List<TechnicalTest> technicalTests = this.techtestService.getTechnicalTests();
			List<ChefDeProjet> chefsDeProjet = this.chefDeProjetService.getChefsDeProjet();
			map.addAttribute("techTests", technicalTests);
			map.addAttribute("chefsDeProjet", chefsDeProjet);
			return "infoco/infoco-create";
		} else {
			this.infocoService.saveOrUpdateInfoco(infoco);
			int infocoId = infoco.getId();
			String url = "redirect:/chefsDeProjet/infocos/add/candidats/" + infocoId;
			return url;
		}
	}

	@RequestMapping(value = "/edit/{infoco}", method = RequestMethod.GET)
	public ModelAndView editInfoco(@PathVariable(name = "infoco", required = false) InfoCollective infoco) {
		ModelAndView mv = new ModelAndView("infoco/infoco-create");
		mv.addObject("infoco", infoco);
		List<TechnicalTest> technicalTests = this.techtestService.getTechnicalTests();
		List<ChefDeProjet> chefsDeProjet = this.chefDeProjetService.getChefsDeProjet();
		mv.addObject("techTests", technicalTests);
		mv.addObject("chefsDeProjet", chefsDeProjet);
		return mv;
	}

	@RequestMapping(value = "/edit/{infoco}", method = RequestMethod.POST)
	public String editInfoco(@Valid @ModelAttribute("infoco") InfoCollective infoco, BindingResult bindingResult,
			ModelMap map) {
		if (infoco == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Infoco non trouvée");
		}

		if (bindingResult.hasErrors()) {
			List<TechnicalTest> technicalTests = this.techtestService.getTechnicalTests();
			List<ChefDeProjet> chefsDeProjet = this.chefDeProjetService.getChefsDeProjet();
			map.addAttribute("techTests", technicalTests);
			map.addAttribute("chefsDeProjet", chefsDeProjet);
			return "infoco/infoco-create";
		} else {
			this.infocoService.saveOrUpdateInfoco(infoco);
			return "redirect:/chefsDeProjet/infocos/";
		}
	}

	@RequestMapping(value = "/delete/{infoco}", method = RequestMethod.GET)
	public String deleteInfoco(@PathVariable(name = "infoco", required = false) InfoCollective infoco,
			RedirectAttributes alert) {
		if (infoco == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Infoco non trouvée");
		} else {
			if (infoco.getCandidats() != null) {
				alert.addFlashAttribute("error",
						"impossible de supprimer cette infoco, des candidats y sont déjà inscrits ");
				return "redirect:/chefsDeProjet/infocos/";
			} else {
				this.infocoService.deleteInfoco(infoco);
				return "redirect:/chefsDeProjet/infocos/";
			}

		}
	}

	@RequestMapping(value = "/add/candidats/{infoco}", method = RequestMethod.GET)
	public ModelAndView addCandidats(@PathVariable(name = "infoco", required = false) InfoCollective infoco,
			Model model) {
		return addPaginatedCandidats(infoco, 1, "nom", "asc", model);
	}

	@RequestMapping(value = "/add/candidats/{infoco}", method = RequestMethod.POST)
	public String addSelectedCandidats(@ModelAttribute("infoco") InfoCollective infoco) {

		if (infoco == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Infoco non trouvée");
		} else {
			this.infocoService.saveOrUpdateInfoco(infoco);
			return "redirect:/chefsDeProjet/infocos/";

		}
	}

	@RequestMapping(value = "/add/candidats/{infoco}/page/{pageNo}", method = RequestMethod.GET)
	public ModelAndView addPaginatedCandidats(
			@PathVariable(name = "infoco", required = false) InfoCollective infoco,
			@PathVariable(value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {

		int pageSize = 6; // nb de candidats par page

		ModelAndView mv = new ModelAndView("infoco/infoco-form");

		Page<Candidat> page = candidatService.getPaginatedCandidats(pageNo, pageSize, sortField, sortDir);
		List<Candidat> candidats = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("nbTotalPages", page.getTotalPages());
		model.addAttribute("nbCandidatsDeLaPage", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("candidats", candidats);

		mv.addObject("infoco", infoco);
		String addOrEdit = "add";
		mv.addObject("addOrEdit", addOrEdit);

		return mv;
	}

	@RequestMapping(value = "/add/candidats/{infoco}/page/{pageNo}", method = RequestMethod.POST)
	public String addSelectedCandidats(@ModelAttribute("infoco") InfoCollective infoco,
			@PathVariable(value = "pageNo") int pageNo) {

		if (infoco == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Infoco non trouvée");
		} else {
			this.infocoService.saveOrUpdateInfoco(infoco);
			return "redirect:/chefsDeProjet/infocos/";

		}
	}

	@RequestMapping(value = "/edit/candidats/{infoco}", method = RequestMethod.GET)
	public ModelAndView editInfocoCandidats(@PathVariable(name = "infoco", required = false) InfoCollective infoco,
			Model model) {
		return editInfocoPaginatedCandidats(infoco, 1, "nom", "asc", model);
	}

	@RequestMapping(value = "/edit/candidats/{infoco}", method = RequestMethod.POST)
	public String editNewSelectedCandidats(@ModelAttribute("infoco") InfoCollective infoco) {
		if (infoco == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Test non trouvée");
		} else {
			this.infocoService.saveOrUpdateInfoco(infoco);
			return "redirect:/chefsDeProjet/infocos/";

		}
	}

	@RequestMapping(value = "/edit/candidats/{infoco}/page/{pageNo}", method = RequestMethod.GET)
	public ModelAndView editInfocoPaginatedCandidats(
			@PathVariable(name = "infoco", required = false) InfoCollective infoco,
			@PathVariable(value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {
		int pageSize = 6; // nb de candidats par page

		ModelAndView mv = new ModelAndView("infoco/infoco-form");

		Page<Candidat> page = candidatService.getPaginatedCandidats(pageNo, pageSize, sortField, sortDir);
		List<Candidat> candidats = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("nbTotalPages", page.getTotalPages());
		model.addAttribute("nbCandidatsDeLaPage", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("candidats", candidats);

		mv.addObject("infoco", infoco);
		String addOrEdit = "edit";
		mv.addObject("addOrEdit", addOrEdit);

		return mv;
	}

	@RequestMapping(value = "/edit/candidats/{infoco}/page/{pageNo}", method = RequestMethod.POST)
	public String editNewSelectedQuestions(@PathVariable(value = "pageNo") int pageNo,
			@ModelAttribute("test") InfoCollective infoco) {
		if (infoco == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "infoco non trouvée");
		} else {
			this.infocoService.saveOrUpdateInfoco(infoco);
			return "redirect:/chefsDeProjet/infocos/";

		}
	}
	
	@RequestMapping(value="/sendEmails/{infoco}", method = RequestMethod.GET)
	public String sendConfirmationEmails(@PathVariable (name= "infoco", required =false) InfoCollective infoco) throws UnsupportedEncodingException, MessagingException {
		List<Candidat> candidats = infoco.getCandidats();

				
		for(Candidat c : candidats){
			
			String body = "<p>Bonjour <b>"  + c.getPrenom() + " " + c.getNom() +  "</b></p>";
			body += "<p>Nous vous confirmons votre inscription à la journée d\'information collective du <b>" + infoco.getDate() + "</b></p>";
			body += "<p>clickez sur le lien suivant pour accéder au test technique</p>";
			body += "<p>Vos identifiants pour vous connecter sont :</p>";
			body += "<p>Username : <strong>" + c.getUsername() + "</strong></p>";
//			body += "<p>Password : <strong>" + c.getPassword() + "</strong></p>";
			// to do : adresse du lien avec prefixe absolu
			body += "<a href=http://localhost:8080/login?techTest=" + infoco.getTechnicalTest().getId() + "&infoco=" + infoco.getId() + ">lien vers le test technique</a>";
			body += "<hr><img src='cid:logoImage'/>";
			
			
			String header = "Confirmation d\'inscription à la journée d\'information collective du " + infoco.getDate();
			this.mailService.sendEmail(c.getEmail(), body, header);
		}
		return "redirect:/chefsDeProjet/infocos/";
	}
	
	@RequestMapping(value="/show/candidats/{infoco}", method =RequestMethod.GET)
	public ModelAndView showSelectedCandidats(@PathVariable(name="infoco", required = false) InfoCollective infoco) {
		ModelAndView mv = new ModelAndView("infoco/infoco-result");
		List<Candidat> candidats = infoco.getCandidats();
		
		// inscrire comme eliminés ceux qui doivent (appeler le resultService pour faire un if sur le score et instancier une interview et seter comme éliminé ceux qui doivent
		for (Candidat c : candidats) {
			// S'assurer que le test à été passé
			if(!this.testResultService.getTestResultsByCandidatAndTechnicalTest(c, infoco.getTechnicalTest()).isEmpty()) {
				int elimine = this.resultService.getTestScore(infoco.getTechnicalTest(), c, infoco)[1];
				System.out.println("ici ici");
				if(elimine>0) {
					Interview interview = new Interview();
					interview.setCandidat(c);
					interview.setInfocoId(infoco.getId());
					interview.setIsAccepted(false);
					this.interviewService.saveOrUpdateInterview(interview);
					}
			}
		}
		mv.addObject("candidats", candidats);	
		mv.addObject("infoco", infoco);	
		return mv;
	}
	
	@RequestMapping(value="/interview", method= RequestMethod.GET)
	public ModelAndView setInterview(
			@RequestParam(required = false) Candidat candidat,
			@RequestParam(required = false )InfoCollective infoco) {
		ModelAndView mv = new ModelAndView("infoco/infoco-interview");
		Interview interview = new Interview();
		interview.setCandidat(candidat);
		interview.setInfocoId(infoco.getId());
		mv.addObject("interview", interview);
		mv.addObject("infoco", infoco);
		mv.addObject("candidat", candidat);
		return mv;
	}
	
	@RequestMapping(value="/interview", method = RequestMethod.POST)
	public String setInterview(@Valid Interview interview, BindingResult bindingResult,
			@RequestParam(required = false) Candidat candidat,
			@RequestParam(required = false )InfoCollective infoco,
			ModelMap map) {
		if (interview == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Interview non trouvée");
		}

		if (bindingResult.hasErrors()) {
			map.addAttribute("infoco", infoco);
			map.addAttribute("candidat", candidat);
			return "infoco/infoco-interview";
		} else {
			this.interviewService.saveOrUpdateInterview(interview);
			String url = "redirect:/chefsDeProjet/infocos/show/candidats/" + infoco.getId();
			return url;
		}
	}
	
	@RequestMapping(value="/edit/interview", method= RequestMethod.GET)
	public ModelAndView editInterview(
			@RequestParam(required = false) Candidat candidat,
			@RequestParam(required = false )InfoCollective infoco) {
		ModelAndView mv = new ModelAndView("infoco/infoco-interview");
		Interview interview = this.interviewService.getInterviewByCandidatAndInfoco(candidat, infoco.getId());
		mv.addObject("interview", interview);
		mv.addObject("infoco", infoco);
		mv.addObject("candidat", candidat);
		return mv;
	}
	
	@RequestMapping(value="/edit/interview", method = RequestMethod.POST)
	public String editInterview(@Valid Interview interview, BindingResult bindingResult,
			@RequestParam(required = false) Candidat candidat,
			@RequestParam(required = false )InfoCollective infoco,
			ModelMap map) {
		if (interview == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Interview non trouvée");
		}

		if (bindingResult.hasErrors()) {
			map.addAttribute("infoco", infoco);
			map.addAttribute("candidat", candidat);
			return "infoco/infoco-interview";
		} else {
			this.interviewService.saveOrUpdateInterview(interview);
			String url = "redirect:/chefsDeProjet/infocos/show/candidats/" + infoco.getId();
			return url;
		}
	}
	
	@RequestMapping(value="/verdict/interview", method= RequestMethod.GET)
	public ModelAndView setVerdict(
			@RequestParam(required = false) Candidat candidat,
			@RequestParam(required = false )InfoCollective infoco) {
		ModelAndView mv = new ModelAndView("infoco/infoco-verdict");
		Interview interview = this.interviewService.getInterviewByCandidatAndInfoco(candidat, infoco.getId());
		mv.addObject("interview", interview);
		mv.addObject("infoco", infoco);
		mv.addObject("candidat", candidat);
		System.out.println(interview.toString());
		return mv;
	}
	
	@RequestMapping(value="/verdict/interview", method = RequestMethod.POST)
	public String setVerdict(@Valid Interview interview, BindingResult bindingResult,
			@RequestParam(required = false) Candidat candidat,
			@RequestParam(required = false )InfoCollective infoco,
			ModelMap map) {
		if (interview == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Interview non trouvée");
		}
		System.out.println(interview.toString());
			this.interviewService.saveOrUpdateInterview(interview);
			String url = "redirect:/chefsDeProjet/infocos/show/candidats/" + infoco.getId();
			return url;
		
	}
	
	
	


}
