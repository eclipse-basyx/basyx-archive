package org.eclipse.basyx.testsuite.regression.vab.coder.json;

import java.io.FileNotFoundException;

import org.eclipse.basyx.vab.coder.json.provider.JSONProvider;
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
 *            should be VABHashmapProvider or stub
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
		try {
			PrintWriterStub outputstream = new PrintWriterStub("test.txt", "ignore");
		
			provider.processBaSysGet(path, outputstream);
			
			return outputstream.getResult();
		} catch (FileNotFoundException e) {
			logger.error("[TEST] Exception in getModelPropertyValue", e);
		}
		return null;		
	}

	/**
	 * Calls JSONProvider and writes result into outputstream to simulate response
	 * message
	 */
	@Override
	public String setModelPropertyValue(String path, String newValue) throws Exception {
		try {
			PrintWriterStub outputstream = new PrintWriterStub("test.txt", "ignore");
		
			provider.processBaSysSet(path, newValue, outputstream);
			
			return outputstream.getResult();
		} catch (FileNotFoundException e) {
			logger.error("[TEST] Exception in setModelPropertyValue", e);
		}
		return null;		
	}

	/**
	 * Calls JSONProvider and writes result into outputstream to simulate response
	 * message
	 */
	@Override
	public String createValue(String path, String newEntity) throws Exception {
		try {
			PrintWriterStub outputstream = new PrintWriterStub("test.txt", "ignore");
		
			provider.processBaSysCreate(path, newEntity, outputstream);
			
			return outputstream.getResult();
		} catch (FileNotFoundException e) {
			logger.error("[TEST] Exception in createValue", e);
		}
		return null;		
	}

	/**
	 * Calls JSONProvider and writes result into outputstream to simulate response
	 * message
	 */
	@Override
	public String deleteValue(String path) throws Exception {
		try {
			PrintWriterStub outputstream = new PrintWriterStub("test.txt", "ignore");
		
			String nullParam = "null";
			provider.processBaSysDelete(path, nullParam, outputstream);
			
			return outputstream.getResult();
		} catch (FileNotFoundException e) {
			logger.error("[TEST] Exception in deleteValue", e);
		}
		return null;
	}

	/**
	 * Calls JSONProvider and writes result into outputstream to simulate response
	 * message
	 */
	@Override
	public String deleteValue(String path, String obj) throws Exception {
		try {
			PrintWriterStub outputstream = new PrintWriterStub("test.txt", "ignore");
		
			provider.processBaSysDelete(path, obj, outputstream);
			
			return outputstream.getResult();
		} catch (FileNotFoundException e) {
			logger.error("[TEST] Exception in deleteValue", e);
		}
		return null;
	}

	/**
	 * Calls JSONProvider and writes result into outputstream to simulate response
	 * message
	 */
	@Override
	public String invokeOperation(String path, String jsonObject) throws Exception {
		try {
			PrintWriterStub outputstream = new PrintWriterStub("test.txt", "ignore");
		
			provider.processBaSysInvoke(path, jsonObject, outputstream);
			
			return outputstream.getResult();
		} catch (FileNotFoundException e) {
			logger.error("[TEST] Exception in invokeOperation", e);
		}
		return null;
	}

	
}
