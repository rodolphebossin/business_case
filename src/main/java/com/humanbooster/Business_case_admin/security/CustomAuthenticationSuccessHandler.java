package com.humanbooster.Business_case_admin.security;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        
        
        if (roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/admins/welcome");
        } else if (roles.contains("ROLE_CANDIDAT")) {
            httpServletResponse.sendRedirect("/candidats/" + httpServletRequest.getParameter("techTest") + "/" + httpServletRequest.getParameter("infoco") );
        } else if (roles.contains("ROLE_CHEFDEPROJET")) {
            httpServletResponse.sendRedirect("/chefsDeProjet/welcome");
        } else {
        	httpServletResponse.sendRedirect("/login");
        }
    }
}
