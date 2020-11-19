package org.eclipse.basyx.sandbox.components.cfgprovider;

import java.util.Map;

import org.eclipse.basyx.components.provider.BaseConfiguredProvider;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.restapi.SubmodelElementProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Asset administration shell sub model provider that exports a properties file
 * 
 * @author kuhn
 *
 */
public class CFGSubModelProvider extends BaseConfiguredProvider {

	/**
	 * Initiates a logger using the current class
	 */
	private static final Logger logger = LoggerFactory.getLogger(CFGSubModelProvider.class);

	/**
	 * Constructor
	 */
	public CFGSubModelProvider(Map<Object, Object> cfgValues) {
		// Call base constructor
		super(cfgValues);

		// Add properties
		for (String key: getConfiguredProperties(cfgValues)) {
			// Create properties
			SubmodelElement elem = createSubmodelElement(key, cfgValues.get(key), cfgValues);
			createValue("submodel/" + SubmodelElementProvider.ELEMENTS, elem);
			
			// Debug output
			logger.debug("Adding configured property: "+key.toString()+" = "+cfgValues.get(key));
		}
	}
}

