package org.eclipse.basyx.examples.support.directory;

import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.identifier.IdentifierType;
import org.eclipse.basyx.testsuite.support.vab.stub.AASRegistryStub;

public class ExampleAASRegistry extends AASRegistryStub {

	public ExampleAASRegistry addAASMapping(String rawUrn, String endpoint) {
		AASDescriptor aasDescriptor = new AASDescriptor(rawUrn, IdentifierType.URI, endpoint);
		ModelUrn aasUrn = new ModelUrn(rawUrn);
		register(aasUrn, aasDescriptor);

		return this;
	}



	public ExampleAASRegistry addSubmodelMapping(String rawurn, String submodelid, String endpoint) {
		AASDescriptor aasDescriptor;
		ModelUrn aasUrn = new ModelUrn(rawurn);
		SubmodelDescriptor smDes = new SubmodelDescriptor(submodelid, IdentifierType.URI, endpoint);

		if (descriptorMap.keySet().contains(aasUrn.getEncodedURN())) {
			aasDescriptor = descriptorMap.get(aasUrn.getEncodedURN());
		} else {
			aasDescriptor = new AASDescriptor(rawurn, IdentifierType.URI, endpoint);
		}
		aasDescriptor.addSubmodelDescriptor(smDes);

		register(aasUrn, aasDescriptor);
		return this;
	}


}
