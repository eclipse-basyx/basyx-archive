package org.eclipse.basyx.testsuite.regression.vab.provider;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.basyx.aas.backend.connector.ConnectorProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.eclipse.basyx.testsuite.support.vab.stub.elements.SimpleVABElement;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.provider.lambda.VABLambdaProvider;
import org.eclipse.basyx.vab.provider.lambda.VABLambdaProviderHelper;
import org.eclipse.basyx.vab.provider.list.ReferencedArrayList;

/**
 * Tests the functionality of the VABLambdaProvider according to the test cases
 * in the snippet package
 * 
 * @author schnicke, espen
 *
 */
public class TestLambdaProvider extends TestProvider {

	private static double doubleElement = 3.14d;
	private static ReferencedArrayList<Object> collectionElement = new ReferencedArrayList<>();
	private static Map<String, Object> rootAccessor;
	private static HashMap<String, Object> rootElement = new SimpleVABElement();
	@SuppressWarnings("unchecked")
	private static HashMap<String, Object> structureElement = (HashMap<String, Object>) rootElement.get("structure");
	@SuppressWarnings("unchecked")
	private static HashMap<String, Object> mapElement = (HashMap<String, Object>) structureElement.get("map");

	protected VABConnectionManager connManager = new VABConnectionManager(new TestsuiteDirectory(),
			new ConnectorProvider() {

				@Override
				protected IModelProvider createProvider(String addr) {
					return buildProvider();
				}
			});

	@Override
	protected VABConnectionManager getConnectionManager() {
		return connManager;
	}

	@SuppressWarnings("unchecked")
	private static IModelProvider buildProvider() {
		// Create primitive lambda elements
		HashMap<String, Object> primitives = (HashMap<String, Object>) rootElement.get("primitives");
		// Has no hidden setter (==null), so value should be completely replaced when set
		primitives.put("integer", VABLambdaProviderHelper.createSimple((Supplier<Object>) () -> {
			return 123;
		}, null));
		// Has a hidden setter, so write access to this element changes "doubleElement", which is returned by the getter
		primitives.put("double", VABLambdaProviderHelper.createSimple((Supplier<Object>) () -> {
			return doubleElement;
		}, (Consumer<Object>) (newObject) -> {
			doubleElement = (double) newObject;
		}));

		// Create collection lambda element
		HashMap<String, Object> collections = (HashMap<String, Object>) rootElement.get("structure");
		Map<String, Object> collectionAccessor = VABLambdaProviderHelper.createCollection((Supplier<Object>) () -> {
			return collectionElement;
		}, (Consumer<Collection<Object>>) (collection) -> {
			collectionElement = new ReferencedArrayList<>(collection);
		}, (Consumer<Object>) (value) -> {
			collectionElement.add(value);
		}, (Consumer<Object>) (object) -> {
			collectionElement.remove(object);
		}, (Consumer<String>) (key) -> {
			if (key != null && key.length() >= 7) {
				String refName = key.substring("byRef_".length());
				Integer reference = Integer.valueOf(refName);
				Object object = collectionElement.getByReference(reference);
				collectionElement.remove(object);
			}
		});
		collections.put("list", collectionAccessor);

		// Create accessors for map property propertyMap
		rootAccessor = VABLambdaProviderHelper.createMap((Supplier<?>) () -> {
			return rootElement;
		}, (Consumer<Map<String, Object>>) (map) -> {
			rootElement = (HashMap<String, Object>) map;
		}, (BiConsumer<String, Object>) (key, value) -> {
			rootElement.put(key, value);
		}, (Consumer<Object>) (o) -> {
		}, (Consumer<String>) (key) -> {
			rootElement.remove(key);
		});

		// Create first accessor for structure/map element (-> test nested lambda properties)
		Map<String, Object> structureAccessor = VABLambdaProviderHelper.createMap((Supplier<?>) () -> {
			return structureElement;
		}, (Consumer<Map<String, Object>>) (map) -> {
			structureElement = (HashMap<String, Object>) map;
		}, (BiConsumer<String, Object>) (key, value) -> {
			structureElement.put(key, value);
		}, (Consumer<Object>) (o) -> {
		}, (Consumer<String>) (key) -> {
			structureElement.remove(key);
		});
		// Replace actual structure property with lambda accessor
		rootElement.put("structure", structureAccessor);
		// Create second accessor for structure/map element (-> nested lambda properties)
		Map<String, Object> mapAccessor = VABLambdaProviderHelper.createMap((Supplier<?>) () -> {
			return mapElement;
		}, (Consumer<Map<String, Object>>) (map) -> {
			mapElement = (HashMap<String, Object>) map;
		}, (BiConsumer<String, Object>) (key, value) -> {
			mapElement.put(key, value);
		}, (Consumer<Object>) (o) -> {
		}, (Consumer<String>) (key) -> {
			mapElement.remove(key);
		});
		// Replace actual map property with lambda accessor
		structureElement.put("map", mapAccessor);

		VABLambdaProvider provider = new VABLambdaProvider(rootAccessor);
		return provider;
	}
}
