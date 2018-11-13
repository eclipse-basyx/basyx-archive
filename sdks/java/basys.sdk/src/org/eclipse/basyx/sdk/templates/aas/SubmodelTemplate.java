package org.eclipse.basyx.sdk.templates.aas;

import org.eclipse.basyx.sdk.provider.hashmap.VABHashmapProvider;
import org.eclipse.basyx.sdk.provider.hashmap.aas.Submodel;



/**
 * Base class for sub model templates
 * 
 * @author kuhn
 *
 */
public class SubmodelTemplate extends VABHashmapProvider {

	
	/**
	 * This is a sub model
	 */
	protected Submodel submodelData = null;

	
	
	
	
	/**
	 * Return created sub model
	 */
	public Submodel getSubModel() {
		return submodelData;
	}
}
