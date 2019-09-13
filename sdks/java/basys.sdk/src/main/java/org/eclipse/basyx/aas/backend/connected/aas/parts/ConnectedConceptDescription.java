package org.eclipse.basyx.aas.backend.connected.aas.parts;

import java.util.HashSet;

import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.api.metamodel.aas.parts.IConceptDescription;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasDataSpecificationFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedIdentifiableFacade;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.parts.ConceptDescription;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * "Connected" implementation of IConceptDescription
 * 
 * @author rajashek
 *
 */
public class ConnectedConceptDescription extends ConnectedElement implements IConceptDescription {

	public ConnectedConceptDescription(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return new ConnectedHasDataSpecificationFacade(getProxy()).getDataSpecificationReferences();
	}

	@Override
	public IAdministrativeInformation getAdministration() {
		return new ConnectedIdentifiableFacade(getProxy()).getAdministration();
	}

	@Override
	public IIdentifier getIdentification() {
		return new ConnectedIdentifiableFacade(getProxy()).getIdentification();
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashSet<String> getisCaseOf() {
		return (HashSet<String>) getProxy().getModelPropertyValue(ConceptDescription.ISCASEOF);
	}

	@Override
	public String getIdshort() {
		return (String) getProxy().getModelPropertyValue(Referable.IDSHORT);
	}

	@Override
	public String getCategory() {
		return (String) getProxy().getModelPropertyValue(Referable.CATEGORY);
	}

	@Override
	public String getDescription() {
		return (String) getProxy().getModelPropertyValue(Referable.DESCRIPTION);
	}

	@Override
	public IReference getParent() {
		return (IReference) getProxy().getModelPropertyValue(Referable.PARENT);
	}
}
