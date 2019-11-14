package org.eclipse.basyx.aas.registration.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * Implements a preconfigured local registry
 */
public class InMemoryRegistry implements IAASRegistryService {
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

	@Override
	public List<AASDescriptor> lookupAll() {
		return new ArrayList<>(descriptorMap.values());
	}

}
