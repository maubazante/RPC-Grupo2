package com.unla.stockearte.helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

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

	@ResponseStatus(HttpStatus.FORBIDDEN)
	public static class UnauthorizedException extends RuntimeException {
		public UnauthorizedException(String message) {
			super(message);
		}
	}

	public static byte[] generarPDF(String contenido) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PDDocument document = new PDDocument();
		PDPage page = new PDPage(PDRectangle.A4);
		document.addPage(page);

		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		contentStream.beginText();
		contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);
		contentStream.newLineAtOffset(25, 700);
		contentStream.showText(contenido);
		contentStream.endText();
		contentStream.close();

		document.save(outputStream);
		document.close();
		return outputStream.toByteArray();
	}

}
