package org.eclipse.basyx.components.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaSyxConfiguration {
	private static Logger logger = LoggerFactory.getLogger(BaSyxConfiguration.class);

	// Properties in this configuration
	private Map<String, String> values;

	/**
	 * Constructor that takes the configuration's default values.
	 * All the keys in the map are the name of the properties that are stored and loaded in this configuration.
	 */
	public BaSyxConfiguration(Map<String, String> defaultValues) {
		this.values = defaultValues;
	}

	public static InputStream getResourceStream(String relativeResourcePath) {
		ClassLoader classLoader = BaSyxContextConfiguration.class.getClassLoader();
		return classLoader.getResourceAsStream(relativeResourcePath);
	}

	public static String getResourceString(String relativeResourcePath) throws IOException {
		ClassLoader classLoader = BaSyxContextConfiguration.class.getClassLoader();
		return IOUtils.resourceToString(relativeResourcePath, StandardCharsets.UTF_8, classLoader);
	}

	/**
	 * Load the configuration from a path relative to the current resource folder
	 * 
	 * @param relativeResourcePath Path to the resource in the resource folder. In a maven project, the resources
	 *                             are located at /src/main/resources by default.
	 */
	public void loadFromResource(String relativeResourcePath) {
		InputStream input = getResourceStream(relativeResourcePath);
		if (input == null) {
			logger.error("No properties found at '%s', using default values", relativeResourcePath);
			return;
		}

		// Try to load property file for servlet configuration
		logger.info("Loading properties from file '" + relativeResourcePath + "'");

		Properties properties = new Properties();
		try {
			properties.load(input);
			loadFromProperties(properties);
		} catch (IOException e) {
			logger.error("Could not load properties", e);
		}
	}

	/**
	 * Load the configuration directly from properties.
	 */
	public void loadFromProperties(Properties properties) {
		for (Object property : properties.keySet()) {
			String propertyName = (String) property;
			String loaded = properties.getProperty(propertyName);
			if (values.containsKey(propertyName)) {
				logger.info(propertyName + ": '" + loaded + "'");
			} else {
				logger.debug(propertyName + ": '" + loaded + "'");
			}
			values.put(propertyName, loaded);
		}
	}

	/**
	 * Sets a property, if it is contained in this configuration
	 * 
	 * @param name  The name of the property
	 * @param value The new value of the property
	 */
	public void setProperty(String name, String value) {
		values.put(name, value);
	}

	/**
	 * Queries a property
	 */
	public String getProperty(String name) {
		return values.get(name);
	}
}
