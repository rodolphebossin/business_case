package com.humanbooster.Business_case_admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.humanbooster.Business_case_admin.services.MyDatabaseUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public WebSecurityConfiguration(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }
    
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
			.antMatchers(
					"/js/**",
					"/css/**",
					"/images/**",
					"/webjars/**").permitAll()
			.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
			.antMatchers("/candidat/**").hasAuthority("ROLE_CANDIDAT")
			.antMatchers("/chefDeProjet/**").hasAuthority("ROLE_CHEFDEPROJET")
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
            .loginProcessingUrl("/login-processing")
            .successHandler(authenticationSuccessHandler)
            .permitAll()
			.and()
			.logout()
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login?logout")
			.permitAll();				
	}
	
	
	
	@Bean
	public UserDetailsService userDetailsService() {
	    return new MyDatabaseUserDetailsService(); // (1)
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	



    

}
