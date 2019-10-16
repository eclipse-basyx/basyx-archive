package org.eclipse.basyx.aas.registration.httpproxy;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.vab.directory.http.VABHTTPDirectoryProxy;



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
}

