package org.eclipse.basyx.vab.provider.lambda;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Helper class which allows to easily create properties as processed by the
 * {@link org.eclipse.basyx.vab.provider.lambda.VABLambdaProvider}
 * 
 * @author schnicke
 *
 */
public class VABLambdaProviderHelper {
	/**
	 * Creates a property referencing a simple value, e.g. int, double, ..
	 * 
	 * @param get
	 *            Method used to get the value
	 * @param set
	 *            Method used to set the value
	 * @return
	 */
	public static Map<String, Object> createSimple(Supplier<Object> get, Consumer<Object> set) {
		Map<String, Object> value = new HashMap<>();
		value.put(VABLambdaProvider.VALUE_GET_SUFFIX, get);
		value.put(VABLambdaProvider.VALUE_SET_SUFFIX, set);
		return value;
	}

	/**
	 * Creates a property referencing a map
	 * 
	 * @param get
	 *            Method used to get the map
	 * @param set
	 *            Method used to set the map
	 * @param insert
	 *            Method used to insert an element into the map
	 * @param remove
	 *            Method used to remove an element from the map
	 * @return
	 */
	public static Map<String, Object> createMap(Supplier<?> get, Consumer<?> set, BiConsumer<String, Object> insert, Consumer<Object> remove) {
		Map<String, Object> value = new HashMap<>();
		value.put(VABLambdaProvider.VALUE_GET_SUFFIX, get);
		value.put(VABLambdaProvider.VALUE_SET_SUFFIX, set);
		value.put(VABLambdaProvider.VALUE_REMOVE_SUFFIX, remove);
		value.put(VABLambdaProvider.VALUE_INSERT_SUFFIX, insert);
		return value;
	}

	/**
	 * Creates a property referencing a collection
	 * 
	 * @param get
	 *            Method used to get the collection
	 * @param set
	 *            Method used to set the collection
	 * @param insert
	 *            Method used to insert an element into the collection
	 * @param remove
	 *            Method used to remove an element from the collection
	 * @return
	 */
	public static Map<String, Object> createCollection(Supplier<?> get, Consumer<?> set, Consumer<Object> insert, Consumer<Object> remove) {
		Map<String, Object> value = new HashMap<>();
		value.put(VABLambdaProvider.VALUE_GET_SUFFIX, get);
		value.put(VABLambdaProvider.VALUE_SET_SUFFIX, set);
		value.put(VABLambdaProvider.VALUE_REMOVE_SUFFIX, remove);
		value.put(VABLambdaProvider.VALUE_INSERT_SUFFIX, insert);
		return value;
	}
}
