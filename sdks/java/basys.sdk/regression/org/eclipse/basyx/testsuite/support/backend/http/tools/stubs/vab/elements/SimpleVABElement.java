package org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.vab.elements;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.sdk.provider.hashmap.VABHashmapProvider;





/**
 * A simple VAB model that explains the use of the VAB hashmap provider
 * 
 * @author kuhn
 *
 */
public class SimpleVABElement extends VABHashmapProvider {

	
	/**
	 * Constructor
	 */
	public SimpleVABElement() {
		// This is a sub model

		// Create contained map
		Map<String, Object> containedElement = new HashMap<>();
		// - Initialize contained map
		containedElement.put("property1.1", new Integer(7));
		// - Create contained collection
		containedElement.put("property1.2", new HashSet<>());
		
		
		// Create operation maps
		Map<String, Function<?, ?>> containedOperations = new HashMap<>();
		Map<String, Function<?, ?>> rootOperations = new HashMap<>();
		// - Add operation maps
		containedElement.put("operations", containedOperations);
		elements.put("operations", rootOperations);
		// - Add operations
		//   - Create contained operation without parameter
		containedOperations.put("operation1.1", (Function<Object[], Object>) (v) -> {return elements.get("property1");});
		//   - Contained access function
		rootOperations.put("operation1", (Function<Object[], Object>) (elId) -> {return elements.get((String) elId[0]);});
		//   - Contained function that throws native JAVA exception
		rootOperations.put("operationEx1", (Function<Object[], Object>) (elId) -> {throw new NullPointerException();});
		//   - Contained function that throws VAB exception
		rootOperations.put("operationEx2", (Function<Object[], Object>) (elId) -> {throw new ServerException("ExType", "Exception description");});
		
		
		// Put elements into 'elements' map of this provider
		// - Contained HashMap for 'property1'
		elements.put("property1", containedElement);
	}
}
