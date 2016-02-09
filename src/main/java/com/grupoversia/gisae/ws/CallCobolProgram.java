package com.grupoversia.gisae.ws;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.grupoversia.gisae.model.CobolProgram;
import com.grupoversia.gisae.model.CobolProgramException;
import com.grupoversia.gisae.util.CobolUtils;

public class CallCobolProgram {
	
	private static Log log = LogFactory.getLog(CallCobolProgram.class);

	public List callCobolProgram(CobolProgram cobolProgram) throws CobolProgramException {

		CobolUtils utils = new CobolUtils();

		if (cobolProgram.getProgram() == null) {
			log.debug("===> Falta indicar el nombre del programa Cobol que se quiere ejecutar...");
			
			throw new CobolProgramException("Falta indicar el nombre del programa Cobol que se quiere ejecutar...", "");
		}
		
		log.info("===> Llamada a callCobolProgram...");
		
		// El fichero SH se encontrara en el PATH del servidor alojado en la ruta "/usr/local/bin".
		// Por lo tanto, solo sera necesario indicar al programa Cobol el nombre "wscobol.sh".
		// Al programa Cobol habrá que pasarle el path completo del fichero de intercambio por ejemplo:
		// /tmp/20151222125912-49
		return utils.callCobolProgram(cobolProgram, "wscobol.sh", "/tmp");
	}
}
