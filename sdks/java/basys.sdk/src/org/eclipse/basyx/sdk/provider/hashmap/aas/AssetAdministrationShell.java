package org.eclipse.basyx.sdk.provider.hashmap.aas;


import java.util.HashMap;
import org.eclipse.basyx.sdk.provider.hashmap.aas.parts.Body;
import org.eclipse.basyx.sdk.provider.hashmap.aas.parts.Header;
import org.eclipse.basyx.sdk.provider.hashmap.aas.qualifier.*;



/**
 * AssetAdministrationShell class
 * 
 * FIXME: Add dictionary and securityAttributes
 * 
 * @author kuhn
 *
 */
public class AssetAdministrationShell extends HashMap<String, Object> {

	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	
	/**
	 * Asset Administration Shell header
	 */
	protected Header header = null;

	
	/**
	 * Asset Administration Shell body
	 */
	protected Body body = null;
	

	
	
	
	/**
	 * Constructor
	 */
	public AssetAdministrationShell() {
		// Create header and body
		header = new Header();
		body   = new Body();
		
		// Add qualifiers
		putAll(new HasTemplate());
		putAll(new Identifiable());
		putAll(new Packageable());

		// Default values
		put("predecessor", null);
		
		// Header and body
		put("header",  header);
		put("body",    body);
	}
	
	
	
	/**
	 * Get AAS header
	 */
	public Header getHeader() {
		return header;
	}

	
	/**
	 * Get AAS body
	 */
	public Body getBody() {
		return body;
	}
}
