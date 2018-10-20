package org.eclipse.basyx.components.cfgprovider;

import java.util.Map;

import org.eclipse.basyx.sdk.provider.hashmap.VABHashmapProvider;
import org.eclipse.basyx.sdk.provider.hashmap.aas.Submodel;
import org.eclipse.basyx.sdk.provider.hashmap.aas.property.PropertySingleValued;



/**
 * Asset administration shell sub model provider that exports a properties file
 * 
 * @author kuhn
 *
 */
public class CFGSubModelProvider extends VABHashmapProvider {

	
	/**
	 * This is a sub model
	 */
	protected Submodel submodelData = null;

	
	/**
	 * Constructor
	 */
	public CFGSubModelProvider(Map<Object, Object> cfgValues) {
		// Call base constructor
		super();

		
		// Create sub model
		submodelData = new Submodel();

		// Load predefined elements from submodel
		elements.putAll(submodelData);

		// Add properties
		for (Object key: cfgValues.keySet()) {
			// Create example properties
			submodelData.getProperties().put(key.toString(), new PropertySingleValued(cfgValues.get(key)));
			
			// Debug output
			System.out.println("Adding property: "+key.toString()+" = "+cfgValues.get(key));
		}
		
		// Print configuration values
		System.out.println("CFG exported");
	}
}
