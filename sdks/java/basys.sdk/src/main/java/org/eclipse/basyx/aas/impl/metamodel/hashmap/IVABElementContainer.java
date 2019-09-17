package org.eclipse.basyx.aas.impl.metamodel.hashmap;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IDataElement;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.ISubmodelElement;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.operation.IOperation;

public interface IVABElementContainer {
	public void addSubModelElement(ISubmodelElement element);

	public Map<String, IDataElement> getDataElements();

	public Map<String, IOperation> getOperations();
}
