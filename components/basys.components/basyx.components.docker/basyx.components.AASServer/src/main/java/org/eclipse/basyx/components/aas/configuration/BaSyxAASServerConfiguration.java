package org.eclipse.basyx.components.aas.configuration;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.components.configuration.BaSyxConfiguration;

/**
 * Represents a BaSyx server configuration for an AAS Server with any backend,
 * that can be loaded from a properties file.
 * 
 * @author espen
 *
 */
public class BaSyxAASServerConfiguration extends BaSyxConfiguration {
	// Default BaSyx AAS configuration
	public static final String DEFAULT_BACKEND = AASServerBackend.INMEMORY.toString();
	public static final String DEFAULT_SOURCE = "";
	public static final String DEFAULT_REGISTRY = "";

	// Configuration keys
	public static final String REGISTRY = "registry.path";
	public static final String BACKEND = "aas.backend";
	public static final String SOURCE = "aas.source";

	// The default path for the context properties file
	public static final String DEFAULT_CONFIG_PATH = "aas.properties";

	// The default key for variables pointing to the configuration file
	public static final String DEFAULT_FILE_KEY = "BASYX_AAS";

	public static Map<String, String> getDefaultProperties() {
		Map<String, String> defaultProps = new HashMap<>();
		defaultProps.put(BACKEND, DEFAULT_BACKEND);
		defaultProps.put(SOURCE, DEFAULT_SOURCE);
		defaultProps.put(REGISTRY, DEFAULT_REGISTRY);
		return defaultProps;
	}

	/**
	 * Empty Constructor - use default values
	 */
	public BaSyxAASServerConfiguration() {
		super(getDefaultProperties());
	}

	/**
	 * Constructor with initial configuration
	 * 
	 * @param backend
	 *            The backend for the AASServer
	 * @param source
	 *            The file source for the AASServer (e.g. an .aasx file)
	 */
	public BaSyxAASServerConfiguration(AASServerBackend backend, String source) {
		super(getDefaultProperties());
		setAASBackend(backend);
		setAASSource(source);
	}

	/**
	 * Constructor with initial configuration values
	 * 
	 * @param backend
	 *            The backend for the AASServer
	 * @param source
	 *            The file source for the AASServer (e.g. an .aasx file)
	 * @param registryUrl
	 *            The url to the registry
	 */
	public BaSyxAASServerConfiguration(AASServerBackend backend, String source, String registryUrl) {
		super(getDefaultProperties());
		setAASBackend(backend);
		setAASSource(source);
		setRegistry(registryUrl);
	}

	/**
	 * Constructor with predefined value map
	 */
	public BaSyxAASServerConfiguration(Map<String, String> values) {
		super(values);
	}

	public void loadFromDefaultSource() {
		loadFileOrDefaultResource(DEFAULT_FILE_KEY, DEFAULT_CONFIG_PATH);
	}

	public AASServerBackend getAASBackend() {
		return AASServerBackend.fromString(getProperty(BACKEND));
	}

	public void setAASBackend(AASServerBackend backend) {
		setProperty(BACKEND, backend.toString());
	}

	public String getAASSource() {
		return getProperty(SOURCE);
	}

	public void setAASSource(String source) {
		setProperty(SOURCE, source);
	}

	public String getRegistry() {
		return getProperty(REGISTRY);
	}

	public void setRegistry(String registryPath) {
		setProperty(REGISTRY, registryPath);
	}
}
