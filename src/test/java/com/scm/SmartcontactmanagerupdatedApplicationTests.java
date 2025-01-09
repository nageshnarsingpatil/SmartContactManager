package com.scm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scm.services.EmailService;

@SpringBootTest
class SmartcontactmanagerupdatedApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmailService emailService;
	@Test
	void sendEmailTest() {
		emailService.sendEmail("nageshpatil10000@gmail.com", "this is for testing the email", "this is scm project working on email service");
	}
}
