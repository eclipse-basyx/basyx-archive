package org.eclipse.basyx.aas.backend.connected.facades;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasSemantics;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.backend.connected.aas.qualifier.ConnectedHasSemantics;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Facade providing access to a map containing the ConnectedHasSemanticsFacade structure
 * 
 * @author rajashek
 *
 */
public class ConnectedHasSemanticsFacade extends ConnectedElement implements IHasSemantics {

	public ConnectedHasSemanticsFacade(VABElementProxy proxy) {
		super(proxy);

	}

	@Override
	public IReference getSemanticId() {
		return new ConnectedHasSemantics( getProxy()).getSemanticId();
	}

	@Override
	public void setSemanticID(IReference ref) {
		new ConnectedHasSemantics( getProxy()).setSemanticID(ref);

	}
}
