package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.event;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.event.IBasicEvent;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.event.BasicEvent;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * "Connected" implementation of IBasicEvent
 * @author conradi
 *
 */
public class ConnectedBasicEvent extends ConnectedSubmodelElement implements IBasicEvent {

	public ConnectedBasicEvent(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	@SuppressWarnings("unchecked")
	public IReference getObserved() {
		return Reference.createAsFacade((Map<String, Object>) getElem().getPath(BasicEvent.OBSERVED));
	}
	
	@Override
	protected KeyElements getKeyElement() {
		return KeyElements.BASICEVENT;
	}

	@Override
	public IReference getValue() {
		return getObserved();
	}
}
