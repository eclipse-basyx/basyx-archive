package org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;

public class DataElement extends SubmodelElement implements IDataElement {
	public static final String MODELTYPE = "DataElement";

	public DataElement() {
		// Add model type
		putAll(new ModelType(MODELTYPE));
	}
}
