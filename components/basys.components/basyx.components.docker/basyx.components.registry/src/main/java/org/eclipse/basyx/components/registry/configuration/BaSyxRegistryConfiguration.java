package org.eclipse.basyx.components.registry.configuration;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.components.configuration.BaSyxConfiguration;

/**
 * Represents a BaSyx registry configuration for a BaSyx Registry with any backend,
 * that can be loaded from a properties file.
 * 
 * @author espen
 *
 */
public class BaSyxRegistryConfiguration extends BaSyxConfiguration {
	// Default BaSyx Context configuration
	public static final String DEFAULT_BACKEND = RegistryBackend.INMEMORY.toString();

	// Configuration keys
	public static final String BACKEND = "registry.backend";

	// The default path for the context properties file
	public static final String DEFAULT_CONFIG_PATH = "registry.properties";

	// The default key for variables pointing to the configuration file
	public static final String DEFAULT_FILE_KEY = "BASYX_REGISTRY";

	public static Map<String, String> getDefaultProperties() {
		Map<String, String> defaultProps = new HashMap<>();
		defaultProps.put(BACKEND, DEFAULT_BACKEND);
		return defaultProps;
	}

	public BaSyxRegistryConfiguration() {
		super(getDefaultProperties());
	}

	public BaSyxRegistryConfiguration(RegistryBackend backend) {
		super(getDefaultProperties());
		setRegistryBackend(backend);
	}

	public BaSyxRegistryConfiguration(Map<String, String> values) {
		super(values);
	}

	public void loadFromDefaultSource() {
		loadFileOrDefaultResource(DEFAULT_FILE_KEY, DEFAULT_CONFIG_PATH);
	}

	public RegistryBackend getRegistryBackend() {
		return RegistryBackend.fromString(getProperty(BACKEND));
	}

	public void setRegistryBackend(RegistryBackend backend) {
		setProperty(BACKEND, backend.toString());
	}
}
