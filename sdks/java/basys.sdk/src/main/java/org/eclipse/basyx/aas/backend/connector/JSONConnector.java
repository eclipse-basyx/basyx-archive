package org.eclipse.basyx.aas.backend.connector;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.eclipse.basyx.aas.backend.http.tools.factory.DefaultTypeFactory;
import org.eclipse.basyx.aas.backend.http.tools.factory.GSONToolsFactory;
import org.eclipse.basyx.vab.core.IModelProvider;


/**
 * Connector Class responsible for serializing parameters and de-serializing
 * results. It verifies the results, removes the message header and returns the
 * requested entity.
 * 
 * @author pschorn
 *
 */
public class JSONConnector implements IModelProvider {

	
	/**
	 * Reference to Connector backend
	 */
	protected IBaSyxConnector provider = null;
	
	
	/**
	 * Reference to serializer / deserializer
	 */
	protected GSONTools serializer = null;
	
	
	/**
	 * Handle meta protocol in JSON String
	 * */
	protected IMetaProtocolHandler metaProtocolHandler = null;
	
	
	/**
	 * Constructor
	 * 
	 * @param provider
	 */
	public JSONConnector(IBaSyxConnector provider) {
		// Store provider backend
		this.provider = provider;
		
		// Create the meta protocal handler
		this.metaProtocolHandler = new MetaprotocolHandler();
		
		// Create GSON serializer
		serializer = new GSONTools(new DefaultTypeFactory());
	}

	
	/**
	 * Constructor that accepts specific factory for serializer
	 * 
	 * @param provider
	 */
	public JSONConnector(IBaSyxConnector provider, GSONToolsFactory factory) {
		// Store provider backend
		this.provider = provider;
		
		// Create GSON serializer
		serializer = new GSONTools(factory);
	}

	

	@Override
	public Object getModelPropertyValue(String path) throws Exception {

		// Get element from server
		String message = provider.getModelPropertyValue(path);

		// De-serialize and verify
		return metaProtocolHandler.verify(message);
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {

		// Serialize value Object
		String jsonString = serializer.serialize(newValue);

		String message = provider.setModelPropertyValue(path, jsonString);

		// De-serialize and verify
		metaProtocolHandler.verify(message);
	}

	@Override
	public void createValue(String path, Object newEntity) throws Exception {

		// Serialize value Object
		String jsonString = serializer.serialize(newEntity);

		String message = provider.createValue(path, jsonString);

		// De-serialize and verify
		metaProtocolHandler.verify(message);
	}

	@Override
	public void deleteValue(String path) throws Exception {

		String message = provider.deleteValue(path);

		// De-serialize and verify
		metaProtocolHandler.verify(message);
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {

		// Serialize parameter
		String jsonString = serializer.serialize(obj);

		String message = provider.deleteValue(path, jsonString);

		// De-serialize and verify
		metaProtocolHandler.verify(message);
	}

	@Override
	public Object invokeOperation(String path, Object[] parameter) throws Exception {

		// Serialize parameter
		List<Object> params = new ArrayList<>();
		for (Object o : parameter) {
			params.add(o);
		}

		String jsonString = serializer.serialize(params);

		String message = provider.invokeOperation(path, jsonString);

		// De-serialize and verify
		return metaProtocolHandler.verify(message);
	}
}