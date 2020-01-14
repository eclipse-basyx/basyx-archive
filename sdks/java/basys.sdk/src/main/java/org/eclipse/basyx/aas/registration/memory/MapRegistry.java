package org.eclipse.basyx.aas.registration.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * Implements a preconfigured registry based on the Map interface
 */
public class MapRegistry implements IAASRegistryService {
	protected Map<String, AASDescriptor> descriptorMap;

	/**
	 * Constructor that takes a reference to a map as a base for the registry entries
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

	@Override
	public void register(IIdentifier aas, SubmodelDescriptor smDescriptor) {
		AASDescriptor descriptor = descriptorMap.get(aas.getId());
		descriptor.addSubmodelDescriptor(smDescriptor);
		// Do not assume that the returned descriptor is referenced in the base map
		descriptorMap.put(aas.getId(), descriptor);
		// TODO: Add data to remote AAS
	}

	@Override
	public void delete(IIdentifier aasId, String smIdShort) {
		AASDescriptor desc = descriptorMap.get(aasId.getId());
		desc.removeSubmodelDescriptor(smIdShort);
		// Do not assume that the returned descriptor is referenced in the base map
		descriptorMap.put(aasId.getId(), desc);
	}

}
