package org.eclipse.basyx.aas.backend.connected.aas.qualifier.qualifiable;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IConstraint
 * @author rajashek
 *
 */
public class ConnectedConstraint extends ConnectedElement implements IConstraint{
	public ConnectedConstraint(String path, VABElementProxy proxy) {
		super(path, proxy);		
	}
}
