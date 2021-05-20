package com.projectreveal.reveal.backend.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import com.projectreveal.reveal.backend.exceptionhandler.ValidationException;
import com.projectreveal.reveal.backend.validation.ValidateToken;


//The below filter to work needs @ServletComponentScan annotation in application main function
@ConditionalOnProperty("reveal.frontend.login.filter.enable")
@WebFilter(urlPatterns = "/posts")
public class PostFilter implements Filter{

	@Autowired
	ValidateToken validateToken;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		boolean isValidated = false;
		System.out.println("the method is: " + httpRequest.getMethod());
		if(httpRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
			chain.doFilter(request, response);
		}
		else {
//			Cookie[] cookies = httpRequest.getCookies();
//			if(cookies!=null) {
//				System.out.println("cookies are not null");
//				for(Cookie cookie: cookies) {
//					if(cookie.getName().equals("access-token")) {
//						System.out.println("The cookie is : " + cookie.getValue());
//						try{
//							isValidated = validateToken.verifyToken(request, cookie.getValue());
//						}catch (ValidationException e) {
//							System.out.println("Caught validation exception");
//							isValidated = false;
//						}
//						break;
//					}
//						
//				}
//			}
			
			String token = httpRequest.getHeader("access-token");
			if(token!=null || !token.isEmpty()) {
				try{
					isValidated = validateToken.verifyToken(request, token);
				}catch (ValidationException e) {
					System.out.println("Caught validation exception");
					isValidated = false;
				}
			}
			if(isValidated) {
				System.out.println("successful validation");
				chain.doFilter(request, response);
			}
			else {
				System.out.println("Failed validation");
				httpResponse.sendError(HttpStatus.SC_UNAUTHORIZED, "Invalid token");

			}
				
		}
		
	
	}

	
	
}
