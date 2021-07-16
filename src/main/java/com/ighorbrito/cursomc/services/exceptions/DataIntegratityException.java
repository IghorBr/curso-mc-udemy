package com.ighorbrito.cursomc.services.exceptions;

public class DataIntegratityException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DataIntegratityException(String msg) {
		super(msg);
	}

	public DataIntegratityException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
