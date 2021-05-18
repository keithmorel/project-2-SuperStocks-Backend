package com.revature.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.revature.exception.UserNotFoundException;
import com.revature.template.MessageTemplate;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	// Global Exception Handler, will catch all exceptions that don't have a more specific exception handled by another method below
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MessageTemplate> myGlobalExceptionHandler(HttpServletRequest request, Exception ex) {

		logger.warn("Handling generic exception: " + request + "\n" + ex);
		return ResponseEntity.status(400).body(new MessageTemplate(ex.getMessage()));

	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<MessageTemplate> userNotFoundExceptionHandler(HttpServletRequest request, Exception ex) {

		logger.warn("Handling UserNotFoundException: " + request + "\n" + ex);
		return ResponseEntity.status(404).body(new MessageTemplate(ex.getMessage()));

	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<MessageTemplate> dataIntegrityExceptionHandler(HttpServletRequest request, Exception ex) {

		logger.warn("Handling DataIntegrityViolationException: " + request + "\n" + ex);
		return ResponseEntity.status(400).body(new MessageTemplate("Failed to update information. Another user already has that username or email."));

	}

}
