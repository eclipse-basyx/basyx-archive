package org.eclipse.basyx.vab.directory.api;

/**
 * Directory service SDK interface.
 * 
 * @author kuhn
 *
 */
public interface IVABDirectoryService {

	
	/**
	 * Add a mapping to directory
	 */
	public IVABDirectoryService addMapping(String key, String value);

	
	/**
	 * Remove a mapping from directory
	 */
	public void removeMapping(String key);
	

	/**
	 * Lookup method maps key "id" to value
	 */
	public String lookup(String id);
}
