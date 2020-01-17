package org.eclipse.basyx.submodel.metamodel.facade;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.facade.submodelelement.SubmodelElementFacadeFactory;
import org.eclipse.basyx.submodel.metamodel.map.IVABElementContainer;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;

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
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ISubmodelElement> getSubmodelElements() {
		Map<String, ISubmodelElement> ret = new HashMap<>();
		Collection<Object> smElems = ((Map<String, Object>) map.get(SubModel.SUBMODELELEMENT)).values();
		for(Object smElemO: smElems) {
			Map<String, Object> smElem = (Map<String, Object>) smElemO;
			ret.put((String) smElem.get(Referable.IDSHORT), SubmodelElementFacadeFactory.createSubmodelElement(smElem));
		}
		return ret;
	}
}
