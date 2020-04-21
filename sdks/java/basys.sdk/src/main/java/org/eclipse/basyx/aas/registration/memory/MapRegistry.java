package org.eclipse.basyx.aas.registration.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.vab.exception.provider.ResourceAlreadyExistsException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements a preconfigured registry based on the Map interface
 */
public class MapRegistry implements IAASRegistryService {
	protected Map<String, AASDescriptor> descriptorMap;

	Logger logger = LoggerFactory.getLogger(MapRegistry.class);

	/**
	 * Constructor that takes a reference to a map as a base for the registry
	 * entries
	 */
	public MapRegistry(Map<String, AASDescriptor> rootMap) {
		descriptorMap = rootMap;
	}

	@Override
	public void register(AASDescriptor aasDescriptor) {
		String aasId = aasDescriptor.getIdentifier().getId();
		if (descriptorMap.containsKey(aasId)) {
			descriptorMap.remove(aasId);
		}

		descriptorMap.put(aasId, aasDescriptor);
		logger.debug("Registered " + aasId);
	}

	@Override
	public void registerOnly(AASDescriptor aasDescriptor) {
		String aasId = aasDescriptor.getIdentifier().getId();
		if (descriptorMap.containsKey(aasId)) {
			throw new ResourceAlreadyExistsException("Could not create new key for AAS " + aasId + " since it already exists");
		} else {
			descriptorMap.put(aasId, aasDescriptor);
			logger.debug("Registered " + aasId);
		}
	}

	@Override
	public void delete(IIdentifier aasIdentifier) {
		String aasId = aasIdentifier.getId();
		if (!descriptorMap.containsKey(aasId)) {
			throw new ResourceNotFoundException("Could not delete key for AAS " + aasId + " since it does not exist");
		} else {
			descriptorMap.remove(aasId);
			logger.debug("Removed " + aasId);
		}
	}

	@Override
	public AASDescriptor lookupAAS(IIdentifier aasIdentifier) {
		String aasId = aasIdentifier.getId();
		if (!descriptorMap.containsKey(aasId)) {
			throw new ResourceNotFoundException("Could not look up descriptor for AAS " + aasId + " since it does not exist");
		}
		return descriptorMap.get(aasIdentifier.getId());
	}

	@Override
	public List<AASDescriptor> lookupAll() {
		logger.debug("Looking up all AAS");
		return new ArrayList<>(descriptorMap.values());
	}

	@Override
	public void register(IIdentifier aas, SubmodelDescriptor smDescriptor) {
		AASDescriptor descriptor = descriptorMap.get(aas.getId());
		descriptor.addSubmodelDescriptor(smDescriptor);
		// Do not assume that the returned descriptor is referenced in the base map
		descriptorMap.put(aas.getId(), descriptor);
		logger.debug("Registered submodel " + smDescriptor.getIdShort() + " for AAS " + aas.getId());

		// TODO: Add data to remote AAS
	}

	@Override
	public void delete(IIdentifier aasId, String smIdShort) {
		AASDescriptor desc = descriptorMap.get(aasId.getId());
		if (desc == null) {
			throw new ResourceNotFoundException("Could not delete submodel descriptor for AAS " + aasId + " since the AAS does not exist");
		}

		if (desc.getSubmodelDescriptorFromIdShort(smIdShort) == null) {
			throw new ResourceNotFoundException("Could not delete submodel descriptor for AAS " + aasId + " since the SM does not exist");
		}

		desc.removeSubmodelDescriptor(smIdShort);
		// Do not assume that the returned descriptor is referenced in the base map
		descriptorMap.put(aasId.getId(), desc);

		logger.debug("Deleted submodel " + smIdShort + " from AAS " + aasId.getId());

	}

}
