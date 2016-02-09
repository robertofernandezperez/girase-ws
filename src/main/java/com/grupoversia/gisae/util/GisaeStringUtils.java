package com.grupoversia.gisae.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 
 * @author rfernandez
 *
 */
public class GisaeStringUtils {

	/**
	 * Metodo de utilidad para comprimir un texto usando GZIP
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public byte[] compress(String data) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length());
		GZIPOutputStream gzip = new GZIPOutputStream(bos);
		gzip.write(data.getBytes());
		gzip.close();
		byte[] compressed = bos.toByteArray();
		bos.close();
		
		return compressed;
	}

	/**
	 * Metodo de utilidad para descomprimir un texto usando GZIP
	 * 
	 * @param compressed
	 * @return
	 * @throws IOException
	 */
	public String decompress(byte[] compressed) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
		GZIPInputStream gis = new GZIPInputStream(bis);
		BufferedReader br = new BufferedReader(new InputStreamReader(gis)); //, "UTF-8"
		StringBuffer sb = new StringBuffer();
		String line;
		
		boolean firstLine = true;
		while ((line = br.readLine()) != null) {
			if(firstLine) {
				sb.append(line);
				firstLine = false;
			}else{
				sb.append("\n").append(line);
			}
		}
		br.close();
		gis.close();
		bis.close();
		return sb.toString();
	}

}
