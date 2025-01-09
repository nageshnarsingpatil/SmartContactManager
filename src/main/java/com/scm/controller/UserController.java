package com.scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	
	//user dashboard page 	
	//@PostMapping("/dashboard")
	@RequestMapping(value = "/dashboard", method = {RequestMethod.GET, RequestMethod.POST})
	public String userDashboard() {
		return "user/dashboard";
	}
	
	//user profile page handler
	@RequestMapping(value = "/profile", method = {RequestMethod.GET, RequestMethod.POST})
	public String userProfile(Model model,Authentication authentication) {
		
		return "user/profile";
	}
	//user add contact page
	
	//user view contacts
	
	//user edit contact
	
	//user delete contact
	
}
