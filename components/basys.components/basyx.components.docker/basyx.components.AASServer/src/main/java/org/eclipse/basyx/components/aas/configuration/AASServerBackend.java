package org.eclipse.basyx.components.aas.configuration;

import org.eclipse.basyx.submodel.metamodel.enumhelper.StandardizedLiteralEnumHelper;

import com.google.common.base.Strings;

/**
 * Possible types for AAS backends.
 * 
 * @author espen
 *
 */
public enum AASServerBackend {
	/**
	 * Enum values of KeyElements
	 */
	INMEMORY("InMemory"),
	MONGODB("MongoDB");
	
	private String literal;

	private AASServerBackend(String literal) {
		this.literal = literal;
	}

	@Override
	public String toString() {
		return literal;
	}

	/**
	 * Method to transform string literal to AASBackend enum.
	 * 
	 * @see StandardizedLiteralEnumHelper StandardizedLiteralEnumHelper
	 * 
	 * @param literal
	 * @return
	 */
	public static AASServerBackend fromString(String literal) {
		if (Strings.isNullOrEmpty(literal)) {
			return null;
		}

		AASServerBackend[] enumConstants = AASServerBackend.class.getEnumConstants();
		for (AASServerBackend constant : enumConstants) {
			if (constant.toString().equals(literal)) {
				return constant;
			}
		}
		throw new IllegalArgumentException("The literal '" + literal + "' is not a valid RegistryBackend");
	}
}
