package org.eclipse.basyx.components.cfgprovider;

import java.util.Map;

import org.eclipse.basyx.aas.impl.provider.javahandler.genericsm.GenericHandlerSubmodel;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic.DataType;



/**
 * Asset administration shell sub model provider that exports a properties file
 * 
 * @author kuhn
 *
 */
public class CFGSubModelProvider extends GenericHandlerSubmodel {

	
	/**
	 * Constructor
	 */
	public CFGSubModelProvider(String smName, String smID, String smType, String aasName, String aasID, Map<Object, Object> cfgValues) {
		// Call base constructor
		super(AssetKind.INSTANCE, smName, smID, smType, aasName, aasID);
		
		// Add properties
		for (Object key: cfgValues.keySet()) {
			// Add properties
			addProperty(key.toString(), DataType.STRING, cfgValues.get(key));
			
			// Debug output
			System.out.println("Adding property: "+key.toString()+" = "+cfgValues.get(key));
		}
		
		// Print configuration values
		System.out.println("CFG exported");
	}
}
