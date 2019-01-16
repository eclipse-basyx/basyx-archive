package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier;

import java.util.HashMap;

/**
 * Referable class
 * 
 * @author kuhn, schnicke
 *
 */
public class Referable extends HashMap<String, Object> {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public Referable() {
		// Identifies an element within its name space (String)
		put("idShort", "");
		// Coded value that gives further meta information w.r.t. to the type of the
		// element. It affects the
		// expected existence of attributes and the applicability of constraints.
		// (String)
		put("category", "");
		// Description or comments on the element (String)
		put("description", "");
		// Reference to the parent of this element (Referable)
		put("parent", null);
	}

	/**
	 * Constructor with idShort, category and description
	 * 
	 * @param idShort
	 *            will be matched case insensitive; may only feature letters,
	 *            digits, underscores and start with a letter
	 * @param category
	 * @param description
	 */
	public Referable(String idShort, String category, String description) {
		// Identifies an element within its name space (String)
		put("idShort", idShort);
		// Coded value that gives further meta information w.r.t. to the type of the
		// element. It affects the
		// expected existence of attributes and the applicability of constraints.
		// (String)
		put("category", category);
		// Description or comments on the element (String)
		put("description", description);
		// Reference to the parent of this element (Referable)
		put("parent", null);
	}
}
