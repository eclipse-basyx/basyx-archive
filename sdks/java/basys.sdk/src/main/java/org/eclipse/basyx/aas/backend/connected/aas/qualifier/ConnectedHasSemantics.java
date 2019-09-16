package org.eclipse.basyx.aas.backend.connected.aas.qualifier;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasSemantics;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.backend.connected.aas.reference.ConnectedReference;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * "Connected" implementation of IHasSemantics
 * 
 * @author rajashek
 *
 */
public class ConnectedHasSemantics extends ConnectedElement implements IHasSemantics {
	public ConnectedHasSemantics(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public IReference getSemanticId() {
		return new ConnectedReference(getProxy().getDeepProxy(HasSemantics.SEMANTICID));
	}
}
