package org.eclipse.basyx.aas.metamodel.hashmap.aas.parts;

import java.util.HashMap;
import java.util.HashSet;




/**
 * Asset Administration Shell header class
 * 
 * @author kuhn
 *
 */
public class Header extends HashMap<String, Object> {

	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	
	/**
	 * Constructor
	 */
	public Header() {
		// Default values
		put("primaryAsset",    null);
		put("auxiliaryAssets", new HashSet<Asset>());
	}
}
