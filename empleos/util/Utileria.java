package com.portalempleos.empleos.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class Utileria {

	public static String guardarArchivo(MultipartFile multiPart, String ruta) {
		//Obtengo el nombe original del archivo.
		String nombreOriginal = multiPart.getOriginalFilename();
		//Por cada espacio que haya, voy a reemplazarlo por un guión
		nombreOriginal = nombreOriginal.replace(" ", "-");
		String nombreFinal = randomAlphaNumeric(8) + nombreOriginal;
		try {
			//Creo el nombre del archivo para guardarlo en el disco duro
			File imageFile = new File(ruta + nombreFinal);
			System.out.println("Archivo: " + imageFile.getAbsolutePath());
			//Guardo fisicamente el archivo en HD
			multiPart.transferTo(imageFile);
			return nombreFinal;
			
		} catch(IOException e) {
			System.out.println("Error en utileria: " + e.getMessage());
			return null;
		}
	}
	
	
	/**
	 * Metodo para generar una cadena aleatoria de longitud N
	 * @param count (Determina la cantidad de caracteres que se van a generar)
	 * @return
	 */
	public static String randomAlphaNumeric(int count) {
		String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVXYZ0123456789";
		/**
		 * <StringBuilder> y <StringBuffer> son clases que permiten crear objetos que
		 *  almacenan cadenas de caracteres que pueden ser modificadas sin necesidad de
		 *  crear nuevos objetos. Los métodos append, replace e insert que poseen StringBuilder
		 *  y StringBuffer, permiten manipular las cadenas de caracteres. 
         */
		StringBuilder builder = new StringBuilder();
		while(count-- != 0) {
			int character = (int) (Math.random() * CARACTERES.length());
			builder.append(CARACTERES.charAt(character));
		}
		return builder.toString();
	}
}
