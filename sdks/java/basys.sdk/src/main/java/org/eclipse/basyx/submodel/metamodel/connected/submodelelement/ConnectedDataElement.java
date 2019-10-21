package org.eclipse.basyx.submodel.metamodel.connected.submodelelement;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IDataElement;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * "Connected" implementation of DataElement
 * 
 * @author rajashek
 *
 */
public class ConnectedDataElement extends ConnectedSubmodelElement implements IDataElement {
	public ConnectedDataElement(VABElementProxy proxy) {
		super(proxy);
	}
}
