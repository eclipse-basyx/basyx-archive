package org.eclipse.basyx.aas.backend.connected.facades;

import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IQualifiable;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.backend.connected.aas.qualifier.qualifiable.ConnectedQualifiable;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * Facade providing access to a map containing the ConnectedQualifiableFacade structure
 * @author rajashek
 *
 */
public class ConnectedQualifiableFacade extends ConnectedElement implements IQualifiable {
	public ConnectedQualifiableFacade(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public Set<IConstraint> getQualifier() {
		return new ConnectedQualifiable( getProxy()).getQualifier();
	}
}
