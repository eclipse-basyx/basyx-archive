package org.eclipse.basyx.sandbox.components.cfgprovider;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.components.provider.BaseConfiguredProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Asset administration shell sub model provider that exports a properties file
 * 
 * @author kuhn
 *
 */
public class RawCFGSubModelProvider extends BaseConfiguredProvider {
	
	/**
	 * Initiates a logger using the current class
	 */
	private static final Logger logger = LoggerFactory.getLogger(RawCFGSubModelProvider.class);

	private static final String FTYPE = "$ftype";

	/**
	 * Constructor
	 */
	@SuppressWarnings("unchecked")
	public RawCFGSubModelProvider(Map<Object, Object> cfgValues) {
		// Call base constructor -> creates base submodelData from cfgValues
		super(cfgValues);

		// Load properties
		for (Object key : cfgValues.keySet()) {
			// Do not put meta data keys into map
			if (((String) key).endsWith(FTYPE))
				continue;

			// Get path to element
			String[] path = splitPath((String) key);

			// Create path
			Map<String, Object> scope = new HashMap<>();
			scope = submodelData;
			for (int i = 0; i < path.length - 1; i++) {
				if (!scope.containsKey(path[i]))
					scope.put(path[i], new HashMap<String, Object>());
				scope = (Map<String, Object>) scope.get(path[i]);
			}

			// Get and optionally convert value
			Object value = cfgValues.get(key);
			// - Cast value if requested by user
			if (cfgValues.get(key + FTYPE) != null)
				switch ((String) cfgValues.get(key + FTYPE)) {
				case "int":
					value = Integer.parseInt((String) value);
					break;
				case "boolean":
					value = Boolean.parseBoolean((String) value);
					break;
				case "float":
					value = Float.parseFloat((String) value);
					break;

				default:
					logger.error("Unknown type:" + cfgValues.get(key + FTYPE));
				}

			logger.debug("Putting:" + key + " = " + cfgValues.get(key) + " as " + value.getClass().getName());

			scope.put(path[path.length - 1], value);
		}

		// Push data to provider
		setSubmodel(submodelData);

		// Print configuration values
		logger.debug("CFG exported");
	}
}
