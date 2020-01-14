package org.eclipse.basyx.aas.registration.memory;

import java.util.HashMap;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * An implementation of the IAASRegistryService interface.
 * This registry can not store its entries permanently, because it is completely based on HashMaps.
 * 
 * @author espen
 *
 */
public class InMemoryRegistry extends MapRegistry {

	/**
	 * Default constructor based on HashMaps
	 */
	public InMemoryRegistry() {
		super(new HashMap<>());
	}

	@Override
	public void register(IIdentifier aas, SubmodelDescriptor smDescriptor) {
		AASDescriptor descriptor = descriptorMap.get(aas.getId());
		descriptor.addSubmodelDescriptor(smDescriptor);
		// TODO: Add data to remote AAS
	}

	@Override
	public void delete(IIdentifier aasId, String smIdShort) {
		AASDescriptor desc = descriptorMap.get(aasId.getId());
		desc.removeSubmodelDescriptor(smIdShort);
	}
}
