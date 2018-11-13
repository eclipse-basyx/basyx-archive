package org.eclipse.basyx.aas.metamodel.hashmap.aas.parts;

import java.util.HashMap;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasTemplate;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Typable;




/**
 * Asset Administration Shell header class
 * 
 * @author kuhn
 *
 */
public class Asset extends HashMap<String, Object> {

	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	
	/**
	 * Constructor
	 */
	public Asset() {
		// Add qualifiers
		putAll(new HasTemplate());
		putAll(new Identifiable());
		putAll(new Typable());

		// Default values
		// - FIXME: Implement a submodel reference class
		put("assetIdentificationModel",  null);
	}
}
