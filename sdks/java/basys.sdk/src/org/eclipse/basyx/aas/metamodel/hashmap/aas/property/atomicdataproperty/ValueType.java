package org.eclipse.basyx.aas.metamodel.hashmap.aas.property.atomicdataproperty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.impl.resources.basic.DataTypeMapping;

public class ValueType extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public ValueType() {
		put("isCollection", null);
		put("id_semantics", null);
		put("dataObjectType", null);
	}

	public ValueType(Object value) {
		put("isCollection", value instanceof Collection);
		put("isMap", value instanceof Map); // Not in VWiD document but analog to isCollection
		put("id_semantics", null);
		put("dataObjectType", DataTypeMapping.map(value).getId());
	}
}
