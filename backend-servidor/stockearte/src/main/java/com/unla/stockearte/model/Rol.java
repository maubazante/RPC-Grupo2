package com.unla.stockearte.model;

public enum Rol {
	
	ADMIN("ADMIND"),
	STOREMANAGER("STOREMANAGER");
	
	private final String value;
	
	private Rol(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
