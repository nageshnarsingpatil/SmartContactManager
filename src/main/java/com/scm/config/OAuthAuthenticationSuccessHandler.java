package com.scm.config;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UserRepo userRepo;
	
	Logger logger=LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		logger.info("OAuthAuthenticationSuccessHandler");
		//save the data into the database
		DefaultOAuth2User user2=(DefaultOAuth2User)authentication.getPrincipal();
		//logger.info(user.getName());
		
		//user.getAttributes().forEach((key,value)->{
		//	logger.info("{}=>{}",key,value);
		//});
		
		//logger.info(user.getAuthorities().toString());
		//save the data into the database
		String email=user2.getAttribute("email").toString();
		String name=user2.getAttribute("name").toString();
		String pitcture=user2.getAttribute("picture").toString();
		
		//create user and save to database
		User user1=new User();
		user1.setEmail(email);
		user1.setName(name);
		user1.setProfilePic(pitcture);
		user1.setPassword("password");
		user1.setUserId(UUID.randomUUID().toString());
		user1.setProvider(Providers.GOOGLE);
		user1.setEnabled(true);
		user1.setEmailVerified(true);
	//	user1.setProviderUserId(user.getName());
	//	user1.setRoleList(List.of(AppConstants.Role_USER));
		user1.setAbout("this account is created using google");
		
		User user3=userRepo.findByEmail(email).orElse(null);
		if(user3==null) {
			userRepo.save(user1);
			logger.info("user saved"+email);
		}
		//we can redirect using below two ways
	//1st way using sendredirect
		
		response.sendRedirect("/user/profile");
		
		//2nd way using defaultRedirectStratergy
		
		//new DefaultRedirectStrategy.sendRedirect(request,response,"/user/profile");
		
		
		
	}

}
