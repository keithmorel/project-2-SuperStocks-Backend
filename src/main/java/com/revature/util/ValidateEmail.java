package com.revature.util;

import org.apache.commons.validator.routines.EmailValidator;

public class ValidateEmail {
	
	public static boolean validateEmail(String email) {
		
		EmailValidator validator = EmailValidator.getInstance();
		
		return validator.isValid(email);
		
	}
	
	private ValidateEmail() {
	    throw new IllegalStateException("Utility class");
	}

}
