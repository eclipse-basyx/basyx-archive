package org.eclipse.basyx.aas.impl.metamodel.hashmap;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.operation.IOperation;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.IProperty;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.DataElement;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.operation.Operation;

public interface VABElementContainer {

	public void addDataElement(DataElement element);

	public void addOperation(Operation operation);

	public void addEvent(Object event);

	public void addElementCollection(SubmodelElementCollection collection);

	public Map<String, IProperty> getProperties();

	public Map<String, IOperation> getOperations();
}
