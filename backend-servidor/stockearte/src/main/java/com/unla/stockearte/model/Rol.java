package com.unla.stockearte.model;

public enum Rol {

	ADMIN("ADMIN"), STOREMANAGER("STOREMANAGER");

	private final String value;

	private Rol(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public static Rol fromValue(String value) {
		for (Rol rol : Rol.values()) {
			if (rol.value.equalsIgnoreCase(value)) {
				return rol;
			}
		}
		return null;
	}
}
