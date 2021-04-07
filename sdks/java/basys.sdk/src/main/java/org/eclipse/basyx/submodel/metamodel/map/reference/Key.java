/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.submodel.metamodel.map.reference;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.exception.MetamodelConstructionException;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyType;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * Key as defined in DAAS document <br>
 * <br>
 * A key is a reference to an element by its id.
 * 
 * @author schnicke
 *
 */
public class Key extends VABModelMap<Object> implements IKey {
	public static final String TYPE = "type";
	public static final String VALUE = "value";
	public static final String IDTYPE = "idType";

	/**
	 * 
	 * @param type
	 *            Denote which kind of entity is referenced.
	 * @param value
	 *            The key value, for example an IRDI if the idType=IRDI.
	 * @param idType
	 *            Type of the key value. In case of idType = idShort local shall be
	 *            true. In case type=GlobalReference idType shall not be IdShort.
	 */
	public Key(KeyElements type, String value, KeyType idType) {
		put(TYPE, type.toString());
		put(VALUE, value);
		put(IDTYPE, idType.toString());
	}
	
	/**
	 * Helper constructor to translate IdentifierType to KeyType. <br>
	 * In the meta model KeyType inheritcs from IdentifiertType, however Java does
	 * not support enum inheritance
	 * 
	 * @param type
	 * @param value
	 * @param idType
	 */
	public Key(KeyElements type, String value, IdentifierType idType) {
		this(type, value, KeyType.fromString(idType.toString()));
	}

	/**
	 * Private constructor enabling createAsFacade pattern
	 */
	private Key() {

	}

	/**
	 * Creates a Key object from a map
	 * 
	 * @param map
	 *            a Key object as raw map
	 * @return a Key object, that behaves like a facade for the given map
	 */
	public static Key createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}
		
		if (!isValid(map)) {
			throw new MetamodelConstructionException(Key.class, map);
		}
		
		Key ret = new Key();
		ret.setMap(map);
		return ret;
	}
	
	/**
	 * Check whether all mandatory elements for the metamodel
	 * exist in a map
	 * @return true/false
	 */
	public static boolean isValid(Map<String, Object> map) {
		return map != null &&
				map.containsKey(TYPE) &&
				map.containsKey(VALUE) &&
				map.containsKey(IDTYPE);
	}
	
	@SuppressWarnings("unchecked")
	public static boolean isKey(Object value) {
		if(!(value instanceof Map<?, ?>)) {
			return false;
		}
		
		Map<String, Object> map = (Map<String, Object>) value;
		
		if(!(map.get(VALUE) instanceof String
				&& map.get(IDTYPE) instanceof String && map.get(TYPE) instanceof String)) {
			return false;
		}
		
		try {
			// Try to convert the Strings to Enum-Types
			// If that fails an Exception is thrown
			KeyType.fromString((String) map.get(IDTYPE));
			KeyElements.fromString((String) map.get(TYPE));
		} catch (IllegalArgumentException e) {
			return false;
		}
		
		return true;
	}

	@Override
	public KeyElements getType() {
		return KeyElements.fromString((String) get(Key.TYPE));
	}

	@Override
	public String getValue() {
		return (String) get(Key.VALUE);
	}

	@Override
	public KeyType getIdType() {
		return KeyType.fromString((String) get(Key.IDTYPE));
	}

	public void setType(KeyElements type) {
		put(Key.TYPE, type.toString());
	}

	public void setValue(String value) {
		put(Key.VALUE, value);
	}

	public void setIdType(KeyType idType) {
		put(Key.IDTYPE, idType.toString());
	}

	@Override
	public String toString() {
		return "Key [getType()=" + getType() + ", getValue()=" + getValue() + ", getIdType()=" + getIdType() + "]";
	}
}
