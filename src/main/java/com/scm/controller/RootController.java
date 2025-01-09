package com.scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entities.User;
import com.scm.helper.Helper;
import com.scm.services.UserService;

@ControllerAdvice
public class RootController {

	private Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;

	@ModelAttribute
	public void addLoggedInUserInformation(Model model,Authentication authentication) {
		if(authentication ==null) {
			return;
		}
		
		//adding logged information in the model
		String email=Helper.getEmailOfLoggedInUser(authentication);
		//String name=principal.getName();
		logger.info("user logger in:{} ",email);
		//get username from database
		
		User user=userService.getUserByEmail(email);
			System.out.println(user);			
			model.addAttribute("loggedInUser", user);
			System.out.println(user.getName());
		
		
		
	}
}
