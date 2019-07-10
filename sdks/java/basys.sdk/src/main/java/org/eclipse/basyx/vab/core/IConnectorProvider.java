/**
 * 
 */
package org.eclipse.basyx.vab.core;

/**
 * Base interface for providers that return IModelProviders that connects to
 * specific addresses
 * 
 * @author schnicke
 *
 */
public interface IConnectorProvider {

	/**
	 * Gets an IModelProvider connecting the specific address
	 * 
	 * @param addr
	 * @return
	 */
	public IModelProvider getConnector(String addr);
}
