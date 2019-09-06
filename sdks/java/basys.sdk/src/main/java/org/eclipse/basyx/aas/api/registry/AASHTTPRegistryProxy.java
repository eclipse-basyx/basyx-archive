package org.eclipse.basyx.aas.api.registry;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.identifier.IdentifierType;
import org.eclipse.basyx.vab.core.directory.VABHTTPDirectoryProxy;



/**
 * Local proxy class that hides HTTP calls to BaSys registry
 * 
 * @author kuhn
 *
 */
public class AASHTTPRegistryProxy extends VABHTTPDirectoryProxy implements IAASRegistryService {
	/**
	 * Constructor - instantiate a VABDirectoryProxy with the same endpoint
	 */
	public AASHTTPRegistryProxy(String directoryUrl) {
		super(directoryUrl);
	}

	/**
	 * Register AAS descriptor in registry, delete old registration
	 */
	@Override
	public void register(ModelUrn aasID, AASDescriptor deviceAASDescriptor) {
		delete(aasID);
		registerOnly(deviceAASDescriptor);
	}

	/**
	 * Register AAS descriptor in registry
	 */
	@Override
	public void registerOnly(AASDescriptor deviceAASDescriptor) {
		// Add a mapping from the AAS id to the serialized descriptor
		System.out.println("Registering at path " + deviceAASDescriptor.getId());
		proxy.createValue("", deviceAASDescriptor);
	}
	
	/**
	 * Delete AAS descriptor from registry
	 */
	@Override
	public void delete(ModelUrn aasID) {
		try {
			this.removeMapping(URLEncoder.encode(aasID.getURN(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Lookup device AAS
	 */
	@Override @SuppressWarnings("unchecked")
	public AASDescriptor lookupAAS(ModelUrn aasID) {
		Object result = proxy.getModelPropertyValue(aasID.getEncodedURN());
		if (result instanceof Map<?, ?>) {
			return new AASDescriptor((Map<String, Object>) result);
		} else {
			return null;
		}
	}

	/**
	 * Add an AAS mapping to a directory
	 * 
	 * This function creates an AAS descriptor and registers it in the directory
	 */
	@Override
	public IAASRegistryService addAASMapping(String key, String value) {
		// Create AAS descriptor and set ID, ID type, and endpoint
		AASDescriptor aasDescriptor = new AASDescriptor(key, IdentifierType.URI, value);

		// Push the descriptor to the server
		proxy.createValue(key, aasDescriptor);

		return this;
	}
}

