package com.scm.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helper.Helper;
import com.scm.helper.message;
import com.scm.helper.messageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

	@Autowired
	private ContactService contactService;
	
	@Autowired
	private UserService userService;
	

	@Autowired
	private ImageService imageService;
	
	//add contact page handler
	@RequestMapping("/add")
	public String addContactView(Model model) {
		ContactForm contactForm=new ContactForm();
		contactForm.setName("nagesh patil");
		contactForm.setEmail("nagesh@gmail.com");
		contactForm.setFavorite(true);
		model.addAttribute("contactForm",contactForm);
		return "user/add_contacts";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String saveContact(@Valid @ModelAttribute ContactForm contactForm,BindingResult result,Authentication authentication,HttpSession session,@RequestParam("profileImage") MultipartFile profileImage) {
		
		
		
		//validate 
		//if(result.hasErrors()) {
			/*
			 * session.setAttribute("message",
			 * message.builder().content("failed to add Contact").type(messageType.red).
			 * build());
			 */
		//	return "user/add_contacts";
		//}
		
		//process the form data
		String username=Helper.getEmailOfLoggedInUser(authentication);
		
		User user=userService.getUserByEmail(username);
		
		
		Contact contact=new Contact();
		contact.setName(contactForm.getName());
		contact.setFavorite(contactForm.isFavorite());
		contact.setEmail(contactForm.getEmail());
		contact.setPhoneNumber(contactForm.getPhoneNumber());
		contact.setAddress(contactForm.getAddress());
		contact.setDescription(contactForm.getDescription());
		contact.setWebsiteLink(contactForm.getWebsiteLink());
		contact.setLinkedInLink(contactForm.getLinkedInLink());
		if(contactForm.getProfileImage() !=null && !contactForm.getProfileImage().isEmpty()) {
		//use profile image process
		
				//System.out.println("file information:"+contactForm.getProfileImage().getOriginalFilename());
				//file upload code
		String fileUrl=imageService.uploadImage(contactForm.getProfileImage());
		contact.setPicture(fileUrl);
		}
		contact.setUser(user);
		
		contactService.save(contact);
		System.out.println(contactForm);
		
		session.setAttribute("message", "Contact Added Successfully");
		return "redirect:/user/contacts/add";
	}
	
	//view contacts 
	@RequestMapping
	public String viewContacts(
			@RequestParam(value="page",defaultValue="0") int page,
			@RequestParam(value="size",defaultValue="5") int size,
			@RequestParam(value="sortBy",defaultValue="name") String sortBy,
			@RequestParam(value="direction",defaultValue="asc") String direction,
			Authentication authentication,Model model) {
		//load all the user contacts
		String username=Helper.getEmailOfLoggedInUser(authentication);
		User user=userService.getUserByEmail(username);
		Page<Contact> contacts=    contactService.getByUser(user,page,size,sortBy,direction);
		
		model.addAttribute("contacts", contacts);
		
		
		return "user/contacts";
		}
	
	
	//serach contacts
	@RequestMapping("/search")
	public String searchHandler(
			@RequestParam("field") String field,
			@RequestParam("keyword") String value,
			@RequestParam(value="size",defaultValue="10") int size, 
			@RequestParam(value="page",defaultValue="0") int page,
			@RequestParam(value="sortBy",defaultValue="name") String sortBy,
			@RequestParam(value="direction",defaultValue="asc") String direction,
			Model model,
			Authentication authentication
			) {
		
		System.out.println(field);
		System.out.println(value);
		
		var user=userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
		
		
		Page<Contact> pageContact=null;
		if(field.equalsIgnoreCase("name")) {
			 pageContact=  contactService.searchByName(value, size, page, sortBy, direction,user);
		}else if(field.equalsIgnoreCase("email")) {
			pageContact=  contactService.searchByEmail(value, size, page, sortBy, direction,user);
						
		}else if(field.equalsIgnoreCase("phone")) {
			pageContact=  contactService.searchByPhone(value, size, page, sortBy, direction,user);
						
		}
		if (pageContact == null || pageContact.getContent().isEmpty()) {
	        model.addAttribute("errorMessage", "No results found for your search.");
	        return "user/search"; // You can return to the same page with an error message
	    }
		model.addAttribute("pageContact",pageContact);
		return "user/search";
	}
	
	//delete contact handler
	@RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") String contactId,
    		HttpSession session
    		) {
		
		contactService.delete(contactId);
		session.setAttribute("message", "Contact Deleted Successfully");
		return "redirect:/user/contacts";
    	
    }
	
	//update contact form view  handler 
	@GetMapping("/view/{contactId}")
	public String updateContactFormView(
			@PathVariable("contactId") String contactId,Model model
			) {
		var contact=contactService.getById(contactId);
		
		ContactForm contactForm=new ContactForm();
		contactForm.setName(contact.getName());
		contactForm.setEmail(contact.getEmail());
		contactForm.setPhoneNumber(contact.getPhoneNumber());
		contactForm.setAddress(contact.getAddress());
		contactForm.setDescription(contact.getDescription());
		contactForm.setFavorite(contact.isFavorite());
		contactForm.setLinkedInLink(contact.getLinkedInLink());
		contactForm.setWebsiteLink(contact.getWebsiteLink());
		contactForm.setPitcture(contact.getPicture());
		
		model.addAttribute("contactForm",contactForm);
		model.addAttribute("contactId",contactId);
		return "user/update_contact_view";
	}
	
	@RequestMapping(value="/update/{contactId}",method=RequestMethod.POST)
	public String updateContact(
			@PathVariable("contactId") String contactId,
			@ModelAttribute ContactForm contactForm,
			BindingResult bindingResult,
			Model model
			) {
		
		if(bindingResult.hasErrors()) {
			return "/user/update_contact_view";
		}
		
		//update the contact 
		//var contact=new Contact();
		var contact=contactService.getById(contactId);
		contact.setId(contactId);
		contact.setName(contactForm.getName());
		contact.setEmail(contactForm.getEmail());
		contact.setPhoneNumber(contactForm.getPhoneNumber());
		contact.setAddress(contactForm.getAddress());
		contact.setDescription(contactForm.getDescription());
		contact.setFavorite(contactForm.isFavorite());
		contact.setLinkedInLink(contactForm.getLinkedInLink());
		contact.setWebsiteLink(contactForm.getWebsiteLink());
		
		
		//process image 
		//the below if block will be execute only after image will be updated 
		if(contactForm.getProfileImage() !=null && !contactForm.getProfileImage().isEmpty()) {
			String fileName=UUID.randomUUID().toString();
			String imageURL=imageService.uploadImage(contactForm.getProfileImage());
			contact.setCloudinaryImagePublicId(fileName);
			contact.setPicture(imageURL);
			contactForm.setPitcture(imageURL);
			
		}
		var updatedCon=contactService.update(contact);
		model.addAttribute("message","Contact Successfully Updated");
		return "redirect:/user/contacts/view/"+contactId;
	}
	
}

    
