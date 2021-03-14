package com.humanbooster.Business_case_admin;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.humanbooster.Business_case_admin.model.Admin;
import com.humanbooster.Business_case_admin.model.Candidat;
import com.humanbooster.Business_case_admin.model.ChefDeProjet;
import com.humanbooster.Business_case_admin.repository.AdminRepository;
import com.humanbooster.Business_case_admin.repository.CandidatRepository;
import com.humanbooster.Business_case_admin.repository.ChefDeProjetRepository;
import com.humanbooster.Business_case_admin.services.MediaStorageService;

@SpringBootApplication
public class BusinessCaseAdminApplication {

	@Resource
	private  MediaStorageService storageService;
	
	public static void main(String[] args) {
		SpringApplication.run(BusinessCaseAdminApplication.class, args);}
		
		@Bean
		public CommandLineRunner feedBdd(AdminRepository adminRepository,CandidatRepository candidateRepository,
				ChefDeProjetRepository chefDeProjetRepository, BCryptPasswordEncoder passwordEncoder) {
			return (args) -> {
				
				storageService.init();
				
				if(adminRepository.count() == 0) {
				String cryptedPassword = passwordEncoder.encode("admin");
				Admin admin = new Admin("Toto", "toto", "toto@gmail.com", "toto", cryptedPassword);
	
				adminRepository.save(admin);}
				
				if(chefDeProjetRepository.count() == 0) {
					String cryptedPassword = passwordEncoder.encode("tata");
					ChefDeProjet chefDeProjet = new ChefDeProjet("Tata", "tata", "tata@gmail.com", "tata", cryptedPassword);
		
					chefDeProjetRepository.save(chefDeProjet);}
				
				if(candidateRepository.count() == 0) {
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					Date birthCandidat = formatter.parse("30/10/1970");
					String cryptedPassword = passwordEncoder.encode("tutu");
					Candidat candidat = new Candidat("Tutu", "tutu", "tutu@gmail.com", "tutu", cryptedPassword, birthCandidat, "zrretgenndu234", "2 rue des moulins", "63000", "Clermont-Ferrand");
		
					candidateRepository.save(candidat);}
				
			};

	}

}
