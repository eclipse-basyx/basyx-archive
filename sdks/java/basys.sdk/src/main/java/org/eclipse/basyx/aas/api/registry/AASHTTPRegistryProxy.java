package org.eclipse.basyx.aas.api.registry;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.backend.connector.MetaprotocolHandler;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.IdentifierType;
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
		this.addMapping(deviceAASDescriptor.getId(), serializer.serialize(deviceAASDescriptor));
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

		// Lookup AAS from AAS directory, get AAS descriptor
		String jsonResponse = this.lookup(aasID.getEncodedURN());
		
		// Deserialize AAS descriptor
		AASDescriptor aasDescriptor = null;
		try {
			aasDescriptor = new AASDescriptor((Map<String, Object>) (new MetaprotocolHandler().verify(jsonResponse)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Return AAS descriptor
		return aasDescriptor;
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

		// Push AAS descriptor to server
		this.addMapping(key, serializer.serialize(aasDescriptor));

		return this;
	}
}

