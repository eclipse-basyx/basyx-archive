package org.eclipse.basyx.vab.modelprovider.lambda;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.modelprovider.generic.IVABElementHandler;
import org.eclipse.basyx.vab.modelprovider.generic.VABMultiElementHandler;

/**
 * VABHandler that can additionally handle maps with hidden
 * get/set/delete/invoke properties.
 * 
 * @author schnicke, espen
 *
 */
public class VABLambdaHandler extends VABMultiElementHandler {
	public static final String VALUE_SET_SUFFIX = "set";
	public static final String VALUE_GET_SUFFIX = "get";
	public static final String VALUE_INSERT_SUFFIX = "insert";
	public static final String VALUE_REMOVEKEY_SUFFIX = "removeKey";
	public static final String VALUE_REMOVEOBJ_SUFFIX = "removeObject";

	public VABLambdaHandler(IVABElementHandler... handlers) {
		super(handlers);
	}

	@Override
	public Object postprocessObject(Object element) {
		return super.postprocessObject(resolveAll(element));
	}

	@Override
	public Object getElementProperty(Object element, String propertyName) throws Exception {
		return super.getElementProperty(resolveSingle(element), propertyName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean setModelPropertyValue(Object element, String propertyName, Object newValue) throws Exception {
		Object child = null;
		try {
			child = getElementProperty(element, propertyName);
		} catch (ResourceNotFoundException e) {}
		if (hasHiddenSetter(child)) {
			((Consumer<Object>) ((Map<String, Object>) child).get(VALUE_SET_SUFFIX)).accept(newValue);
			return true;
		} else if (hasHiddenInserter(element) && (resolveSingle(element) instanceof Map<?, ?>)) {
			((BiConsumer<String, Object>) ((Map<String, Object>) element).get(VALUE_INSERT_SUFFIX)).accept(propertyName,
					newValue);
			return true;
		} else {
			return super.setModelPropertyValue(resolveSingle(element), propertyName, newValue);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean createValue(Object element, Object newValue) throws Exception {
		if (hasHiddenInserter(element)) {
			((Consumer<Object>) ((Map<String, Object>) element).get(VALUE_INSERT_SUFFIX)).accept(newValue);
			return true;
		} else {
			return super.createValue(element, newValue);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteValue(Object element, String propertyName) throws Exception {
		if (hasHiddenKeyRemover(element)) {
			super.getElementProperty(resolveSingle(element), propertyName);
			Consumer<String> c = (Consumer<String>) ((Map<String, Object>) element).get(VALUE_REMOVEKEY_SUFFIX);
			c.accept(propertyName);
			return true;
		} else {
			return super.deleteValue(element, propertyName);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteValue(Object element, Object property) throws Exception {
		if (hasHiddenObjectRemover(element)) {
			if(resolveSingle(element) instanceof Map) {
				// Can not delete by value from Maps
				return false;
			}
			Consumer<Object> c = (Consumer<Object>) ((Map<String, Object>) element).get(VALUE_REMOVEOBJ_SUFFIX);
			c.accept(property);
			return true;
		} else {
			return super.deleteValue(element, property);
		}
	}

	@SuppressWarnings("unchecked")
	private Object resolveSingle(Object o) {
		while (hasHiddenGetter(o)) {
			Map<?, ?> map = (Map<?, ?>) o;
			o = ((Supplier<Object>) map.get(VALUE_GET_SUFFIX)).get();
		}
		return o;
	}

	/**
	 * Checks if a value is a raw value or points to a gettable property and
	 * resolves the underlying structure
	 */
	@SuppressWarnings("unchecked")
	private Object resolveAll(Object o) {
		o = resolveSingle(o);
		if (o instanceof Map<?, ?>) {
			return resolveMap((Map<String, Object>) o);
		} else if (o instanceof Collection<?>) {
			return resolveCollection((Collection<Object>) o);
		} else {
			return o;
		}
	}

	private Object resolveMap(Map<String, Object> map) {
		Map<String, Object> ret = new HashMap<>();
		for (String s : map.keySet()) {
			ret.put(s, resolveAll(map.get(s)));
		}
		return ret;
	}

	private Object resolveCollection(Collection<Object> coll) {
		List<Object> ret = new ArrayList<>(coll.size());
		for (Object o : coll) {
			ret.add(resolveAll(o));
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	private boolean hasHiddenInserter(Object elem) {
		if (elem instanceof Map<?, ?>) {
			Map<String, Object> map = (Map<String, Object>) elem;
			Object o = map.get(VALUE_INSERT_SUFFIX);
			return o instanceof BiConsumer<?, ?> || o instanceof Consumer<?>;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private boolean hasHiddenObjectRemover(Object elem) {
		if (elem instanceof Map<?, ?>) {
			Map<String, Object> map = (Map<String, Object>) elem;
			Object o = map.get(VALUE_REMOVEOBJ_SUFFIX);
			return o instanceof Consumer<?>;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private boolean hasHiddenKeyRemover(Object elem) {
		if (elem instanceof Map<?, ?>) {
			Map<String, Object> map = (Map<String, Object>) elem;
			Object o = map.get(VALUE_REMOVEKEY_SUFFIX);
			return o instanceof Consumer<?>;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private boolean hasHiddenSetter(Object elem) {
		if (elem instanceof Map<?, ?>) {
			Map<String, Object> map = (Map<String, Object>) elem;
			Object o = map.get(VALUE_SET_SUFFIX);
			return o instanceof Consumer<?>;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private boolean hasHiddenGetter(Object elem) {
		if (elem instanceof Map<?, ?>) {
			Map<String, Object> map = (Map<String, Object>) elem;
			Object o = map.get(VALUE_GET_SUFFIX);
			return o instanceof Supplier<?>;
		}
		return false;
	}
}
