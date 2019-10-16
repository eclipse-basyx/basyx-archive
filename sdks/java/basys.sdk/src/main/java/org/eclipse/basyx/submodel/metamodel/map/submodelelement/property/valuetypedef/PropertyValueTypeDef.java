package org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.valuetypedef;

/**
 * Helper enum to handle anySimpleTypeDef as defined in DAAS document <br />
 * Represents the type of a data entry <br />
 * TODO: Extend this to support rest of types (cf. p. 58)
 * 
 * @author schnicke
 *
 */
public enum PropertyValueTypeDef {
	Double("double"), Float("float"), Integer("int"), String("string"), Boolean("boolean"), Map("map"), Collection("collection"), Void("void"), Null("null"), Container("container");

	private String name;

	private PropertyValueTypeDef(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
