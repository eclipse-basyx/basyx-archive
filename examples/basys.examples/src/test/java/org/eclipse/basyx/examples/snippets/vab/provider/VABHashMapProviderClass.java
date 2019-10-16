package org.eclipse.basyx.examples.snippets.vab.provider;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.function.Function;

import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.modelprovider.map.VABHashmapProvider;
import org.junit.Test;

/**
 * Code snippet showing the use of the VABHashMapProvider. It can be used as a basic low level model provider for
 * arbitrary models based on HashMaps. The provider encapsulates the actual data and implements the abstract interface
 * IModelProvider so that the contained model can be accessed via the five VAB primitives create, retrieve, update,
 * delete and invoke.
 * 
 * @author espen
 *
 */
public class VABHashMapProviderClass {

	/**
	 * Snippet showing the programmatic approach for creating arbitrary local, static models using HashMaps
	 */
	@Test
	public void snippet() throws Exception {
		// Create a HashMap as the root for the data structure
		HashMap<String, Object> rootElement = new HashMap<>();

		// Add a simple string element
		rootElement.put("name", "myElement");

		// Create a function and then add it as another element to the root
		Function<Object[], Object> myFunction = (param) -> {
			return (int) param[0] + (int) param[1];
		};
		rootElement.put("operation", myFunction);

		// Hierarchical structures are possible by nesting multiple HashMaps
		// => Create a new HashMap with two elements and add it as a child to the root Map
		HashMap<String, Object> childMap = new HashMap<>();
		childMap.put("type", "boolean");
		childMap.put("value", true);
		rootElement.put("data", childMap);

		// The provider then encapsulates the data and realizes the abstract IModelProvider interface
		IModelProvider provider = new VABHashmapProvider(rootElement);

		// Child elements can now be accessed with a path that is mapped to actual data structure
		assertEquals("myElement", provider.getModelPropertyValue("/name"));
		assertEquals("boolean", provider.getModelPropertyValue("/data/type"));
		assertEquals(true, provider.getModelPropertyValue("/data/value"));

		// Future modifications are now applied using the IModelProvider interface.
		provider.setModelPropertyValue("/name", "yourElement");
		assertEquals("yourElement", provider.getModelPropertyValue("/name"));

		// The creation of nested elements within an existing provider is also possible
		// HashMaps and Collections are supported for this purpose
		HashMap<String, Object> description = new HashMap<>();
		description.put("DE", "Dies ist ein generisches Element für den VAB");
		description.put("EN", "This is a generic VAB element");
		provider.createValue("/description", description);

		// The path is again mapped to the HashMap structure
		assertEquals("This is a generic VAB element", provider.getModelPropertyValue("/description/EN"));
	}
}
