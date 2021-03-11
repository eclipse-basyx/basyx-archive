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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.vab.model.VABModelMap;

public class HasExtensions extends VABModelMap<Object> {
	public static final String EXTENSION = "extension";
	
	public HasExtensions() {}
	
	public HasExtensions(List<Extension> extensions) {
		setExtension(extensions);
	}
	
	/**
	 * Creates a HasExtensions object from a map
	 * 
	 * @param obj
	 *            a HasExtensions object as raw map
	 * @return a HasExtensions object, that behaves like a facade for the given map
	 */
	public static HasExtensions createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		HasExtensions ret = new HasExtensions();
		ret.setMap(map);
		return ret;
	}
	
	public void setExtension(List<Extension> extensions) {
		put(EXTENSION, extensions);
	}
	
	@SuppressWarnings("unchecked")
	public List<Extension> getExtensions() {
		List<Extension> ret = new ArrayList<Extension>();
		List<Map<String, Object>> values = (List<Map<String, Object>>) get(EXTENSION);
		
		if (values != null && values.size() > 0) {
			for (Map<String, Object> value : values) {
				ret.add(Extension.createAsFacade(value));
			}
		}
		
		return ret;
	}
}
