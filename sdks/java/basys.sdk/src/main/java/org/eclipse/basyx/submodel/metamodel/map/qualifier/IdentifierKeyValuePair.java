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

/**
 * An IdentifierKeyValuePair describes a generic identifier as key-value pair.
 * As described in DAAS Document V3
 * 
 * @author haque
 *
 */
public class IdentifierKeyValuePair extends HasSemantics {
	public static final String KEY = "key";
	public static final String VALUE = "value";
	public static final String EXTERNALSUBJECTID = "externalSubjectId";
	
	private IdentifierKeyValuePair() {}
	
	/**
	 * Constructor with mandatory attribute
	 * @param key
	 */
	public IdentifierKeyValuePair(String key) {
		setKey(key);
	}
	
	/**
	 * Creates a IdentifierKeyValuePair object from a map
	 * 
	 * @param obj
	 *            a IdentifierKeyValuePair object as raw map
	 * @return a IdentifierKeyValuePair object, that behaves like a facade for the given map
	 */
	public static IdentifierKeyValuePair createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}
		
		if (!isValid(map)) {
			throw new MetamodelConstructionException(IdentifierKeyValuePair.class, map);
		}

		IdentifierKeyValuePair ret = new IdentifierKeyValuePair();
		ret.setMap(map);
		return ret;
	}
	
	/**
	 * Check whether all mandatory elements for the metamodel
	 * exist in a map
	 * @return true/false
	 */
	public static boolean isValid(Map<String, Object> map) {
		return map != null && createAsFacadeNonStrict(map).getKey() != null;
	}
	
	/**
	 * Creates a IdentifierKeyValuePair object from a map without validation
	 * 
	 * @param obj
	 *            a IdentifierKeyValuePair object as raw map
	 * @return a IdentifierKeyValuePair object, that behaves like a facade for the given map
	 */
	private static IdentifierKeyValuePair createAsFacadeNonStrict(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		IdentifierKeyValuePair ret = new IdentifierKeyValuePair();
		ret.setMap(map);
		return ret;
	}
	
	/**
	 * sets key of the identifier
	 * @param key
	 */
	public void setKey(String key) {
		put(KEY, key);
	}
	
	/**
	 * gets key of the identifier
	 * @return {@link String}
	 */
	public String getKey() {
		return (String) get(KEY);
	}
	
	/**
	 * sets the value of the identifier with the corresponding key.
	 * @param value
	 */
	public void setValue(String value) {
		put(VALUE, value);
	}
	
	/**
	 * gets the value of the identifier with the corresponding key.
	 * @return
	 */
	public String getValue() {
		return (String) get(VALUE);
	}
	
	/**
	 * sets the (external) subject the key belongs to or has meaning to.
	 * @param ref
	 */
	public void setExternalSubjectId(Reference ref) {
		put(EXTERNALSUBJECTID, ref);
	}
	
	/**
	 * gets the (external) subject the key belongs to or has meaning to.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IReference getExternalSubjectId() {
		return Reference.createAsFacade((Map<String, Object>) get(EXTERNALSUBJECTID));
	}
}
