package org.eclipse.basyx.aas.registration.proxy;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.restapi.DirectoryModelProvider;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.vab.coder.json.connector.JSONConnector;
import org.eclipse.basyx.vab.directory.proxy.VABDirectoryProxy;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Local proxy class that hides HTTP calls to BaSys registry
 * 
 * @author kuhn
 *
 */
public class AASRegistryProxy extends VABDirectoryProxy implements IAASRegistryService {
	
	private static Logger logger = LoggerFactory.getLogger(AASRegistryProxy.class);

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
	public AASRegistryProxy(IModelProvider provider) throws ProviderException {
		super(provider);
	}

	/**
	 * Register AAS descriptor in registry, delete old registration
	 */
	@Override
	public void register(AASDescriptor deviceAASDescriptor) throws ProviderException {
		try {
			delete(deviceAASDescriptor.getIdentifier());
		} catch (ResourceNotFoundException e) {
			logger.info("register: Tried to delete AAS that does not exist.");
		}

		registerOnly(deviceAASDescriptor);
	}

	/**
	 * Register AAS descriptor in registry
	 */
	@Override
	public void registerOnly(AASDescriptor deviceAASDescriptor) throws ProviderException {
		// Add a mapping from the AAS id to the serialized descriptor
		try {
			provider.createValue("", deviceAASDescriptor);
		} catch (Exception e) {
			if (e instanceof ProviderException) {
				throw (ProviderException) e;
			} else {
				throw new ProviderException(e);
			}
		}
	}
	
	/**
	 * Delete AAS descriptor from registry
	 */
	@Override
	public void delete(IIdentifier aasIdentifier) throws ProviderException {
		try {
			this.removeMapping(URLEncoder.encode(aasIdentifier.getId(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("Could not encode URL. This should not happen");
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Lookup device AAS
	 */
	@Override @SuppressWarnings("unchecked")
	public AASDescriptor lookupAAS(IIdentifier aasIdentifier) throws ProviderException {
		try {
			Object result = provider.getModelPropertyValue(URLEncoder.encode(aasIdentifier.getId(), "UTF-8"));
			return new AASDescriptor((Map<String, Object>) result);
		} catch (Exception e) {
			if (e instanceof ProviderException) {
				throw (ProviderException) e;
			} else {
				throw new ProviderException(e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AASDescriptor> lookupAll() throws ProviderException {
		try {
			Object result = provider.getModelPropertyValue("");
			Collection<?> descriptors = (Collection<?>) result;
			return descriptors.stream().map(x -> new AASDescriptor((Map<String, Object>) x)).collect(Collectors.toList());
		} catch (Exception e) {
			if (e instanceof ProviderException) {
				throw (ProviderException) e;
			} else {
				throw new ProviderException(e);
			}
		}
	}

	@Override
	public void register(IIdentifier aas, SubmodelDescriptor smDescriptor) throws ProviderException {
		try {
			delete(aas, smDescriptor.getIdShort());
		} catch (ResourceNotFoundException e) {
			logger.info("register: Tried to delete submodel that does not exist.");
		}

		try {
			provider.createValue(buildSubmodelPath(aas), smDescriptor);
		} catch (Exception e) {
			if (e instanceof ProviderException) {
				throw (ProviderException) e;
			} else {
				throw new ProviderException(e);
			}
		}
	}

	@Override
	public void delete(IIdentifier aasId, String smIdShort) throws ProviderException {
		try {
			provider.deleteValue(VABPathTools.concatenatePaths(buildSubmodelPath(aasId), URLEncoder.encode(smIdShort, "UTF-8")));
		} catch (Exception e) {
			if (e instanceof ProviderException) {
				throw (ProviderException) e;
			} else {
				throw new ProviderException(e);
			}
		}
	}

	private String buildSubmodelPath(IIdentifier aas) throws ProviderException {
		try {
			// Encode id to handle usage of reserved symbols, e.g. /
			String encodedAASId = URLEncoder.encode(aas.getId(), "UTF-8");
			return VABPathTools.concatenatePaths(encodedAASId, DirectoryModelProvider.SUBMODELS);
		} catch (UnsupportedEncodingException e) {
			logger.error("Could not encode URL. This should not happen");
			throw new RuntimeException(e);
		}

	}
}

