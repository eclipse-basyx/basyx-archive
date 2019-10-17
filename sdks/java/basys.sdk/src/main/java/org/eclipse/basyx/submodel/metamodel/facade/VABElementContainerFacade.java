package org.eclipse.basyx.submodel.metamodel.facade;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.map.IVABElementContainer;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;

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
		((Map<String, IDataElement>) map.get(SubModel.PROPERTIES)).put(property.getIdShort(), property);

	}

	@SuppressWarnings("unchecked")
	private void addOperation(IOperation operation) {
		((Map<String, IOperation>) map.get(SubModel.OPERATIONS)).put(operation.getIdShort(), operation);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addSubModelElement(ISubmodelElement element) {
		((Map<String, ISubmodelElement>) map.get(SubModel.SUBMODELELEMENT)).put(element.getIdShort(), element);
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
