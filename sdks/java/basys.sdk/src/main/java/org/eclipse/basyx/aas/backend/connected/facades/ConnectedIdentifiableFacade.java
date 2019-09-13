package org.eclipse.basyx.aas.backend.connected.facades;

import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IIdentifiable;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.backend.connected.aas.identifier.ConnectedAdministrativeInformation;
import org.eclipse.basyx.aas.backend.connected.aas.identifier.ConnectedIdentifier;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Facade providing access to a map containing the ConnectedIdentifiableFacade
 * structure
 * 
 * @author rajashek
 *
 */
public class ConnectedIdentifiableFacade extends ConnectedElement implements IIdentifiable {

	public ConnectedIdentifiableFacade(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public IAdministrativeInformation getAdministration() {
		return new ConnectedAdministrativeInformation(getProxy().getDeepProxy(Identifiable.ADMINISTRATION));
	}

	@Override
	public IIdentifier getIdentification() {
		return new ConnectedIdentifier(getProxy().getDeepProxy(Identifiable.IDENTIFICATION));
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
