package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement;

import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElement;
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
	
	@Override
	protected KeyElements getKeyElement() {
		return KeyElements.DATAELEMENT;
	}
	
	@Override
	public Object getValue() {
		throw new UnsupportedOperationException("getValue is only possible in specific Element");
	}
}
