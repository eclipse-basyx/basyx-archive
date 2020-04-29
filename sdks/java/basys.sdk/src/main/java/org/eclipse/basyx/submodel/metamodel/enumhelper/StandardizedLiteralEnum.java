package org.eclipse.basyx.submodel.metamodel.enumhelper;

/**
 * Enums with this interface hold a custom string literal that is used during e.g. XML serialization.
 * You may use the {@link StandardizedLiteralEnumHelper} to map a custom string literal to an enum. 
 * 
 * @author alexandergordt
 */
public interface StandardizedLiteralEnum {

	/**
	 * Custom string for use in case sensitive environments or during serialization.
	 * 
	 * @return Case sensitive string
	 */
	String getStandardizedLiteral();
}
