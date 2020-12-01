package com.lendico.annuity.exceptionhandling;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.lendico.annuity.controller.AnnuityController;

@ControllerAdvice
public class AnnuityControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ ValidationException.class })
	public final ResponseEntity<ResponseMessage> inputValidationException(ValidationException ex, WebRequest request) {
		ResponseMessage error = new ResponseMessage(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ProcessingException.class)
	public final ResponseEntity<ResponseMessage> handleProcessingException(ProcessingException ex, WebRequest request) {
		ResponseMessage error = new ResponseMessage("Error while calculating Repayments " + ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ArithmeticException.class)
	public final ResponseEntity<ResponseMessage> handleArithmeticException(Exception ex, WebRequest request) {
		ResponseMessage error = new ResponseMessage("Arithematic exception " + ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler
	public final ResponseEntity<ResponseMessage> handleAllExceptions(Exception ex, WebRequest request) {
		ResponseMessage error = new ResponseMessage(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError apiError = new ApiError(BAD_REQUEST);
		apiError.setMessage("Validation error");
		apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
		apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
		return buildResponseEntity(apiError);
	}

	
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex) {
		ApiError apiError = new ApiError(BAD_REQUEST);
		apiError.setMessage("Validation error");
		apiError.addValidationErrors(ex.getConstraintViolations());
		return buildResponseEntity(apiError);
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}
