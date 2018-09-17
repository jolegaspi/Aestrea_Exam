package com.rocketmiles.cashregister.exceptions;

public class CashRegisterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Error handler for Cash register business logic 
	public CashRegisterException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	public CashRegisterException(String message) {
		super(message);
	}

}
