package org.eclipse.basyx.vab.modelprovider.map;

import java.util.Map;

import org.eclipse.basyx.vab.modelprovider.generic.IVABElementHandler;
import org.eclipse.basyx.vab.modelprovider.generic.VABModelProvider;



/**
 * A simple VAB model provider based on a HashMap.
 * 
 * This provider demonstrates the inclusion of new data sources to the VAB
 * 
 * @author kuhn, schnicke, espen
 *
 */
public class VABMapProvider extends VABModelProvider {
	public VABMapProvider(Map<String, Object> elements) {
		super(elements, new VABMapHandler());
	}

	protected VABMapProvider(Map<String, Object> elements, IVABElementHandler handler) {
		super(elements, handler);
	}

	@SuppressWarnings("unchecked")
	protected Map<String, Object> getElements() {
		return (Map<String, Object>) elements;
	}
}
