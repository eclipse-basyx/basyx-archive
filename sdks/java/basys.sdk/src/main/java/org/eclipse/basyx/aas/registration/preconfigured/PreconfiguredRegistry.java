package org.eclipse.basyx.aas.registration.preconfigured;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * Implements a preconfigured local registry
 */
public class PreconfiguredRegistry implements IAASRegistryService {
	protected Map<String, AASDescriptor> descriptorMap = new HashMap<>();

	@Override
	public void register(AASDescriptor aasDescriptor) {
		String aasId = aasDescriptor.getIdentifier().getId();
		if (descriptorMap.containsKey(aasId)) {
			descriptorMap.remove(aasId);
		}

		descriptorMap.put(aasId, aasDescriptor);
	}

	@Override
	public void registerOnly(AASDescriptor aasDescriptor) {
		String aasId = aasDescriptor.getIdentifier().getId();
		descriptorMap.put(aasId, aasDescriptor);
	}

	@Override
	public void delete(IIdentifier aasIdentifier) {
		descriptorMap.remove(aasIdentifier.getId());

	}

	@Override
	public AASDescriptor lookupAAS(IIdentifier aasIdentifier) {
		return descriptorMap.get(aasIdentifier.getId());
	}

}
