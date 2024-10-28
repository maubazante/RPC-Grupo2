package com.unla.stockearte.helpers;

public class ContenidoPDF {
	private String texto;
	private String urlImagen;

	public ContenidoPDF(String texto, String urlImagen) {
		this.texto = texto;
		this.urlImagen = urlImagen;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getUrlImagen() {
		return urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}
}
