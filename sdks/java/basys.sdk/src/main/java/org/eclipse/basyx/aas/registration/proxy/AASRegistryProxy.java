package org.eclipse.basyx.aas.registration.proxy;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.vab.coder.json.connector.JSONConnector;
import org.eclipse.basyx.vab.directory.proxy.VABDirectoryProxy;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnector;



/**
 * Local proxy class that hides HTTP calls to BaSys registry
 * 
 * @author kuhn
 *
 */
public class AASRegistryProxy extends VABDirectoryProxy implements IAASRegistryService {

	/**
	 * Constructor for an AAS registry proxy based on a HTTP connection
	 * 
	 * @param registryUrl
	 *            The endpoint of the registry with a HTTP-REST interface
	 */
	public AASRegistryProxy(String registryUrl) {
		this(new VABElementProxy("/api/v1/registry", new JSONConnector(new HTTPConnector(registryUrl))));
	}

	/**
	 * Constructor for an AAS registry proxy based on its model provider
	 * 
	 * @param provider
	 *            A model provider for the actual registry
	 */
	public AASRegistryProxy(IModelProvider provider) {
		super(provider);
	}

	/**
	 * Register AAS descriptor in registry, delete old registration
	 */
	@Override
	public void register(AASDescriptor deviceAASDescriptor) {
		delete(deviceAASDescriptor.getIdentifier());
		registerOnly(deviceAASDescriptor);
	}

	/**
	 * Register AAS descriptor in registry
	 */
	@Override
	public void registerOnly(AASDescriptor deviceAASDescriptor) {
		// Add a mapping from the AAS id to the serialized descriptor
		try {
			provider.createValue("", deviceAASDescriptor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Delete AAS descriptor from registry
	 */
	@Override
	public void delete(IIdentifier aasIdentifier) {
		try {
			this.removeMapping(URLEncoder.encode(aasIdentifier.getId(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Lookup device AAS
	 */
	@Override @SuppressWarnings("unchecked")
	public AASDescriptor lookupAAS(IIdentifier aasIdentifier) {
		Object result = null;
		try {
			result = provider.getModelPropertyValue(URLEncoder.encode(aasIdentifier.getId(), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result instanceof Map<?, ?>) {
			return new AASDescriptor((Map<String, Object>) result);
		} else {
			return null;
		}
	}
}

