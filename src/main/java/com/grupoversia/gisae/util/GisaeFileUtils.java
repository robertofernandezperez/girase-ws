package com.grupoversia.gisae.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.grupoversia.gisae.model.CobolProgram;
import com.grupoversia.gisae.model.CobolProgramException;

/**
 * 
 * @author rfernandez
 *
 */
public class GisaeFileUtils {

	private String FICHERO_OK = "OK";
	private String FICHERO_ERROR = "ERROR";
	private String RESULTADO_INICIO = "RESULTADO-INICIO";
	private String RESULTADO_FIN = "RESULTADO-FIN";
	private String ERROR_INICIO = "MENSAJE-ERROR-INICIO";
	private String ERROR_FIN = "MENSAJE-ERROR-FIN";

	/**
	 * Metodo de utilidad para leer la respuesta de un programa Cobol
	 * 
	 * @param pathFie
	 * @return
	 * @throws IOException
	 */
	public String getResponseFromFile(String pathFile) throws IOException, CobolProgramException {
		
		StringBuffer sb = new StringBuffer();
		InputStream fis = new FileInputStream(pathFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		int fileLineCount = 0;
		
		boolean ficheroErroneo = false;

		for (String line = br.readLine(); line != null; line = br.readLine()) {

			if (fileLineCount == 0 && !line.equals(FICHERO_OK) && !line.equals(FICHERO_ERROR)) {
				sb = null;
				ficheroErroneo = true;
				break;
			} else if (fileLineCount == 1 && !line.equals(RESULTADO_INICIO) && !line.equals(ERROR_INICIO)) {
				sb = null;
				ficheroErroneo = true;
				break;
			} else if (fileLineCount > 1 && !line.equals(RESULTADO_FIN) && !line.equals(ERROR_FIN)) {
				if (sb.length() > 0) {
					sb.append("\n").append(line);
				} else {
					sb.append(line);
				}
			}

			fileLineCount++;
		}
		
		br.close();

		// Casuística de fichero erróneo
		if(ficheroErroneo)
			throw new CobolProgramException("El formato del fichero de salida Cobol es erróneo...", "");
		
		// Casuistica de fichero vacio...
		if (fileLineCount == 0)
			throw new CobolProgramException("El fichero de salida Cobol está vacio...", "");

		return sb.toString();
	}
	
	/**
	 * Metodo de utilidad para leer la respuesta de un programa Cobol
	 * 
	 * @param pathFie
	 * @return
	 * @throws IOException
	 */
	public List getResponseFromFileInArray(String pathFile) throws IOException, CobolProgramException {
		
//		StringBuffer sb = new StringBuffer();
		InputStream fis = new FileInputStream(pathFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		int fileLineCount = 0;
		List lineas = new ArrayList();
		
		boolean ficheroErroneo = false;

		for (String line = br.readLine(); line != null; line = br.readLine()) {

			if (fileLineCount == 0 && !line.equals(FICHERO_OK) && !line.equals(FICHERO_ERROR)) {
				lineas = null;
				ficheroErroneo = true;
				break;
			} else if (fileLineCount == 1 && !line.equals(RESULTADO_INICIO) && !line.equals(ERROR_INICIO)) {
				lineas = null;
				ficheroErroneo = true;
				break;
			} else if (fileLineCount > 1 && !line.equals(RESULTADO_FIN) && !line.equals(ERROR_FIN)) {

				lineas.add(line);
				
//				if (sb.length() > 0) {
//					sb.append("\n").append(line);
//				} else {
//					sb.append(line);
//				}
			}

			fileLineCount++;
		}
		
		br.close();

		// Casuística de fichero erróneo
		if(ficheroErroneo)
			throw new CobolProgramException("El formato del fichero de salida Cobol es erróneo...", "");
		
		// Casuistica de fichero vacio...
		if (fileLineCount == 0)
			throw new CobolProgramException("El fichero de salida Cobol está vacio...", "");

		return lineas;
	}

	/**
	 * 
	 * Metodo de utilidad que genera un fichero con los parametros de entrada
	 * separados por salto de linea
	 * 
	 * @param params
	 * @param pathFile
	 * @return
	 * @throws IOException
	 */
	public void createExchangeFile(CobolProgram cobolProgram, String pathFile) throws IOException {
		OutputStream fos = new FileOutputStream(new File(pathFile));

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

		// Añadimos en la primera fila el nombre del programa
		if (cobolProgram.getProgram() != null) {
			bw.write(cobolProgram.getProgram() + "\n");
		}

		// A partir de la segunda linea añadimos los argumentos de entrada
		// al programa
		if (cobolProgram.getParams() != null) {
			for (int i = 1; i <= cobolProgram.getParams().length; i++) {
				if (i < cobolProgram.getParams().length) {
					bw.write(cobolProgram.getParams()[i - 1] + "\n");
				} else {
					bw.write(cobolProgram.getParams()[i - 1]);
				}
			}
		}

		bw.close();
		fos.flush();
		fos.close();
	}

	/**
	 * Método de utilidad que genera un identificador único para el fichero de
	 * parámetros que se va a pasar al programa Cobol
	 * 
	 * @return
	 */
	public String generateFileName() {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(100);

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

		return format.format(new Date()) + "-" + randomInt;
	}

	/**
	 * Método de utilidad que elimina el fichero de intercambio con Cobol
	 * 
	 * @param ficheroTmp
	 * @throws IOException
	 */
	public void deleteFile(String ficheroTmp) throws IOException {
		File file = new File(ficheroTmp);

		file.delete();
	}

}
