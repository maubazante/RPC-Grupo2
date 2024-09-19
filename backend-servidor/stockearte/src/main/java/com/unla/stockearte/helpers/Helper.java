package com.unla.stockearte.helpers;

import java.security.SecureRandom;

public class Helper {

	private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LONGITUD_CADENA = 10;
    private static final SecureRandom random = new SecureRandom();

    public static String generarCadenaAleatoria() {
        StringBuilder sb = new StringBuilder(LONGITUD_CADENA);
        for (int i = 0; i < LONGITUD_CADENA; i++) {
            int indiceAleatorio = random.nextInt(CARACTERES.length());
            char caracterAleatorio = CARACTERES.charAt(indiceAleatorio);
            sb.append(caracterAleatorio);
        }
        return sb.toString();
    }
}
