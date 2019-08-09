package org.eclipse.basyx.aas.backend.connected.aas.qualifier;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasSemantics;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.backend.connected.aas.reference.ConnectedReference;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * "Connected" implementation of IHasSemantics
 * 
 * @author rajashek
 *
 */
public class ConnectedHasSemantics extends ConnectedElement implements IHasSemantics {
	public ConnectedHasSemantics(String path, VABElementProxy proxy) {
		super(path, proxy);
	}

	@Override
	public IReference getSemanticId() {
		return new ConnectedReference(constructPath(HasSemantics.SEMANTICID), getProxy());
	}

	@Override
	public void setSemanticID(IReference ref) {
		getProxy().updateElementValue(constructPath(HasSemantics.SEMANTICID), ref);
	}
}
