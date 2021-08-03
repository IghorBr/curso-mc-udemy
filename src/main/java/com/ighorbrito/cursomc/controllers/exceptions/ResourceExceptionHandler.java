package com.ighorbrito.cursomc.controllers.exceptions;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ighorbrito.cursomc.services.exceptions.AuthorizationException;
import com.ighorbrito.cursomc.services.exceptions.DataIntegratityException;
import com.ighorbrito.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		StandardError stdError = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(),
				sdf.format(System.currentTimeMillis()));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(stdError);
	}

	@ExceptionHandler(DataIntegratityException.class)
	public ResponseEntity<StandardError> dataIntegratityViolation(DataIntegratityException e,
			HttpServletRequest request) {
		StandardError stdError = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				sdf.format(System.currentTimeMillis()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(stdError);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validationException(MethodArgumentNotValidException e,
			HttpServletRequest request) {
		
		
		ValidationError vldError = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação",
				sdf.format(System.currentTimeMillis()));
		
		for (FieldError error : e.getBindingResult().getFieldErrors()) {
			vldError.addError(error.getField(),	error.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(vldError);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request) {
		StandardError stdError = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(),
				sdf.format(System.currentTimeMillis()));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(stdError);
	}

}
