package org.eclipse.basyx.components.configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a BaSyx docker configuration for a docker environment.
 * 
 * @author espen
 *
 */
public class BaSyxDockerConfiguration extends BaSyxConfiguration {
	// Default BaSyx Context configuration
	private static final int DEFAULT_HOSTPORT = 8082;
	private static final int DEFAULT_CONTAINERPORT = 4000;
	private static final String DEFAULT_IMAGENAME = "basys/component";
	private static final String DEFAULT_CONTAINERNAME = "component";

	private static final String HOSTPORT = "BASYX_HOST_PORT";
	private static final String CONTAINERPORT = "BASYX_CONTAINER_PORT";
	private static final String IMAGENAME = "BASYX_IMAGE_NAME";
	private static final String CONTAINERNAME = "BASYX_CONTAINER_NAME";

	// The default path for the context properties file
	public static final String DEFAULT_CONFIG_PATH = ".env";

	public static Map<String, String> getDefaultProperties() {
		Map<String, String> defaultProps = new HashMap<>();
		defaultProps.put(HOSTPORT, Integer.toString(DEFAULT_HOSTPORT));
		defaultProps.put(CONTAINERPORT, Integer.toString(DEFAULT_CONTAINERPORT));
		defaultProps.put(IMAGENAME, DEFAULT_IMAGENAME);
		defaultProps.put(CONTAINERNAME, DEFAULT_CONTAINERNAME);

		return defaultProps;
	}

	public BaSyxDockerConfiguration() {
		super(getDefaultProperties());
	}

	public BaSyxDockerConfiguration(Map<String, String> values) {
		super(values);
	}

	public int getHostPort() {
		return Integer.parseInt(getProperty(HOSTPORT));
	}

	public int getContainerPort() {
		return Integer.parseInt(getProperty(CONTAINERPORT));
	}

	public String getImageName() {
		return getProperty(IMAGENAME);
	}

	public String getContainerName() {
		return getProperty(CONTAINERNAME);
	}
}
