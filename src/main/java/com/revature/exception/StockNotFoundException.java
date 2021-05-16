package com.revature.exception;

public class StockNotFoundException extends Exception {

	public StockNotFoundException() {
	}

	public StockNotFoundException(String arg0) {
		super(arg0);
	}

	public StockNotFoundException(Throwable arg0) {
		super(arg0);
	}

	public StockNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public StockNotFoundException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
