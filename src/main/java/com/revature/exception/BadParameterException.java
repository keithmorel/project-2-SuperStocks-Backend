package com.revature.exception;

public class BadParameterException extends Exception {

	public BadParameterException() {
	}

	public BadParameterException(String arg0) {
		super(arg0);
	}

	public BadParameterException(Throwable arg0) {
		super(arg0);
	}

	public BadParameterException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BadParameterException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
