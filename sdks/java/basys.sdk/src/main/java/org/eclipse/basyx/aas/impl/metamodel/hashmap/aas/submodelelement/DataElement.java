package org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IDataElement;

public class DataElement extends SubmodelElement implements IDataElement {
	private static final long serialVersionUID = 1L;

	public DataElement() {
	}

	/**
	 * Wraps existing map in DataElement interface
	 *
	 * @param map
	 */
	public DataElement(Map<String, Object> map) {
		putAll(map);
	}

}
