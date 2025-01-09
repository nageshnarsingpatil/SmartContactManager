package com.scm.services;

import java.util.List;
import java.util.Optional;

import com.scm.entities.User;

public interface UserService {

	//in the service we can write the methods of user and business logic
	//we can write business logic inside UserServiceImpl class
	User saveUser(User user);
	//optional is a data type that tells us that user is present or not
	Optional<User> getUserById(String id);
	Optional<User> updateUser(User user);
	void deleteUser(String id);
	boolean isUserExist(String userId);
	boolean isUserExistByEmail(String email);
	List<User> getAllUsers();
	User getUserByEmail(String email);
}
