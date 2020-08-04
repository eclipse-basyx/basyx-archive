package org.eclipse.basyx.aas.registration.memory;

import java.util.List;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements a generic AAS registry that makes use of a given handler.
 */
public class AASRegistry implements IAASRegistryService {
	private static Logger logger = LoggerFactory.getLogger(AASRegistry.class);
	protected IRegistryHandler handler;
	
	public AASRegistry(IRegistryHandler handler) {
		this.handler = handler;
	}

	@Override
	public void register(AASDescriptor aasDescriptor) {
		IIdentifier aasIdentifier = aasDescriptor.getIdentifier();
		if (handler.contains(aasIdentifier)) {
			handler.update(aasDescriptor);
		} else {
			handler.insert(aasDescriptor);
		}
		logger.debug("Registered " + aasIdentifier.getId());
	}

	@Override
	public void delete(IIdentifier aasIdentifier) {
		String aasId = aasIdentifier.getId();
		if (!handler.contains(aasIdentifier)) {
			throw new ResourceNotFoundException(
					"Could not delete key for AAS " + aasId + " since it does not exist");
		} else {
			handler.remove(aasIdentifier);
			logger.debug("Removed " + aasId);
		}
	}

	@Override
	public AASDescriptor lookupAAS(IIdentifier aasIdentifier) {
		String aasId = aasIdentifier.getId();
		if (!handler.contains(aasIdentifier)) {
			throw new ResourceNotFoundException(
					"Could not look up descriptor for AAS " + aasId + " since it does not exist");
		}
		return handler.get(aasIdentifier);
	}

	@Override
	public List<AASDescriptor> lookupAll() {
		logger.debug("Looking up all AAS");
		return handler.getAll();
	}

	@Override
	public void register(IIdentifier aas, SubmodelDescriptor smDescriptor) {
		try {
			delete(aas, smDescriptor.getIdShort());
		} catch (ResourceNotFoundException e) {
			// Doesn't matter
		}

		AASDescriptor descriptor = handler.get(aas);
		descriptor.addSubmodelDescriptor(smDescriptor);
		handler.update(descriptor);
		logger.debug("Registered submodel " + smDescriptor.getIdShort() + " for AAS " + aas.getId());
	}

	@Override
	public void delete(IIdentifier aasId, String smIdShort) {
		AASDescriptor desc = handler.get(aasId);
		if (desc == null) {
			throw new ResourceNotFoundException(
					"Could not delete submodel descriptor for AAS " + aasId.getId() + " since the AAS does not exist");
		}

		if (desc.getSubmodelDescriptorFromIdShort(smIdShort) == null) {
			throw new ResourceNotFoundException(
					"Could not delete submodel descriptor for AAS " + aasId.getId() + " since the SM does not exist");
		}

		desc.removeSubmodelDescriptor(smIdShort);
		handler.update(desc);
		logger.debug("Deleted submodel " + smIdShort + " from AAS " + aasId.getId());
	}
}
