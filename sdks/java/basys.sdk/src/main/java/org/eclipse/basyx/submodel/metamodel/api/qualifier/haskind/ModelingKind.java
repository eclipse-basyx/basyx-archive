package org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind;

/**
 * ModelingKind enum as defined by DAAS document<br />
 * Enumeration for denoting whether an element is a template or an instance.
 * 
 * @author schnicke
 *
 */
public enum ModelingKind {

	/**
	 * Software element which specifies the common attributes shared by all
	 * instances of the template.
	 */
	INSTANCE("Instance"),
	/**
	 * Concrete, clearly identifiable component of a certain template.
	 */
	TEMPLATE("Template");

	private String name;

	private ModelingKind(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public static ModelingKind fromString(String str) {
		if (str == null || str.isEmpty()) {
			return null;
		}

		if(str.equals(ModelingKind.INSTANCE.toString())) {
			return INSTANCE;
		} else if (str.equals(ModelingKind.TEMPLATE.toString())) {
			return TEMPLATE;
		}
		throw new RuntimeException("Unknown ModelingKind: " + str);
	}
}
