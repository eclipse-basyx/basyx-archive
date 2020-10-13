package org.eclipse.basyx.vab.coder.json.connector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.basyx.vab.coder.json.metaprotocol.IMetaProtocolHandler;
import org.eclipse.basyx.vab.coder.json.metaprotocol.MetaprotocolHandler;
import org.eclipse.basyx.vab.coder.json.serialization.DefaultTypeFactory;
import org.eclipse.basyx.vab.coder.json.serialization.GSONTools;
import org.eclipse.basyx.vab.coder.json.serialization.GSONToolsFactory;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.api.IBaSyxConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Connector Class responsible for serializing parameters and de-serializing
 * results. It verifies the results, removes the message header and returns the
 * requested entity.
 * 
 * @author pschorn
 *
 */
public class JSONConnector implements IModelProvider {

	private static final Logger LOGGER_DEFAULT = LoggerFactory.getLogger(JSONConnector.class);
	private static final Logger LOGGER_COMMUNICATION = LoggerFactory.getLogger(LOGGER_DEFAULT.getName() + ".MALFORMED");
	
	
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
	public Object getModelPropertyValue(String path) throws ProviderException {
		VABPathTools.checkPathForNull(path);

		// Get element from server
		String message = provider.getModelPropertyValue(path);

		// De-serialize and verify
		try {
			return metaProtocolHandler.deserialize(message);
		} catch (ProviderException e) {
			throw e;
		} catch (RuntimeException e) {
			String messageCorrelation = UUID.randomUUID().toString();
			String msg = "Failed to deserialize request for '" + provider.getEndpointRepresentation(path) + "' (" + messageCorrelation + ")";
			LOGGER_DEFAULT.warn(msg);
			LOGGER_COMMUNICATION.warn(msg + ": " + message);
			throw new ProviderException(msg, e);
		}
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws ProviderException {
		VABPathTools.checkPathForNull(path);

		// Serialize value Object
		String jsonString = serializer.serialize(newValue);

		String message = provider.setModelPropertyValue(path, jsonString);

		// De-serialize and verify
		metaProtocolHandler.deserialize(message);
	}

	@Override
	public void createValue(String path, Object newEntity) throws ProviderException {
		VABPathTools.checkPathForNull(path);
		
		// Serialize value Object
		String jsonString = serializer.serialize(newEntity);

		String message = provider.createValue(path, jsonString);

		// De-serialize and verify
		metaProtocolHandler.deserialize(message);
	}

	@Override
	public void deleteValue(String path) throws ProviderException {
		VABPathTools.checkPathForNull(path);

		String message = provider.deleteValue(path);

		// De-serialize and verify
		metaProtocolHandler.deserialize(message);
	}

	@Override
	public void deleteValue(String path, Object obj) throws ProviderException {
		VABPathTools.checkPathForNull(path);

		// Serialize parameter
		String jsonString = serializer.serialize(obj);

		String message = provider.deleteValue(path, jsonString);

		// De-serialize and verify
		metaProtocolHandler.deserialize(message);
	}

	@Override
	public Object invokeOperation(String path, Object... parameter) throws ProviderException {
		VABPathTools.checkPathForNull(path);

		// Serialize parameter
		List<Object> params = new ArrayList<>();
		for (Object o : parameter) {
			params.add(o);
		}

		String jsonString = serializer.serialize(params);

		String message = provider.invokeOperation(path, jsonString);

		// De-serialize and verify
		return metaProtocolHandler.deserialize(message);
	}
}