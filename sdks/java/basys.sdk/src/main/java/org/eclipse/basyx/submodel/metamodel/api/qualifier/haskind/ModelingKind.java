package org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind;

import org.eclipse.basyx.enumhelper.StandardizedLiteralEnum;
import org.eclipse.basyx.enumhelper.StandardizedLiteralEnumHelper;

/**
 * ModelingKind enum as defined by DAAS document<br />
 * Enumeration for denoting whether an element is a template or an instance.
 * 
 * @author schnicke
 *
 */
public enum ModelingKind implements StandardizedLiteralEnum {

	/**
	 * Software element which specifies the common attributes shared by all
	 * instances of the template.
	 */
	INSTANCE("Instance"),
	/**
	 * Concrete, clearly identifiable component of a certain template.
	 */
	TEMPLATE("Template");

	private String standardizedLiteral;

	private ModelingKind(String standardizedLiteral) {
		this.standardizedLiteral = standardizedLiteral;
	}

	@Override
	public String getStandardizedLiteral() {
		return standardizedLiteral;
	}
	
	@Override
	public String toString() {
		return standardizedLiteral;
	}

	public static ModelingKind fromString(String str) {
		return StandardizedLiteralEnumHelper.fromLiteral(ModelingKind.class, str);
	}
}
