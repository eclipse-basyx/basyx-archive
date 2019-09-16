package org.eclipse.basyx.aas.backend.connected.aas.qualifier;

import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IIdentifiable;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * "Connected" implementation of IIdentifiable
 * 
 * @author rajashek
 *
 */
public class ConnectedIdentifiable extends ConnectedElement implements IIdentifiable {

	public ConnectedIdentifiable(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public IAdministrativeInformation getAdministration() {
		return (IAdministrativeInformation) getProxy().getModelPropertyValue(Identifiable.ADMINISTRATION);
	}

	@Override
	public IIdentifier getIdentification() {
		return (IIdentifier) getProxy().getModelPropertyValue(Identifiable.IDENTIFICATION);
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
