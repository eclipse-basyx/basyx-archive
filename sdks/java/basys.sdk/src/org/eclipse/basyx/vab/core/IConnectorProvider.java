/**
 * 
 */
package org.eclipse.basyx.vab.core;

/**
 * @author schnicke
 *
 */
public interface IConnectorProvider {
	/**
	 * Selects an appropriate connector based on addr
	 * 
	 * @param addr
	 * @return
	 */
	public IModelProvider getConnector(String addr);
}
