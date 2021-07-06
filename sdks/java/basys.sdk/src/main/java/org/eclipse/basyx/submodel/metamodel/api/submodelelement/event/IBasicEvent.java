/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
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
