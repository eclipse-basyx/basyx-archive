package org.eclipse.basyx.testsuite.regression.vab.coder.json;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.eclipse.basyx.vab.coder.json.provider.JSONProvider;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.api.IBaSyxConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is required for Meta-protocol integration testing. It makes
 * JSONProvider directly usable for the JSONConnector.
 * 
 * @author pschorn
 *
 * @param <T>
 *            should be VABMapProvider or stub
 */
public class IBasyxConnectorFacade<T extends IModelProvider> implements IBaSyxConnector {
	
	private static Logger logger = LoggerFactory.getLogger(IBasyxConnectorFacade.class);
	
	JSONProvider<T> provider;
	
	public IBasyxConnectorFacade(JSONProvider<T> p) {
		provider = p;
	}

	/**
	 * Calls JSONProvider and writes result into outputstream to simulate response
	 * message
	 */
	@Override
	public String getModelPropertyValue(String path) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		provider.processBaSysGet(path, outputStream);
		
		try {
			return outputStream.toString(StandardCharsets.UTF_8.displayName());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Should not happen...");
		}
	}

	/**
	 * Calls JSONProvider and writes result into outputstream to simulate response
	 * message
	 */
	@Override
	public String setModelPropertyValue(String path, String newValue) throws ProviderException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		provider.processBaSysSet(path, newValue, outputStream);
		
		try {
			return outputStream.toString(StandardCharsets.UTF_8.displayName());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Should not happen...");
		}
	}

	/**
	 * Calls JSONProvider and writes result into outputstream to simulate response
	 * message
	 */
	@Override
	public String createValue(String path, String newEntity) throws ProviderException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		provider.processBaSysCreate(path, newEntity, outputStream);
		
		try {
			return outputStream.toString(StandardCharsets.UTF_8.displayName());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Should not happen...");
		}	
	}

	/**
	 * Calls JSONProvider and writes result into outputstream to simulate response
	 * message
	 */
	@Override
	public String deleteValue(String path) throws ProviderException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String nullParam = "null";
		provider.processBaSysDelete(path, nullParam, outputStream);
		
		try {
			return outputStream.toString(StandardCharsets.UTF_8.displayName());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Should not happen...");
		}
	}

	/**
	 * Calls JSONProvider and writes result into outputstream to simulate response
	 * message
	 */
	@Override
	public String deleteValue(String path, String obj) throws ProviderException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		provider.processBaSysDelete(path, obj, outputStream);
		
		try {
			return outputStream.toString(StandardCharsets.UTF_8.displayName());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Should not happen...");
		}
	}

	/**
	 * Calls JSONProvider and writes result into outputstream to simulate response
	 * message
	 */
	@Override
	public String invokeOperation(String path, String jsonObject) throws ProviderException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		provider.processBaSysInvoke(path, jsonObject, outputStream);
		
		try {
			return outputStream.toString(StandardCharsets.UTF_8.displayName());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Should not happen...");
		}
	}

	@Override
	public String getEndpointRepresentation(String path) {
		return "test://" + path;
	}
}
