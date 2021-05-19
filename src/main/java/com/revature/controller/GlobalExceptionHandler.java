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
	
	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	String exceptionFormat = "Exception: %s";

	// Global Exception Handler, will catch all exceptions that don't have a more specific exception handled by another method below
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MessageTemplate> myGlobalExceptionHandler(HttpServletRequest request, Exception ex) {

		String formattedString = String.format(" Handling generic exception: %s", request);
		logger.warn(formattedString);
		
		String strGlobalException = String.format(exceptionFormat, ex);
		logger.warn(strGlobalException);
		
		return ResponseEntity.status(400).body(new MessageTemplate(ex.getMessage()));

	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<MessageTemplate> userNotFoundExceptionHandler(HttpServletRequest request, Exception ex) {

		String formattedString = String.format(" Handling UserNotFoundException: %s", request);
		logger.warn(formattedString);
		
		String strUserException = String.format(exceptionFormat, ex);
		logger.warn(strUserException);
		
		return ResponseEntity.status(404).body(new MessageTemplate(ex.getMessage()));

	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<MessageTemplate> dataIntegrityExceptionHandler(HttpServletRequest request, Exception ex) {

		String formattedString = String.format("Handling DataIntegrityViolationException: %s to %s.", request.getMethod(), request.getRequestURI());
		logger.warn(formattedString);
		
		String strDataException = String.format(exceptionFormat, ex);
		logger.warn(strDataException);
		
		return ResponseEntity.status(400).body(new MessageTemplate("Duplicate Entry to table."));

	}

}
