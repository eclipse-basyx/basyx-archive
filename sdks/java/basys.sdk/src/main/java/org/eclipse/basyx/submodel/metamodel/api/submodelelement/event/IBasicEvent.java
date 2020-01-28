package org.eclipse.basyx.submodel.metamodel.api.submodelelement.event;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;

/**
 * A basic event. <br/>
 * To be extended according to upcoming releases of Plattform I4.0.
 * 
 * @author schnicke
 *
 */
public interface IBasicEvent extends IEvent {
	/**
	 * Gets reference to the data or other elements that are being observed
	 * 
	 * @return
	 */
	IReference getObserved();
}
