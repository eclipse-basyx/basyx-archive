package org.eclipse.basyx.submodel.metamodel.map;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;

public interface IVABElementContainer {
	public void addSubModelElement(ISubmodelElement element);

	public Map<String, IDataElement> getDataElements();

	public Map<String, IOperation> getOperations();
}
