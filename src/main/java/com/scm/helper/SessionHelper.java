package com.scm.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;



@Component
public class SessionHelper {

	//this is used to remove the message from session
	public static void removeMessage() {
		try {
		HttpSession session=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
		
		session.removeAttribute("message");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
