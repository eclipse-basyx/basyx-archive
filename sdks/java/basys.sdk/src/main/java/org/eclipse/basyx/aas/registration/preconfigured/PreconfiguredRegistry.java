package org.eclipse.basyx.aas.registration.preconfigured;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;

/**
 * Implements a preconfigured local registry
 */
public class PreconfiguredRegistry implements IAASRegistryService {
	protected Map<String, AASDescriptor> descriptorMap = new HashMap<>();

	@Override
	public void register(ModelUrn aasID, AASDescriptor deviceAASDescriptor) {
		if (descriptorMap.containsKey(aasID.getEncodedURN())) {
			descriptorMap.remove(aasID.getEncodedURN());
		}

		descriptorMap.put(aasID.getEncodedURN(), deviceAASDescriptor);
	}

	@Override
	public void registerOnly(AASDescriptor deviceAASDescriptor) {
		descriptorMap.put(deviceAASDescriptor.getId(), deviceAASDescriptor);
	}

	@Override
	public void delete(ModelUrn aasID) {
		descriptorMap.remove(aasID.getEncodedURN());

	}

	@Override
	public AASDescriptor lookupAAS(ModelUrn aasID) {

		return descriptorMap.get(aasID.getEncodedURN());
	}

}
