package com.unla.stockearte.helpers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class HelperPDF {

	public static byte[] generarPDF(List<ContenidoPDF> contenidos) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PDDocument document = new PDDocument();
		PDPage page = new PDPage(PDRectangle.A4);
		document.addPage(page);

		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		float yPosition = 750;
		float lineHeight = 15;

		for (ContenidoPDF contenido : contenidos) {
			if (contenido.getTexto() != null) {
				// Cambiar el tamaño de fuente para el nombre del catálogo
				if (contenido.getTexto().startsWith("Catálogo:")) {
					contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
				} else {
					contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
				}

				// Agrega un separador despues de la direccion
				if (contenido.getTexto().startsWith("Tienda:")) {
					yPosition = agregarTexto(contentStream, contenido.getTexto(), yPosition, lineHeight);
					yPosition = agregarSeparador(contentStream, yPosition);
				} else {
					yPosition = agregarTexto(contentStream, contenido.getTexto(), yPosition, lineHeight);
				}

			}

			if (contenido.getUrlImagen() != null) {
				yPosition = agregarImagen(contentStream, contenido.getUrlImagen(), document, yPosition);
				yPosition = agregarSeparador(contentStream, yPosition);
			}

			yPosition -= 20; // Espacio entre contenidos

			// Agregar una nueva página si el espacio es insuficiente
			if (yPosition <= 50) {
				contentStream.close();
				page = new PDPage(PDRectangle.A4);
				document.addPage(page);
				contentStream = new PDPageContentStream(document, page);
				yPosition = 750;
			}
		}

		contentStream.close();
		document.save(outputStream);
		document.close();
		return outputStream.toByteArray();
	}

	private static float agregarTexto(PDPageContentStream contentStream, String texto, float yPosition,
			float lineHeight) throws IOException {
		// Reemplazar saltos de línea por un espacio
		String[] lineas = texto.split("\\n");

		contentStream.beginText();
		contentStream.newLineAtOffset(50, yPosition);

		for (String linea : lineas) {
			contentStream.showText(linea);
			contentStream.newLineAtOffset(0, -lineHeight); // Mover la posición hacia abajo
		}

		contentStream.endText();
		return yPosition - (lineas.length * lineHeight);
	}

	private static float agregarImagen(PDPageContentStream contentStream, String urlImagen, PDDocument document,
			float yPosition) {
		try {
			BufferedImage image = ImageIO.read(new URL(urlImagen));
			PDImageXObject pdImage = LosslessFactory.createFromImage(document, image);
			contentStream.drawImage(pdImage, 50, yPosition - 100, 100, 100);
			yPosition -= 120; // Espacio para la imagen
		} catch (IOException e) {
			System.out.println("Error al cargar la imagen de URL: " + urlImagen);
		}
		return yPosition;
	}

	private static float agregarSeparador(PDPageContentStream contentStream, float yPosition) throws IOException {
		contentStream.setLineWidth(1);
		contentStream.moveTo(50, yPosition);
		contentStream.lineTo(550, yPosition);
		contentStream.stroke();
		return yPosition - 10;
	}
}
