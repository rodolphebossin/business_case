package com.humanbooster.Business_case_admin.services;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	@Autowired 
	private JavaMailSender javaMailSender;
	
	public void sendEmail(String to, String body, String header) throws MessagingException, UnsupportedEncodingException {
		
		MimeMessage mimemessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimemessage, true);	
		
		try {
			helper.setFrom("contact@humanbooster.com", "HUMANBooster");
			helper.setTo(to);
			helper.setSubject(header);
			helper.setText(body, true);
			
			ClassPathResource resource = new ClassPathResource("static/images/humanboosterLogoScaled.jpg");
			helper.addInline("logoImage", resource);;
			
			javaMailSender.send(mimemessage);
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		

	}

}
 