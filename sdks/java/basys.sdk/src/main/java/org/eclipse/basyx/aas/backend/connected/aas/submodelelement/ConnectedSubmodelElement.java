package org.eclipse.basyx.aas.backend.connected.aas.submodelelement;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.ISubmodelElement;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of SubmodelElement
 * @author rajashek
 *
 */
public abstract class ConnectedSubmodelElement extends ConnectedElement implements ISubmodelElement {
	public ConnectedSubmodelElement(VABElementProxy proxy) {
		super(proxy);		
	}
}
