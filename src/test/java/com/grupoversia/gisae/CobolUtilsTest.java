package com.grupoversia.gisae;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.grupoversia.gisae.model.CobolProgram;
import com.grupoversia.gisae.model.CobolProgramException;
import com.grupoversia.gisae.util.CobolUtils;
import com.grupoversia.gisae.ws.CallCobolProgram;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * @author rfernandez
 *
 */
public class CobolUtilsTest extends TestCase {

	private static Log log = LogFactory.getLog(CallCobolProgram.class);

	private CobolUtils cobolUtils;
	private CobolProgram cobolProgram;

	// A tener en cuenta que para que funcionen estos test en entorno Windows es
	// necesario crear otros
	// ficheros de linea de comandos
	private String scriptOk = "scripts/wscobol-prueba-OK.sh";
	private String scriptKo = "scripts/wscobol-prueba-KO.sh";
	private String intercambioPath = "scripts";

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public CobolUtilsTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(CobolUtilsTest.class);
	}

	protected void setUp() throws Exception {
		this.cobolUtils = new CobolUtils();

		String[] params = { "param-1", "param-2", "param-3", "param-n" };

		this.cobolProgram = new CobolProgram("PROGRAMA-COBOL", params);

		ClassLoader cl = getClass().getClassLoader();

		this.scriptOk = cl.getResource(scriptOk).getPath();
		this.scriptKo = cl.getResource(scriptKo).getPath();
		this.intercambioPath = cl.getResource(intercambioPath).getPath();

		File scriptOkFile = new File(scriptOk);
		if (scriptOkFile.exists()) {
			log.debug("Is file executable: " + scriptOkFile.canExecute());
			log.debug("Is file writable : " + scriptOkFile.canWrite());
			log.debug("Is file readable : " + scriptOkFile.canRead());
		}
		scriptOkFile.setReadable(true);
		scriptOkFile.setWritable(true);
		scriptOkFile.setExecutable(true);
		
		File scriptKoFile = new File(scriptKo);

		scriptKoFile.setReadable(true);
		scriptKoFile.setWritable(true);
		scriptKoFile.setExecutable(true);
	}

	/**
	 * Comprobar el funcionamiento cuando se genera un fichero de resultados correcto
	 * 
	 * @throws CobolProgramException
	 */
	public void testCallCobolProgramOk() throws CobolProgramException {
		assertNotNull(this.cobolUtils.callCobolProgram(cobolProgram, scriptOk, intercambioPath));
	}

	/**
	 * Comprobar el funcionamiento cuando el fichero de resultados tiene formato erróneo
	 * 
	 */
	public void testCallCobolProgramKo() {
		Throwable e = null;
		
		try {
			this.cobolUtils.callCobolProgram(cobolProgram, scriptKo, intercambioPath);
		} catch (Throwable ex) {
			e = ex;
		}
		assertTrue(e instanceof CobolProgramException);
	}

}
