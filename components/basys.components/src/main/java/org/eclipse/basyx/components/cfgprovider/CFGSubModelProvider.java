package org.eclipse.basyx.components.cfgprovider;

import java.util.Map;

import org.eclipse.basyx.components.provider.BaseConfiguredProvider;



/**
 * Asset administration shell sub model provider that exports a properties file
 * 
 * @author kuhn
 *
 */
public class CFGSubModelProvider extends BaseConfiguredProvider {

	
	
	
	int abc = 1234;
	
	/**
	 * Constructor
	 */
	public CFGSubModelProvider(Map<Object, Object> cfgValues) {
		// Call base constructor
		super(cfgValues);

		// Add properties
		for (String key: getConfiguredProperties(cfgValues)) {
			// Create properties
			submodelData.getDataElements().put(key.toString(), createSubmodelElement(key, cfgValues.get(key), cfgValues));
			
			// Debug output
			System.out.println("Adding configured property: "+key.toString()+" = "+cfgValues.get(key));
		}
	}
}

