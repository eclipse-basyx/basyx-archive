/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
* 
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/

* 
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/

package org.eclipse.basyx.submodel.metamodel.map.qualifier;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.exception.MetamodelConstructionException;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueTypeHelper;

/**
 * Single extension of an element
 * As described in the DAAS document V3
 * 
 * @author haque
 *
 */
public class Extension extends HasSemantics {
	public static final String NAME = "name";
	public static final String VALUETYPE = "valueType";
	public static final String VALUE = "value";
	public static final String REFERSTO = "refersTo";
	
	private Extension() {
		setValueType(ValueType.String);
	}
	
	/**
	 * Constructor with mandatory attribute
	 * @param name
	 */
	public Extension(String name) {
		this();
		setName(name);
	}
	
	/**
	 * Creates a Extension object from a map
	 * 
	 * @param map
	 *            a Extension object as raw map
	 * @return a Extension object, that behaves like a facade for the given map
	 */
	public static Extension createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}
		
		if (!isValid(map)) {
			throw new MetamodelConstructionException(Extension.class, map);
		}

		Extension ret = new Extension();
		ret.setMap(map);
		return ret;
	}
	
	/**
	 * Check whether all mandatory elements for the metamodel
	 * exist in a map
	 * @return true/false
	 */
	public static boolean isValid(Map<String, Object> map) {
		return map != null && createAsFacadeNonStrict(map).getName() != null;
	}
	
	/**
	 * Creates a Extension object from a map without validation
	 * 
	 * @param obj
	 *            a Extension object as raw map
	 * @return a Extension object, that behaves like a facade for the given map
	 */
	private static Extension createAsFacadeNonStrict(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		Extension ret = new Extension();
		ret.setMap(map);
		return ret;
	}
	
	/**
	 * sets name.
	 * An extension of the element.
	 * Constraint AASd-077: The name of an extension within HasExtensions needs to be unique.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		put(NAME, name);
	}
	
	/**
	 * gets name.
	 * An extension of the element.
	 * Constraint AASd-077: The name of an extension within HasExtensions needs to be unique.
	 * @return
	 */
	public String getName() {
		return (String) get(NAME);
	}
	
	/**
	 * sets type of the value of the extension.
	 * Default: xsd:string
	 * 
	 * @param type
	 */
	public void setValueType(ValueType type) {
		if(type == null) {
			throw new RuntimeException("Can not set null as valueType");
		}
		put(VALUETYPE, type.toString());
	}
	
	/**
	 * gets type of the value of the extension.
	 * Default: xsd:string
	 * 
	 * @return
	 */
	public ValueType getValueType() {
		String valueType = (String) get(VALUETYPE);
		if (valueType == null) {
			return null;
		} else {
			return ValueTypeHelper.fromName(valueType);
		}
	}
	
	/**
	 * sets value of the extension
	 * 
	 * @param value
	 */
	public void setValue(Object value) {
		put(VALUE, value);
	}
	
	/**
	 * gets value of the extension
	 * @return
	 */
	public Object getValue() {
		Object value = get(VALUE);
		if (value instanceof String) {
			return ValueTypeHelper.getJavaObject(value, getValueType());
		} else {
			return value;
		}
	}
	
	/**
	 * sets reference to an element the extension refers to.
	 * @param ref
	 */
	public void setRefersTo(IReference ref) {
		put(REFERSTO, ref);
	}
	
	/**
	 * gets reference to an element the extension refers to.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IReference getRefersTo() {
		return Reference.createAsFacade((Map<String, Object>) get(REFERSTO));
	}
}
