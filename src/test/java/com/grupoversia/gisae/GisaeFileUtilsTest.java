package com.grupoversia.gisae;

import java.io.IOException;

import com.grupoversia.gisae.model.CobolProgram;
import com.grupoversia.gisae.model.CobolProgramException;
import com.grupoversia.gisae.util.GisaeFileUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * @author rfernandez
 *
 */
public class GisaeFileUtilsTest extends TestCase {
	private String ficheroOk = "files/20151209124359-RANDOM-OK";
	private String ficheroError = "files/20151209124359-RANDOM-ERROR";
	private String ficheroMalFormato = "files/20151209124359-RANDOM-MAL-FORMATO";
	private String ficheroVacio = "files/20151209124359-RANDOM-VACIO";

	private String ficheroTmp = "files/20151209124359-RANDOM-INPUT-PARAMS";

	private String resultadoOk = "fila 1\nfila 2\nfila 3\n...\nfila n";
	private String resultadoError = "Aqui se debería explicar la razón del problema encontrado en la ejecución del programa Cobol...";
	private int numeroLineas = 5;
	
	private String[] params = { "param-1", "param-2", "param-3", "param-n" };
	private String program = "PROGRAMA.gnt";

	private CobolProgram cobolProgram = new CobolProgram(program, params);

	private GisaeFileUtils gisaeFileUtils;

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public GisaeFileUtilsTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(GisaeFileUtilsTest.class);
	}

	/**
	 * Preparativos
	 */
	protected void setUp() throws Exception {
		this.gisaeFileUtils = new GisaeFileUtils();

		ClassLoader cl = getClass().getClassLoader();

		this.ficheroOk = cl.getResource(ficheroOk).getPath();
		this.ficheroError = cl.getResource(ficheroError).getPath();
		this.ficheroMalFormato = cl.getResource(ficheroMalFormato).getPath();
		this.ficheroVacio = cl.getResource(ficheroVacio).getPath();
		this.ficheroTmp = cl.getResource(".").getPath() + ficheroTmp;
	}

	/**
	 * Comprobar el resultado de un fichero correcto
	 * 
	 * @throws IOException
	 * @throws CobolProgramException
	 */
	public void testListadoRespuestaOk() throws IOException, CobolProgramException {
		assertNotNull(gisaeFileUtils.getResponseFromFile(ficheroOk));

		assertEquals(resultadoOk, gisaeFileUtils.getResponseFromFile(ficheroOk));
	}

	/**
	 * Comprobar el resultado de un fichero con error de Cobol
	 * 
	 * @throws IOException
	 * @throws CobolProgramException
	 */
	public void testRespuestaError() throws IOException, CobolProgramException {
		assertNotNull(gisaeFileUtils.getResponseFromFile(ficheroError));

		assertEquals(resultadoError, gisaeFileUtils.getResponseFromFile(ficheroError));
	}

	/**
	 * Comprobar el resultado de un fichero con formato erróneo
	 * 
	 * @throws IOException
	 * @throws CobolProgramException
	 */
	public void testListadoRespuestaMalFormato() throws IOException, CobolProgramException {
		Throwable e = null;

		try {
			assertNull(gisaeFileUtils.getResponseFromFile(ficheroMalFormato));
		} catch (Throwable ex) {
			e = ex;
		}

		assertTrue(e instanceof CobolProgramException);
	}

	/**
	 * Comprobar el resultado de un fichero vacío
	 * 
	 * @throws IOException
	 * @throws CobolProgramException
	 */
	public void testListadoRespuestaVacio() throws IOException, CobolProgramException {
		Throwable e = null;

		try {
			gisaeFileUtils.getResponseFromFile(ficheroVacio);
		} catch (Throwable ex) {
			e = ex;
		}

		assertTrue(e instanceof CobolProgramException);
	}

	/**
	 * Comprobar la generación de un fichero de parametros
	 * 
	 * @throws IOException
	 */
	public void testCreateExchangeFile() throws IOException {
		gisaeFileUtils.createExchangeFile(cobolProgram, ficheroTmp);

		assertTrue(true);
	}

	/**
	 * Crear un nombre de fichero único en base a la fecha del sistema y un
	 * número aleatorio que se añade como sufijo ejemplo: 20151210134903-34
	 * 
	 */
	public void testGenerateFileName() {
		assertNotNull(gisaeFileUtils.generateFileName());

		assertFalse(gisaeFileUtils.generateFileName().equals(gisaeFileUtils.generateFileName()));
	}

	/**
	 * Probar el borrado de un fichero
	 * 
	 * @throws IOException
	 */
	public void testDeleteFile() throws IOException {
		gisaeFileUtils.createExchangeFile(cobolProgram, ficheroTmp);

		assertTrue(true);

		gisaeFileUtils.deleteFile(ficheroTmp);

		assertTrue(true);
	}
	
	/**
	 * Comprobar el resultado de un fichero correcto que devuelve las lineas en un listado en lugar de en un unico string
	 * 
	 * @throws IOException
	 * @throws CobolProgramException
	 */
	public void testListadoRespuestaInArrayOk() throws IOException, CobolProgramException {
		assertNotNull(gisaeFileUtils.getResponseFromFileInArray(ficheroOk));

		assertEquals(numeroLineas, gisaeFileUtils.getResponseFromFileInArray(ficheroOk).size());
	}
	

}
