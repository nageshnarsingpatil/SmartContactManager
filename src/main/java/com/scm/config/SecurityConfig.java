package com.scm.config;

import java.awt.TrayIcon.MessageType;
import java.io.IOException;

import org.apache.hc.core5.http.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.scm.services.SecurityCustomUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Configuration

public class SecurityConfig {
	
	
	//create user and login using java code in memory service
//	@Bean
//	public UserDetailsService userDetailsService() {
		
	    //using below method we can create user manually
		//UserDetails user1=User
		//		.withDefaultPasswordEncoder()
		//		.username("nagesh")
		//		.password("nagesh")
		//		.roles("Admin")
		//		.build();
		
		//UserDetails user2=User			
		//		.withDefaultPasswordEncoder()
		//		.username("nageshp")
		//		.password("nageshp")
		//		.roles("User")
		//		.build();
		//var inMemoryUserDetailsManager= new InMemoryUserDetailsManager(user1,user2);
		//return inMemoryUserDetailsManager;
	
	
	//}
	@Autowired
	private SecurityCustomUserDetailService userDetailService;
	
	@Autowired
	private OAuthAuthenticationSuccessHandler handler;
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		//user details service object
		daoAuthenticationProvider.setUserDetailsService(userDetailService);
		
		//password Encoder object
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
	    return daoAuthenticationProvider;
	
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		//configuration
		
		httpSecurity.authorizeHttpRequests(authorize->{
			//authorize below pages to access without login
			//authorize.requestMatchers("/home","/register","/service","/about","/contact").permitAll();
			
			//using below we can restict the url to aceess without login
			//these urls are private
			authorize.requestMatchers("/user/*").authenticated();
			
			//using below page we can give access that access the below pages without login also
			//this is used to urls are public
			authorize.anyRequest().permitAll();
		});
		
		//form default login
		//httpSecurity.formLogin(Customizer.withDefaults());
		//form login manually form
		httpSecurity.formLogin(formLogin->{
			//below line is used to tel  that our url for login form
			formLogin.loginPage("/login");
			formLogin.loginProcessingUrl("/authenticate");
			formLogin.successForwardUrl("/user/profile");
			//formLogin.failureForwardUrl("/login?error=true");
			formLogin.usernameParameter("email");
			formLogin.passwordParameter("password");
			
			
			//below code is for handling the success and failure
			//formLogin.failureHandler(new AuthenticationFailureHandler() {

			//	@Override
			//	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			//			AuthenticationException exception) throws IOException, ServletException {
					// TODO Auto-generated method stub
					
			//	}
				
				
			//});
			
			//formLogin.successHandler(new AuthenticationSuccessHandler() {

			//	@Override
			//	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			//			Authentication authentication) throws IOException, ServletException {
					// TODO Auto-generated method stub
					
			//	}
				
		//	});
			
			formLogin.failureHandler(new AuthenticationFailureHandler() {

				@Override
				public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
						AuthenticationException exception) throws IOException, ServletException {
					if(exception instanceof DisabledException) {
						
						HttpSession session=request.getSession();
						session.setAttribute("message", "User is Disabled,Email with verification link send on your eamil id!!");
						response.sendRedirect("/login");
					}else {
					response.sendRedirect("/login?error=true");
					}
				}
				
			});
		});
		httpSecurity.csrf(AbstractHttpConfigurer::disable);
		httpSecurity.logout(logoutForm->{
			logoutForm.logoutUrl("/logout");
			logoutForm.logoutSuccessUrl("/login?logout=true");
		});
		
		//oauth configuration
		
		
		//httpSecurity.oauth2Login(Customizer.withDefaults());
		 httpSecurity.oauth2Login(oauth->{
		 
		  oauth.loginPage("/login"); 
		  oauth.successHandler(handler);
		  });
		 
		
		return httpSecurity.build();
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
