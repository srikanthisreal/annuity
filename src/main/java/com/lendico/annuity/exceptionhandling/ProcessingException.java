package com.lendico.annuity.exceptionhandling;

public class ProcessingException extends RuntimeException {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 5196210063772894056L;

	private String message;

	public ProcessingException() {
		super();
	}

	public ProcessingException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
