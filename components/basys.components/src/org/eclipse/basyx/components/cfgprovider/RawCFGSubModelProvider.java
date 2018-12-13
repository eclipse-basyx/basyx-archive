package org.eclipse.basyx.components.cfgprovider;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.components.provider.BaseConfiguredProvider;



/**
 * Asset administration shell sub model provider that exports a properties file
 * 
 * @author kuhn
 *
 */
public class RawCFGSubModelProvider extends BaseConfiguredProvider {

		
	/**
	 * Constructor
	 */
	@SuppressWarnings("unchecked")
	public RawCFGSubModelProvider(Map<Object, Object> cfgValues) {
		// Call base constructor
		super(cfgValues);

		
		// Create sub model
		submodelData = createSubModel(cfgValues);

		// Load predefined elements from sub model
		elements.putAll(submodelData);


		// Load properties
		for (Object key: cfgValues.keySet()) {
			// Get path to element
			String[] path = splitPath((String) key);
			
			// Create path
			Map<String, Object> scope = elements;
			for (int i=0; i<path.length-1; i++) {
				if (!scope.containsKey(path[i])) scope.put(path[i], new HashMap<String, Object>()); 
				scope = (Map<String, Object>) scope.get(path[i]);
			}
			
			System.out.println("Putting:"+key+" = "+cfgValues.get(key));
			
			//if (cfgValues.get(key).equals("8"))
				//scope.put(path[path.length-1], Integer.parseInt((String) cfgValues.get(key)));
			//else
				scope.put(path[path.length-1], cfgValues.get(key));
		}

		
		// Print configuration values
		System.out.println("CFG exported");
	}
}

