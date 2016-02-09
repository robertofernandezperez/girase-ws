package com.grupoversia.gisae.model;

/**
 * 
 * @author rfernandez
 *
 */
public class CobolProgramException extends Exception {

	private static final long serialVersionUID = 1L;

	private String errorCode = "Unknown_Exception";

	public CobolProgramException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

}
