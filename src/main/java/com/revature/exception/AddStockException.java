package com.revature.exception;

public class AddStockException extends Exception {

	public AddStockException() {
	}

	public AddStockException(String message) {
		super(message);
	}

	public AddStockException(Throwable cause) {
		super(cause);
	}

	public AddStockException(String message, Throwable cause) {
		super(message, cause);
	}

	public AddStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
