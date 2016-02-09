package com.grupoversia.gisae;

import com.grupoversia.gisae.util.Base64Coder;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * @author rfernandez
 *
 */
public class Base64CoderTest extends TestCase {
	private String textoDePrueba = "En un lugar de la mancha de cuyo nombre no puedo acordarme...";
	private String textoDePruebaCodificado = "RW4gdW4gbHVnYXIgZGUgbGEgbWFuY2hhIGRlIGN1eW8gbm9tYnJlIG5vIHB1ZWRvIGFjb3JkYXJtZS4uLg==";

	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public Base64CoderTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(Base64CoderTest.class);
	}
	
	public void testEncode() {
		assertNotNull(Base64Coder.encodeString(textoDePrueba));
		
		assertEquals(textoDePruebaCodificado, Base64Coder.encodeString(textoDePrueba));
	}
}
