package org.eclipse.basyx.vab.provider.hashmap;

import java.util.Map;

import org.eclipse.basyx.vab.provider.IVABElementHandler;
import org.eclipse.basyx.vab.provider.VABModelProvider;

/**
 * A simple VAB model provider based on a HashMap.
 * 
 * This provider demonstrates the inclusion of new data sources to the VAB
 * 
 * @author kuhn, schnicke, espen
 *
 */
public class VABHashmapProvider extends VABModelProvider {
	public VABHashmapProvider(Map<String, Object> elements) {
		super(elements, new VABMapHandler());
	}

	protected VABHashmapProvider(Map<String, Object> elements, IVABElementHandler handler) {
		super(elements, handler);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getElements() {
		return (Map<String, Object>) elements;
	}
}
