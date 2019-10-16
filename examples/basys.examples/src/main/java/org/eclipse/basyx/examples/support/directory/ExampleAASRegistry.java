package org.eclipse.basyx.examples.support.directory;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.preconfigured.PreconfiguredRegistry;
import org.eclipse.basyx.submodel.metamodel.map.identifier.IdentifierType;

public class ExampleAASRegistry extends PreconfiguredRegistry {

	public ExampleAASRegistry addAASMapping(String rawUrn, String endpoint) {
		AASDescriptor aasDescriptor = new AASDescriptor(rawUrn, IdentifierType.URI, endpoint);
		ModelUrn aasUrn = new ModelUrn(rawUrn);
		register(aasUrn, aasDescriptor);

		return this;
	}



	public ExampleAASRegistry addSubmodelMapping(String rawAASUrn, String submodelid, String endpoint) {
		AASDescriptor aasDescriptor;
		ModelUrn aasUrn = new ModelUrn(rawAASUrn);
		SubmodelDescriptor smDes = new SubmodelDescriptor(submodelid, IdentifierType.URI, endpoint);

		if (descriptorMap.keySet().contains(aasUrn.getEncodedURN())) {
			aasDescriptor = descriptorMap.get(aasUrn.getEncodedURN());
		} else {
			throw new RuntimeException("AASDescriptor for " + rawAASUrn + " missing");
		}
		aasDescriptor.addSubmodelDescriptor(smDes);

		register(aasUrn, aasDescriptor);
		return this;
	}


}
