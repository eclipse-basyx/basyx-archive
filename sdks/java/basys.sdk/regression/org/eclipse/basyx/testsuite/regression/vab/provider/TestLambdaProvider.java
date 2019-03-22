package org.eclipse.basyx.testsuite.regression.vab.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.backend.connector.ConnectorProvider;
import org.eclipse.basyx.testsuite.regression.vab.snippet.CreateDelete;
import org.eclipse.basyx.testsuite.regression.vab.snippet.GetPropertyValue;
import org.eclipse.basyx.testsuite.regression.vab.snippet.Invoke;
import org.eclipse.basyx.testsuite.regression.vab.snippet.SetPropertyValue;
import org.eclipse.basyx.testsuite.regression.vab.snippet.TestCollectionProperty;
import org.eclipse.basyx.testsuite.regression.vab.snippet.TestMapProperty;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.provider.lambda.VABLambdaProvider;
import org.eclipse.basyx.vab.provider.lambda.VABLambdaProviderHelper;
import org.junit.Test;

/**
 * Tests the functionality of the VABLambdaProvider according to the test cases
 * in the snippet package
 * 
 * @author schnicke
 *
 */
public class TestLambdaProvider {

	// Representative of the value of property1.1
	static int property11_val;

	// Representative of the value of property1.2
	static Map<String, Object> propertyMap_val;

	// Representative of the value of propertyMap
	static Collection<Object> propertyCollection_val;

	protected VABConnectionManager connManager = new VABConnectionManager(new TestsuiteDirectory(), new ConnectorProvider() {

		@Override
		protected IModelProvider createProvider(String addr) {
			return buildProvider();
		}
	});

	private static IModelProvider buildProvider() {
		// Set initial values for property representatives
		property11_val = 7;

		propertyMap_val = new HashMap<>();
		propertyMap_val.put("test", 123);

		propertyCollection_val = new ArrayList<>();
		propertyCollection_val.add(1);
		propertyCollection_val.add(2);

		// Create accessors for simple value property property1.1
		Map<String, Object> property11 = VABLambdaProviderHelper.createSimple((Supplier<Object>) () -> {
			return property11_val;
		}, null);

		// Create accessors for collection property property1.2
		Map<String, Object> collectionAccessors = VABLambdaProviderHelper.createCollection((Supplier<Object>) () -> {
			return propertyCollection_val;
		}, (Consumer<Collection<Object>>) (collection) -> {
			propertyCollection_val = collection;
		}, (Consumer<Object>) (value) -> {
			propertyCollection_val.add(value);
		}, (Consumer<Object>) (o) -> {
			propertyCollection_val.remove(o);
		});

		// Create accessors for map property propertyMap
		Map<String, Object> mapAccessors = VABLambdaProviderHelper.createMap((Supplier<?>) () -> {
			return propertyMap_val;
		}, (Consumer<Map<String, Object>>) (map) -> {
			propertyMap_val = map;
		}, (BiConsumer<String, Object>) (key, value) -> {
			propertyMap_val.put(key, value);
		}, (Consumer<Object>) (o) -> {
			propertyMap_val.remove(o);
		});

		// Build contained operations
		Map<String, Object> containedOperations = new HashMap<>();

		// Function returning constant
		HashMap<String, Object> operation11 = new HashMap<>();
		operation11.put("endpoint", (Function<Object[], Object>) (v) -> {
			return 10;
		});
		containedOperations.put("operation1.1", operation11);

		// Build root operations
		Map<String, Function<?, ?>> rootOperations = new HashMap<>();
		// Adding function
		rootOperations.put("operation1", (Function<Object[], Object>) (param) -> {
			return (int) param[0] + (int) param[1];
		});

		rootOperations.put("operation2", (Function<Object[], Object>) (param) -> {
			return null;
		});

		// Function that throws native JAVA exception
		rootOperations.put("operationEx1", (Function<Object[], Object>) (elId) -> {
			throw new NullPointerException();
		});
		// Function that throws VAB exception
		rootOperations.put("operationEx2", (Function<Object[], Object>) (elId) -> {
			throw new ServerException("ExType", "Exception description");
		});

		// Create property1 map
		Map<String, Object> property1 = new HashMap<>();
		property1.put("property1.1", property11);
		property1.put("property1.2", collectionAccessors);
		property1.put("propertyMap", mapAccessors);
		property1.put("operations", containedOperations);

		// Create root map
		Map<String, Object> obj = new HashMap<>();
		obj.put("property1", property1);
		obj.put("operations", rootOperations);

		// Build provider
		VABLambdaProvider provider = new VABLambdaProvider(obj);

		return provider;
	}

	@Test
	public void testCreateDelete() {
		CreateDelete.test(connManager);
	}

	@Test
	public void testGet() {
		GetPropertyValue.test(connManager);
	}

	@Test
	public void testInvoke() {
		Invoke.test(connManager);
	}

	@Test
	public void testSet() {
		SetPropertyValue.test(connManager);
	}

	@Test
	public void testMapGet() {
		TestMapProperty.testGet(connManager);
	}

	@Test
	public void testMapUpdateComplete() {
		TestMapProperty.testUpdateComplete(connManager);
	}

	@Test
	public void testMapUpdateElement() {
		TestMapProperty.testUpdateElement(connManager);
	}

	@Test
	public void testMapRemoveElement() {
		TestMapProperty.testRemoveElement(connManager);
	}

	@Test
	public void testCollectionGet() {
		TestCollectionProperty.testGet(connManager);
	}

	@Test
	public void testCollectionUpdateComplete() {
		TestCollectionProperty.testUpdateComplete(connManager);
	}

	@Test
	public void testCollectionUpdateElement() {
		TestCollectionProperty.testUpdateElement(connManager);
	}

	@Test
	public void testCollectionRemoveElement() {
		TestCollectionProperty.testRemoveElement(connManager);
	}
}
