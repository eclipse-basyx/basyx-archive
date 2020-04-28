package org.eclipse.basyx.enumhelper;

import com.google.common.base.Strings;

/**
 * Helper class to map custom string literals to StandardizedLiteralEnums.
 *  
 * @author alexandergordt
 */
public class StandardizedLiteralEnumHelper {

	/**
	 * Maps string literals of {@link StandardizedLiteralEnum}s to enum constants.
	 * The string literals read via getStandardizedLiteral() from the enum constants.
	 * 
	 * @param <T> Enum class implementing StandardizedLiteralEnum
	 * @param clazz Target enum with matching custom string literal
	 * @param literal The literal as contained in e.g. XML schema
	 * @return Enum constant
	 * @throws IllegalArgumentException when string literal is not found in enum.
	 */
	public static <T extends StandardizedLiteralEnum> T fromLiteral(Class<T> clazz, String literal) {
		if (Strings.isNullOrEmpty(literal)) {
			return null;
		}
		
		T[] enumConstants = clazz.getEnumConstants();
		for(T constant : enumConstants) {
			if(constant.getStandardizedLiteral().equals(literal)) {
				return constant;
			}
		}
		throw new IllegalArgumentException("The literal '" + literal + "' is not contained in enum " + clazz.getName());
	}
}
