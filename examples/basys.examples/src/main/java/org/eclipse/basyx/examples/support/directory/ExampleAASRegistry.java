package org.eclipse.basyx.examples.support.directory;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

public class ExampleAASRegistry extends InMemoryRegistry {

	public ExampleAASRegistry addAASMapping(String rawUrn, String endpoint) {
		IIdentifier id = new Identifier(IdentifierType.IRI, rawUrn);
		AASDescriptor aasDescriptor = new AASDescriptor(id, endpoint);
		register(aasDescriptor);
		return this;
	}

	public ExampleAASRegistry addSubmodelMapping(String rawAASUrn, String submodelid, String endpoint) {
		AASDescriptor aasDescriptor;
		ModelUrn aasUrn = new ModelUrn(rawAASUrn);
		IIdentifier smId = new Identifier(IdentifierType.IRI, submodelid);
		SubmodelDescriptor smDes = new SubmodelDescriptor(submodelid, smId, endpoint);

		if (handler.contains(aasUrn)) {
			aasDescriptor = handler.get(aasUrn);
		} else {
			throw new RuntimeException("AASDescriptor for " + rawAASUrn + " missing");
		}
		aasDescriptor.addSubmodelDescriptor(smDes);
		register(aasDescriptor);
		return this;
	}
}
