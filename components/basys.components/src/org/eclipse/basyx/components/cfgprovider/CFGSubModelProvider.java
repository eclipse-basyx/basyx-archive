package org.eclipse.basyx.components.cfgprovider;

import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.basyx.components.provider.BaseConfiguredProvider;
import org.eclipse.basyx.vab.provider.lambda.VABLambdaProviderHelper;



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
			submodelData.getProperties().put(key.toString(), createSubmodelElement(key, cfgValues.get(key), cfgValues));
			
			// Debug output
			System.out.println("Adding configured property: "+key.toString()+" = "+cfgValues.get(key));
		}
		

		// Add lambda expression
		Map<String, Object> property4 = VABLambdaProviderHelper.createSimple((Supplier<Object>) () -> {
			return abc;
		}, null);

		System.out.println("------------ P4 -------------");
		printHashMap(property4, 0);
		
		cfgValues.put("prop4.type", "PropertySingleValued");
		
		submodelData.getProperties().put("prop4", createSubmodelElement("prop4", property4, cfgValues));
	}
}

