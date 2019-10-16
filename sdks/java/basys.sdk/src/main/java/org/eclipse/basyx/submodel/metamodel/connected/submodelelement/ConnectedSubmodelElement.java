package org.eclipse.basyx.submodel.metamodel.connected.submodelelement;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedElement;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
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
