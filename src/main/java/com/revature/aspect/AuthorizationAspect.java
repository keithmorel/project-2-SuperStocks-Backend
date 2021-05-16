package com.revature.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.revature.model.User;
import com.revature.template.MessageTemplate;

@Aspect
@Component
public class AuthorizationAspect {

	@Autowired
	private HttpServletRequest request;
	
	@Around("@annotation(com.revature.annotation.LoggedInOnly)")
	public ResponseEntity<Object> protectEndpointForLoggedInUsersOnly(ProceedingJoinPoint pjp) throws Throwable {
		
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loggedInUser") == null) {
			return ResponseEntity.status(401)
					.body(new MessageTemplate("You must be logged in to acces this resource"));
		}
		
		ResponseEntity<Object> result = (ResponseEntity<Object>) pjp.proceed(pjp.getArgs());
		return result;
		
	}
	
	@Around("@annotation(com.revature.annotation.AdminOnly)")
	public ResponseEntity<Object> protectEndpointForAdminUsersOnly(ProceedingJoinPoint pjp) throws Throwable {
		
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loggedInUser") == null) {
			return ResponseEntity.status(401)
					.body(new MessageTemplate("You must be logged in to acces this resource"));
		}
		User loggedIn = (User) session.getAttribute("loggedInUser");
		if (!loggedIn.getUserRole().getRoleName().equals("Admin")) {
			return ResponseEntity.status(401)
					.body(new MessageTemplate("You must be an admin user to access this resource"));
		}
		
		ResponseEntity<Object> result = (ResponseEntity<Object>) pjp.proceed(pjp.getArgs());
		return result;
		
	}

}
