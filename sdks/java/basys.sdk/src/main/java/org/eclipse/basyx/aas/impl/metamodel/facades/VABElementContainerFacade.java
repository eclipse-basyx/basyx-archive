package org.eclipse.basyx.aas.impl.metamodel.facades;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IDataElement;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.ISubmodelElement;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.operation.IOperation;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.IVABElementContainer;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.SubModel;

/**
 * Facade providing access to attributes of entities implementing
 * IVABElementContainer
 * 
 * @author schnicke
 *
 */
public class VABElementContainerFacade implements IVABElementContainer {

	Map<String, Object> map;

	public VABElementContainerFacade(Map<String, Object> map) {
		this.map = map;
	}

	@SuppressWarnings("unchecked")
	private void addDataElement(IDataElement property) {
		((Map<String, IDataElement>) map.get(SubModel.PROPERTIES)).put(property.getIdshort(), property);

	}

	@SuppressWarnings("unchecked")
	private void addOperation(IOperation operation) {
		((Map<String, IOperation>) map.get(SubModel.OPERATIONS)).put(operation.getIdshort(), operation);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addSubModelElement(ISubmodelElement element) {
		((Map<String, ISubmodelElement>) map.get(SubModel.SUBMODELELEMENT)).put(element.getIdshort(), element);
		if (element instanceof IDataElement) {
			addDataElement((IDataElement) element);
		} else if (element instanceof IOperation) {
			addOperation((IOperation) element);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IDataElement> getDataElements() {
		return (Map<String, IDataElement>) map.get(SubModel.PROPERTIES);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IOperation> getOperations() {
		return (Map<String, IOperation>) map.get(SubModel.OPERATIONS);
	}
}
