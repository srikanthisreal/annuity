package com.lendico.annuity.exceptionhandling;

public class ValidationException extends RuntimeException {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = -6877552174017920822L;

	private String message;

	public ValidationException() {
		super();
	}

	public ValidationException(String message) {
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
