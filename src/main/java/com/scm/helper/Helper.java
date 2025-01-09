package com.scm.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

	public static String getEmailOfLoggedInUser(Authentication authentication) {
		
		//AuthenticationPrincipal principal=(AuthenticationPrincipal)authentication.getPrincipal();
		if(authentication instanceof OAuth2AuthenticationToken) {
			//how to fetch email if user login using google
			String username="";
			var oAuth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
			var clientId=oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
			var oauth2User=(OAuth2User)authentication.getPrincipal();
			username=oauth2User.getAttribute("email").toString();
			System.out.println(username);
		    return username;
		
		}else {
			//how to fetch email if user login using username and password
			return authentication.getName();
		}
		
		
		
	}

   public static String getLinkForEmailVerification( String emailToken) {
	   
	   
	   String link="http://localhost:8083/auth/verify-email?token="+emailToken;
	   return link;
   }
}
