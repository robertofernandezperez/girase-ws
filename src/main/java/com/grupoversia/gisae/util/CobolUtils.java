package com.grupoversia.gisae.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.grupoversia.gisae.model.CobolProgram;
import com.grupoversia.gisae.model.CobolProgramException;

public class CobolUtils {

	private static Log log = LogFactory.getLog(CobolUtils.class);

	/**
	 * 
	 * @param cobolProgram
	 * @param scriptPath
	 * @param exchangePath
	 * @return
	 * @throws IOException
	 */
	public List callCobolProgram(CobolProgram cobolProgram, String scriptPath, String exchangePath)
			throws CobolProgramException {
		try {
			log.debug("===> INICIO Llamada al programa Linux...");

			GisaeFileUtils gfu = new GisaeFileUtils();
//			GisaeStringUtils gsu = new GisaeStringUtils();

			String exchangeFile = gfu.generateFileName();

			log.debug("===> 1. Generado el fichero de intercambio: '" + exchangeFile + "'");

			String exchangeFilePath = exchangePath + "/" + exchangeFile;

			gfu.createExchangeFile(cobolProgram, exchangeFilePath);

			log.debug("===> 2. Se va a lanzar: '" + scriptPath + " " + exchangeFilePath + "'");

			Process proc = Runtime.getRuntime().exec(scriptPath + " " + exchangeFilePath);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

			String shellOutput = null;

			log.debug("===> 3. Aqui esta la salida estandar del comando:");

			while ((shellOutput = stdInput.readLine()) != null) {
				log.debug(shellOutput);
			}

			log.debug("===> 4. Aqui esta la salida de error del comando (si la hubiera):");

			StringBuffer sb = new StringBuffer();

			while ((shellOutput = stdError.readLine()) != null) {
				log.debug(shellOutput);
				sb.append(shellOutput);
			}

			if (sb.length() > 0) {
				throw new CobolProgramException("Salida del comando con errores: " + sb.toString(), "");
			}

			proc.destroy();

			// Cambios solicitados por Javier Martinez:
			// Ya no se comprimira la respuesta ni se codificará en base64.
			// Se va a devolver un array de String con todas las filas devueltas por parte del programa Cobol
			
			List resultado = gfu.getResponseFromFileInArray(exchangeFilePath);
			
			log.debug("===> 5. Resultado del fichero '" + resultado.size() + "' lineas");
			
			return resultado;
			
			
			
			
//			String fileResponse = gfu.getResponseFromFile(exchangeFilePath);
//
//			log.debug("===> 5. Resultado del fichero '" + fileResponse + "'");
//
//			// Borramos el fichero de intercambio...
//			gfu.deleteFile(exchangeFilePath);
//
//			log.debug("===> 6. Borrado el fichero de intercambio...");
			
//			byte[] fileResponseCompressed;
//
//			// Comprimir la respuesta...
//			if (fileResponse != null)
//				fileResponseCompressed = gsu.compress(fileResponse);
//			else
//				throw new CobolProgramException("No se ha generado una respuesta correcta en el fichero", "");
//
//			log.debug("===> 7. Resultado del fichero comprimido en GZIP '" + fileResponseCompressed + "'");
//
//			char[] fileResponseBase64;
//
//			// Codificar la respuesta en base64...
//			if (fileResponseCompressed != null)
//				fileResponseBase64 = Base64Coder.encode(fileResponseCompressed);
//			else
//				throw new CobolProgramException("No se ha podido comprimir la respuesta del fichero", "");
//
//			log.debug("===> 8. Resultado del fichero en base 64 '" + String.valueOf(fileResponseBase64) + "'");
//			log.debug("===> FIN Llamada al programa Linux...");
//
//			return String.valueOf(fileResponseBase64);

		} catch (IOException e) {
			throw new CobolProgramException(e.getMessage(), "");
		}

	}
}
