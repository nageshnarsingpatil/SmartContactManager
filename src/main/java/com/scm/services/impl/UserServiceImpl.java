package com.scm.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.helper.Helper;
import com.scm.helper.ResourceNotFoundException;
import com.scm.repositories.UserRepo;
import com.scm.services.EmailService;
import com.scm.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private EmailService emailService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Override
	public User saveUser(User user) {
		//user id:have to generate
		//set the encrypted password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		//set the role
		//user.setRoleList(List.of(AppConstants.Role_USER));
		
		String userId=UUID.randomUUID().toString();
		user.setUserId(userId);

		
		String eamilToken=UUID.randomUUID().toString();
		user.setEmailToken(eamilToken);
		
		User savedUser=  userRepo.save(user);
		String emailLink=Helper.getLinkForEmailVerification(eamilToken);
		
		emailService.sendEmail(savedUser.getEmail(),"Verify Account:Smart Conatct Manager", emailLink);
		
		return savedUser;
	}

	@Override
	public Optional<User> getUserById(String id) {
		return userRepo.findById(id);
	}

	@Override
	public Optional<User> updateUser(User user) {

		User user2= userRepo.findById(user.getUserId()).orElseThrow(()->new ResourceNotFoundException("user not found"));
		//update user from user2
		user2.setName(user.getName());
		user2.setEmail(user.getEmail());
		user2.setPassword(user.getPassword());
		user2.setAbout(user.getAbout());
		user2.setPhoneNumber(user.getPhoneNumber());
		user2.setProfilePic(user.getProfilePic());
		user2.setEnabled(user.isEnabled());
		user2.setEmailVerified(user.isEmailVerified());
		user2.setPhoneVerified(user.isPhoneVerified());
		user2.setProvider(user.getProvider());
		user2.setProviderUserId(user.getProviderUserId());
		//save the user i n database
		User save=userRepo.save(user2);
		return Optional.ofNullable(save);


	}

	@Override
	public void deleteUser(String id) {
		User user2= userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("user not found"));
		 userRepo.delete(user2);
	}

	@Override
	public boolean isUserExist(String userId) {
		User user2= userRepo.findById(userId).orElse(null);
		return user2!=null ?true:false;
	}

	@Override
	public boolean isUserExistByEmail(String email) {
	User user=	userRepo.findByEmail(email).orElse(null);
	return user!=null ?true:false;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		//this code is for if email is null then it throws exception
		//return userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user not found"));
	    
		//using below code we can at the place of exception we can write null
	
		return userRepo.findByEmail(email).orElseThrow(null);
	}

}
