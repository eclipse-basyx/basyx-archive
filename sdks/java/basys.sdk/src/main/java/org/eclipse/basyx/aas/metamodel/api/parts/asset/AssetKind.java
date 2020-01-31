package org.eclipse.basyx.aas.metamodel.api.parts.asset;

/**
 * AssetKind enum as defined by DAAS document<br />
 * Enumeration for denoting whether an element is a type or an instance.
 * 
 * @author schnicke
 *
 */
public enum AssetKind {
	/**
	 * Hardware or software element which specifies the common attributes shared by
	 * all instances of the type
	 */
	TYPE("Type"),
	/**
	 * Concrete, clearly identifiable component of a certain type
	 */
	INSTANCE("Instance");

	private String name;

	private AssetKind(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public static AssetKind fromString(String str) {
		if (str == null || str.isEmpty()) {
			return null;
		}

		if (str.equals(AssetKind.INSTANCE.toString())) {
			return INSTANCE;
		} else if (str.equals(AssetKind.TYPE.toString())) {
			return TYPE;
		}
		throw new RuntimeException("Unknown AssetKind: " + str);
	}
}
