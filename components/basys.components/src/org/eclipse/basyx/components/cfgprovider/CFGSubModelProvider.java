package org.eclipse.basyx.components.cfgprovider;

import java.util.Map;

import org.eclipse.basyx.components.provider.BaseConfiguredProvider;
import org.eclipse.basyx.sdk.provider.hashmap.aas.Submodel;



/**
 * Asset administration shell sub model provider that exports a properties file
 * 
 * @author kuhn
 *
 */
public class CFGSubModelProvider extends BaseConfiguredProvider {

	
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
		submodelData = createSubModel(cfgValues);

		// Load predefined elements from sub model
		elements.putAll(submodelData);

		// Add properties
		for (String key: getConfiguredProperties(cfgValues)) {
			// Create properties
			submodelData.getProperties().put(key.toString(), createProperty(key, cfgValues.get(key), cfgValues));
			
			// Debug output
			System.out.println("Adding property: "+key.toString()+" = "+cfgValues.get(key));
		}
		
		// Print configuration values
		System.out.println("CFG exported");
	}
}

