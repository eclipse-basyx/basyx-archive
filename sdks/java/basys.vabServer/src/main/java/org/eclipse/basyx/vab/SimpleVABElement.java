package org.eclipse.basyx.vab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import org.eclipse.basyx.vab.exception.provider.ProviderException;

/**
 * A simple VAB model that explains the use of the VAB primitives.
 * Removes local Java-specific properties (e.g. function serialization)
 * 
 * @author espen
 *
 */
public class SimpleVABElement extends HashMap<String, Object> {
	private static final long serialVersionUID = 3942399852711325850L;

	/**
	 * Constructor for a simple VAB element that contains all data types
	 */
	public SimpleVABElement() {
		// Add primitive types
		HashMap<String, Object> primitives = new HashMap<>();
		primitives.put("integer", 123);
		primitives.put("double", 3.14d);
		primitives.put("string", "TestValue");
		put("primitives", primitives);

		// Add function types
		HashMap<String, Object> functions = new HashMap<>();
		functions.put("providerException", (Function<Object[], Object>) (param) -> {
			throw new ProviderException("Exception description");
		});
		functions.put("nullException", (Function<Object[], Object>) (param) -> {
			throw new NullPointerException();
		});
		functions.put("complex", (Function<Object[], Object>) (param) -> {
			return (int) param[0] + (int) param[1];
		});
		functions.put("invalid", true);
		functions.put("invokable", (Function<Object[], Object>) (param) -> {
			return true;
		});
		put("operations", functions);

		// Add structure types
		HashMap<String, Object> structure = new HashMap<>();
		structure.put("map", new HashMap<String, Object>());
		structure.put("list", new ArrayList<Object>());
		put("structure", structure);

		// Add corner cases
		HashMap<String, Object> special = new HashMap<>();
		special.put("casesensitivity", true);
		special.put("caseSensitivity", false);
		HashMap<String, Object> nestedA = new HashMap<>();
		HashMap<String, Object> nestedB = new HashMap<>();
		nestedA.put("nested", nestedB);
		nestedB.put("value", 100);
		special.put("nested", nestedA);
		special.put("null", null);
		put("special", special);
	}
}
