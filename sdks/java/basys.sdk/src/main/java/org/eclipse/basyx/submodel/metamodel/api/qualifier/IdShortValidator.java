package org.eclipse.basyx.submodel.metamodel.api.qualifier;

/**
 * Tests the validity of IdShorts according to DotAAS
 * 
 * @author schnicke
 *
 */
public class IdShortValidator {
	/*
	 * Taken from DotAAS p. 50: "Constraint AASd-002: idShort shall only feature
	 * letters, digits, underscore ("_"); starting mandatory with a letter."
	 */
	public static final String IDSHORT_REGEX = "[a-zA-Z][a-zA-Z0-9_]+";

	/**
	 * Returns true iff the idShort is valid according to DotAAS
	 * 
	 * @param idShort
	 *            to test
	 * @return test result
	 */
	public static boolean isValid(String idShort) {
		if (idShort == null) {
			return false;
		}

		return idShort.matches(IDSHORT_REGEX);
	}
}
