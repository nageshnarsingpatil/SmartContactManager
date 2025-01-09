package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String index() {
		return "redirect:/home";
	}

	@RequestMapping("/home")
	public String home(Model m) {
		m.addAttribute("name","nagesh");
		return "home";
	}

	//about handler or route
	@RequestMapping("/about")
	public String about() {
		return "about";
	}

	//services handler or route
	@RequestMapping("/service")
	public String service() {
		return "service";
	}

	//contact handler or route
	@RequestMapping("/contact")
	public String contact() {
		return "contact";
	}

	
		
	//login handler or route
	@GetMapping("/login")
	public String login() {
		return "login";
	}


	//register handler or route
	@GetMapping("/register")
	public String register(Model model) {
		UserForm userForm=new UserForm();
		model.addAttribute("userForm", userForm);
		return "register";
	}

	//processing register
	@PostMapping("/do-register")
	public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult result , HttpSession session) {

		//fethch the form data
		System.out.println(userForm);
		/*
		 * userForm.setProfilePic(
		 * "https://static.vecteezy.com/system/resources/thumbnails/001/840/612/small/picture-profile-icon-male-icon-human-or-people-sign-and-symbol-free-vector.jpg"
		 * );
		 */
		//validate the data

		if(result.hasErrors()) {
			return "register";
		}
		
		
		//save to database
		/*
		 * User user=User.builder() .name(userForm.getName())
		 * .email(userForm.getEmail()) .password(userForm.getPassword())
		 * .about(userForm.getAbout()) .phoneNumber(userForm.getPhoneNumber())
		 * .profilePic(
		 * "https://static.vecteezy.com/system/resources/thumbnails/001/840/612/small/picture-profile-icon-male-icon-human-or-people-sign-and-symbol-free-vector.jpg")
		 * 
		 * .build();
		 */
		User user=new User();
		user.setName(userForm.getName());
		user.setEmail(userForm.getEmail());
		user.setPassword(userForm.getPassword());
		user.setAbout(userForm.getAbout());
		user.setPhoneNumber(userForm.getPhoneNumber());
		user.setEnabled(false);
		user.setProfilePic( "https://static.vecteezy.com/system/resources/thumbnails/001/840/612/small/picture-profile-icon-male-icon-human-or-people-sign-and-symbol-free-vector.jpg");
		userService.saveUser(user);

		//message successfull or not
		//add the message
		/* message.builder().content("registration Successfull").type(Message) */
		session.setAttribute("message","registration Successfully");
		
		//redirect to register page


		return "redirect:/register";
	}
}
