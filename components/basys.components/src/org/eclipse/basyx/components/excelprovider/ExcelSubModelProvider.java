package org.eclipse.basyx.components.excelprovider;

import java.util.Map;

import org.eclipse.basyx.sdk.provider.hashmap.VABHashmapProvider;
import org.eclipse.basyx.sdk.provider.hashmap.aas.Submodel;
import org.eclipse.basyx.sdk.provider.hashmap.aas.property.PropertySingleValued;



/**
 * Asset administration shell sub model provider that exports an Excel file
 * 
 * @author kuhn
 *
 */
public class ExcelSubModelProvider extends VABHashmapProvider {

	
	/**
	 * This is a sub model
	 */
	protected Submodel submodelData = null;

	
	
	/**
	 * Constructor
	 */
	public ExcelSubModelProvider(Map<Object, Object> cfgValues) {
		// Call base constructor
		super();

		
		// Create sub model
		submodelData = new Submodel();

		// Load predefined elements from submodel
		elements.putAll(submodelData);

		// Load Excel file
		
		
		// Add properties
		for (Object key: cfgValues.keySet()) {
			// Do not process internal keys with provider configuration values
			if (isInternalKey(key.toString())) continue;
			
			// Create example properties
			submodelData.getProperties().put(key.toString(), new PropertySingleValued(cfgValues.get(key)));
			
			// Debug output
			System.out.println("Adding property: "+key.toString()+" = "+cfgValues.get(key));
		}
		
		// Print configuration values
		System.out.println("CFG exported");
	}
	
	
	/**
	 * Check if a key is an internal key. Internal keys start with "basyx_"
	 */
	protected boolean isInternalKey(String key) {
		return key.startsWith("basyx_");
	}
	
	
	/**
	 * Get sheet number
	 */
	
	
	/**
	 * Get line number
	 */
	
	
	/**
	 * Get column number
	 */
	
	
	
	/**
	 * Get cell value from excel file
	 */
	
	
	/**
	 * Update excel file cell
	 */
}
