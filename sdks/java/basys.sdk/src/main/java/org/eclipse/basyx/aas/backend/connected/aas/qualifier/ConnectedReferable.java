package org.eclipse.basyx.aas.backend.connected.aas.qualifier;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IReferable;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * "Connected" implementation of IReferable
 * 
 * @author rajashek
 *
 */
public class ConnectedReferable extends ConnectedElement implements IReferable {
	public ConnectedReferable(VABElementProxy proxy) {
		super(proxy);
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
