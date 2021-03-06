package com.grupoversia.gisae;

import java.io.IOException;

import com.grupoversia.gisae.util.Base64Coder;
import com.grupoversia.gisae.util.GisaeStringUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * @author rfernandez
 *
 */
public class GisaeStringUtilsTest extends TestCase {
	private String textoDePrueba = "En un lugar de la mancha de cuyo nombre no puedo acordarme...En un lugar de la mancha de cuyo nombre no puedo acordarme...";
	private GisaeStringUtils gisaeStringUtils;
	private String resultado = "�       s�S(�S�)MO,RHIU�IT�M�K�Hq�K+���s��R��BAijJ�Bbr~QJbQn����+%��l=�z   ";

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public GisaeStringUtilsTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(GisaeStringUtilsTest.class);
	}

	protected void setUp() throws Exception {
		this.gisaeStringUtils = new GisaeStringUtils();
	}

	public void testCompress() throws IOException {
		assertNotNull(gisaeStringUtils.compress(textoDePrueba));

		assertFalse(gisaeStringUtils.compress(textoDePrueba).toString().length() > textoDePrueba.length());

		assertEquals(resultado, new String(gisaeStringUtils.compress(textoDePrueba)));
	}

	public void testDecompress() throws IOException {
		assertNotNull(gisaeStringUtils.compress(textoDePrueba));

		assertEquals(textoDePrueba, gisaeStringUtils.decompress(gisaeStringUtils.compress(textoDePrueba)));
	}

	public void testCompressAndDecompressWithBase64() throws IOException {
		String texto = "resultado1\nresultado2\nresultado3";

		GisaeStringUtils gsu = new GisaeStringUtils();

		byte[] textoComprimido = gsu.compress(texto);
		char[] textoBase64 = Base64Coder.encode(textoComprimido);
		
		//Este sería el resultado enviado en el response...
		String aux = String.valueOf(textoBase64);

		textoComprimido = Base64Coder.decode(aux.toCharArray());

		assertEquals(texto, gsu.decompress(textoComprimido));
	}

}
