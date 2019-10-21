package org.eclipse.basyx.submodel.metamodel.map.submodelelement;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;

public class DataElement extends SubmodelElement implements IDataElement {
	private static final long serialVersionUID = 1L;
	public static final String MODELTYPE = "DataElement";

	public DataElement() {
		// Add model type
		putAll(new ModelType(MODELTYPE));
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
