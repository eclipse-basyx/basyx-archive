package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier;

import java.util.HashMap;


/**
 * Identifiable class 
 * 
 * @author kuhn
 *
 */
public class Identifiable extends HashMap<String, Object> {


	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;



	/**
	 * Constructor
	 */
	public Identifiable() {
		// Add all attributes form Packageable and Referable
		this.putAll(new Packageable());
		this.putAll(new Referable());

		// Default values
		put("administration", null);
		put("identification", new Identification());
	}
}

