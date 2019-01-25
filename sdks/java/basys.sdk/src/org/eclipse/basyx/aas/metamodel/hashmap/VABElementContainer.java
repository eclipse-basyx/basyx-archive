package org.eclipse.basyx.aas.metamodel.hashmap;

import java.util.Map;

import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.DataElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.Operation;

public interface VABElementContainer {

	public void addDataElement(DataElement element);

	public void addOperation(Operation operation);

	public void addEvent(Object event);

	public void addElementCollection(SubmodelElementCollection collection);

	public Map<String, IProperty> getProperties();

	public Map<String, IOperation> getOperations();
}
