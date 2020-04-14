package org.eclipse.basyx.submodel.metamodel.api.dataspecification.enums;

/**
 * Possible level types as defined in DAAS for IEC61360 data specification templates
 * 
 * @author espen
 *
 */
public enum LevelType {
	MIN("Min"), MAX("Max"), NOM("Nom"), TYP("Typ");
	
	private String type;

	private LevelType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}

	public static LevelType fromString(String str) {
		if (str == null || str.isEmpty()) {
			return null;
		}

		for (LevelType elem : LevelType.values()) {
			if (elem.toString().equals(str)) {
				return elem;
			}
		}

		throw new RuntimeException("Unknown LevelType: " + str);
	}
}
