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
	
	private String sessionAttr = "loggedInUser";
	
	@SuppressWarnings("unchecked")
	@Around("@annotation(com.revature.annotation.LoggedInOnly)")
	public ResponseEntity<Object> protectEndpointForLoggedInUsersOnly(ProceedingJoinPoint pjp) throws Throwable {
		
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute(sessionAttr) == null) {
			return ResponseEntity.status(401)
					.body(new MessageTemplate("You must be logged in to acces this resource"));
		}
		
		return (ResponseEntity<Object>) pjp.proceed(pjp.getArgs());
		
	}
	
	@SuppressWarnings("unchecked")
	@Around("@annotation(com.revature.annotation.AdminOnly)")
	public ResponseEntity<Object> protectEndpointForAdminUsersOnly(ProceedingJoinPoint pjp) throws Throwable {
		
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute(sessionAttr) == null) {
			return ResponseEntity.status(401)
					.body(new MessageTemplate("You must be logged in to acces this resource"));
		}
		User loggedIn = (User) session.getAttribute(sessionAttr);
		if (!loggedIn.getUserRole().getRoleName().equals("Admin")) {
			return ResponseEntity.status(401)
					.body(new MessageTemplate("You must be an admin user to access this resource"));
		}
		
		return (ResponseEntity<Object>) pjp.proceed(pjp.getArgs());
		
	}

}
