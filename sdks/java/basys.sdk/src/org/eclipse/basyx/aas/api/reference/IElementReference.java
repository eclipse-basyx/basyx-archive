package org.eclipse.basyx.aas.api.reference;



/**
 * Reference to a remote property
 * 
 * @author kuhn
 *
 */
public interface IElementReference {

	
	/**
	 * Get AAS ID
	 */
	public String getAASID();
	
	
	/**
	 * Get sub model ID
	 */
	public String getSubModelID();
	
	
	/**
	 * Get path to property
	 */
	public String getPathToProperty();
	
	
	/**
	 * Get server path to property that should be used when accessing the property
	 */
	public String getServerPathToProperty();

	
	/**
	 * Return the unique ID that identifies an element
	 * 
	 * @return unique ID
	 */
	public String getId();
	
	
	/**
	 * Indicate AAS reference
	 */
	public boolean isAASReference();
	
	
	/**
	 * Indicate sub model reference
	 */
	public boolean isSubModelReference();
	
	
	/**
	 * Indicate property reference
	 */
	public boolean isPropertyReference();
	
	/**
	 * Indicate if it is a collection
	 */
	public boolean isCollection();
	
	/**
	 * Indicates if it is a map
	 */
	public boolean isMap();
	
	
	/**
	 * Add scope to aasID of element reference
	 */
	public void addScope(String scope);
	
	
	/**
	 * Set server path
	 */
	public void setServerpath(String serverPath);
}
