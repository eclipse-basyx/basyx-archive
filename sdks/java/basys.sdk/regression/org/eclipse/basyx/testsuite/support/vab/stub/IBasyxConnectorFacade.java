package org.eclipse.basyx.testsuite.support.vab.stub;

import java.io.FileNotFoundException;

import org.eclipse.basyx.aas.backend.connector.IBaSyxConnector;
import org.eclipse.basyx.vab.backend.server.utils.JSONProvider;
import org.eclipse.basyx.vab.core.IModelProvider;

/**
 * This class is required for Meta-protocol integration testing. It makes JSONProvider directly usable for the JSONConnector.
 * @author pschorn
 *
 * @param <T>
 */
public class IBasyxConnectorFacade<T extends IModelProvider> implements IBaSyxConnector {
	
	JSONProvider<T> provider;
	
	public IBasyxConnectorFacade(JSONProvider<T> p) {
		provider = p;
	}

	@Override
	public String getModelPropertyValue(String path) {
		try {
			PrintWriterStub outputstream = new PrintWriterStub("test.txt", "ignore");
		
			provider.processBaSysGet(path,  outputstream); // writes result into outputstream
			
			return outputstream.getResult();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;		
	}

	@Override
	public String setModelPropertyValue(String path, String newValue) throws Exception {
		try {
			PrintWriterStub outputstream = new PrintWriterStub("test.txt", "ignore");
		
			provider.processBaSysSet(path,  newValue, outputstream); // writes result into outputstream
			
			return outputstream.getResult();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;		
	}

	@Override
	public String createValue(String path, String newEntity) throws Exception {
		try {
			PrintWriterStub outputstream = new PrintWriterStub("test.txt", "ignore");
		
			provider.processBaSysCreate(path,  newEntity, outputstream); // writes result into outputstream
			
			return outputstream.getResult();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;		
	}

	@Override
	public String deleteValue(String path) throws Exception {
		try {
			PrintWriterStub outputstream = new PrintWriterStub("test.txt", "ignore");
		
			String nullParam = "{\"basystype\":\"null\"}";
			provider.processBaSysDelete(path,  nullParam, outputstream); // need serialized null here; writes result into outputstream
			
			return outputstream.getResult();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String deleteValue(String path, String obj) throws Exception {
		try {
			PrintWriterStub outputstream = new PrintWriterStub("test.txt", "ignore");
		
			provider.processBaSysDelete(path,  obj, outputstream); // writes result into outputstream
			
			return outputstream.getResult();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String invokeOperation(String path, String jsonObject) throws Exception {
		try {
			PrintWriterStub outputstream = new PrintWriterStub("test.txt", "ignore");
		
			provider.processBaSysInvoke(path,  jsonObject, outputstream); // writes result into outputstream
			
			return outputstream.getResult();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
