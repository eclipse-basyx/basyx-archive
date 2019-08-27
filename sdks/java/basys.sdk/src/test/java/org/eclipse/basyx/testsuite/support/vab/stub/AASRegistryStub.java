package org.eclipse.basyx.testsuite.support.vab.stub;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.api.registry.IAASRegistryService;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.descriptor.AASDescriptor;

public class AASRegistryStub implements IAASRegistryService {
	protected Map<String, AASDescriptor> descriptorMap = new HashMap<>();

	@Override
	public IAASRegistryService addAASMapping(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

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
