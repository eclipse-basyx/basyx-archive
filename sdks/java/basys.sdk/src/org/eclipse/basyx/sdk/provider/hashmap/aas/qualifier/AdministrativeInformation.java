package org.eclipse.basyx.sdk.provider.hashmap.aas.qualifier;

import java.util.HashMap;




/**
 * AdministrativeInformation class 
 * 
 * @author kuhn
 *
 */
public class AdministrativeInformation extends HashMap<String, Object> {
	
	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	
	/**
	 * Default constructor
	 */
	public AdministrativeInformation() {
		// Add all attributes form HasTemplate
		putAll(new HasTemplate());

		// Default values
		put("version",  ""); 
		put("revision", "");
	}


	/**
	 * Constructor
	 */
	public AdministrativeInformation(String version, String revision) {
		// Add all attributes form HasTemplate
		putAll(new HasTemplate());

		// Default values
		put("version",  version); 
		put("revision", revision);
	}
}
