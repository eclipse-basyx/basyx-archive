/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement;

import org.eclipse.basyx.submodel.metamodel.api.IElement;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.vab.exception.provider.ProviderException;

/**
 * Interface for IElement properties
 * 
 * @author kuhn
 *
 */
public interface IProperty extends IElement, IDataElement {
	/**
	 * Will be replaced by {@link #getValue() }<br>
	 * Get property value
	 * 
	 * @return Property value
	 * @throws Exception
	 */
	@Deprecated
	public Object get() throws Exception;

	/**
	 * Will be replaced by {@link #setValue(Object)} <br>
	 * Set property value
	 * 
	 * @throws ProviderException
	 */
	@Deprecated
	public void set(Object newValue) throws ProviderException;

	/**
	 * Gets the data type of the value
	 * 
	 * @return
	 */
	public PropertyValueTypeDef getValueType();

	/**
	 * Gets the reference to the global unique id of a coded value.
	 * 
	 * @return
	 */
	public IReference getValueId();
}
