package org.eclipse.basyx.testsuite.support.vab.stub.elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.aas.api.exception.ServerException;

/**
 * A simple VAB model that explains the use of the VAB hashmap provider
 * 
 * @author kuhn
 *
 */
public class SimpleVABElement extends HashMap<String, Object> {
	private static final long serialVersionUID = -1339664424451243526L;

	/**
	 * Constructor
	 */
	public SimpleVABElement() {
		// Create contained map
		Map<String, Object> containedElement = new HashMap<>();
		// - Initialize contained map
		containedElement.put("property1.1", new Integer(7));
		// - Create contained collection

		Collection<Integer> collection = new ArrayList<>();
		collection.add(1);
		collection.add(2);
		containedElement.put("property1.2", collection);

		Map<String, Object> prop = new HashMap<>();
		prop.put("test", 123);
		containedElement.put("propertyMap", prop);

		// Create operation maps
		Map<String, Object> containedOperations = new HashMap<>();
		Map<String, Function<?, ?>> rootOperations = new HashMap<>();
		// - Add operation maps
		containedElement.put("operations", containedOperations);
		put("operations", rootOperations);
		// - Add operations
		// - Create contained operation without parameter
		HashMap<String, Object> operation11 = new HashMap<>();
		operation11.put("endpoint", (Function<Object[], Object>) (v) -> {
			return 10;
		});

		operation11.put("output", "Integer");

		containedOperations.put("operation1.1", operation11);

		// - Contained function
		rootOperations.put("operation1", (Function<Object[], Object>) (param) -> {
			return (int) param[0] + (int) param[1];
		});
		// - Contained function that throws native JAVA exception
		rootOperations.put("operationEx1", (Function<Object[], Object>) (elId) -> {
			throw new NullPointerException();
		});
		// - Contained function that throws VAB exception
		rootOperations.put("operationEx2", (Function<Object[], Object>) (elId) -> {
			throw new ServerException("ExType", "Exception description");
		});

		// Put elements into 'elements' map of this provider
		// - Contained HashMap for 'property1'
		put("property1", containedElement);
	}
}
