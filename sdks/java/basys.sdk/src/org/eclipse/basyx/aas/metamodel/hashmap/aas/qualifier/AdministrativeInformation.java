package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier;

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
	 * Constructor
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
	
	
	
	
	/**
	 * Get value of 'version' property
	 */
	public String getVersion() {
		return (String) get("version");
	}

	/**
	 * Update value of 'version' property
	 */
	public void setVersion(String newValue) {
		put("version", newValue);
	}
	
	

	/**
	 * Get value of 'revision' property
	 */
	public String getRevision() {
		return (String) get("revision");
	}
	
	/**
	 * Update value of 'revision' property
	 */
	public void setRevision(String newValue) {
		put("revision", newValue);
	}
}
