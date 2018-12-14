package org.eclipse.basyx.aas.metamodel.hashmap.aas.parts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Asset Administration Shell body class
 * 
 * @author kuhn
 *
 */
public class Body extends HashMap<String, Object> {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public Body() {
		// Default values
		put("views", new HashSet<View>());
		put("submodels", new ArrayList<String>());

	}
}
