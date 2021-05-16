package com.revature.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.revature.exception.UserNotFoundException;
import com.revature.template.MessageTemplate;

@ControllerAdvice
public class GlobalExceptionHandler {

	// Global Exception Handler, will catch all exceptions that don't have a more specific exception handled by another method below
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MessageTemplate> MyGlobalExceptionHandler(HttpServletRequest request, Exception ex) {

		System.out.println("****IN GLOBAL EXCEPTION HANDLER****");
		return ResponseEntity.status(400).body(new MessageTemplate(ex.getMessage()));

	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<MessageTemplate> UserNotFoundExceptionHandler(HttpServletRequest request, Exception ex) {

		System.out.println("****IN USERNOTFOUND EXCEPTION HANDLER****");
		return ResponseEntity.status(404).body(new MessageTemplate(ex.getMessage()));

	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<MessageTemplate> DataIntegrityExceptionHandler(HttpServletRequest request, Exception ex) {

		System.out.println("****IN DATAINTEGRITY EXCEPTION HANDLER****");
		return ResponseEntity.status(400).body(new MessageTemplate("Failed to update information. Users must have unique username and email."));

	}

}
