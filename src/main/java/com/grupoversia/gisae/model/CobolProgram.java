package com.grupoversia.gisae.model;

/**
 * 
 * @author rfernandez
 *
 */
public class CobolProgram {

	private String program;
	private String[] params;

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String[] getParams() {
		return params;
	}

	public void setParams(String[] params) {
		this.params = params;
	}

	public CobolProgram(String program, String[] params) {
		super();
		this.program = program;
		this.params = params;
	}

	public CobolProgram() {
		super();
	}

}
