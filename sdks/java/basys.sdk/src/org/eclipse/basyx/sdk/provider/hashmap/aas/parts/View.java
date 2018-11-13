package org.eclipse.basyx.sdk.provider.hashmap.aas.parts;

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.basyx.sdk.provider.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.sdk.provider.hashmap.aas.qualifier.HasTemplate;
import org.eclipse.basyx.sdk.provider.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.vab.ref.VABElementRef;




/**
 * Asset Administration Shell body class
 * 
 * @author kuhn
 *
 */
public class View extends HashMap<String, Object> {

	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	
	/**
	 * Constructor
	 */
	public View() {
		// Add qualifiers
		putAll(new HasSemantics());
		putAll(new HasTemplate());
		putAll(new Referable());
		
		// Default values
		put("containedElement", new HashSet<VABElementRef>());
	}
}
