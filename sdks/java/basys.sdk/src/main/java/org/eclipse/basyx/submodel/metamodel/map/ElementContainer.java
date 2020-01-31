package org.eclipse.basyx.submodel.metamodel.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.IElementContainer;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.facade.submodelelement.SubmodelElementFacadeFactory;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * Facade providing access to attributes of entities implementing
 * IElementContainer
 * 
 * @author schnicke
 *
 */
public class ElementContainer extends VABModelMap<Object> implements IElementContainer {
	private ElementContainer() {
	}

	/**
	 * Creates a ElementContainer object from a map
	 * 
	 * @param obj
	 *            a ElementContainer object as raw map
	 * @return a ElementContainer object, that behaves like a facade for the given
	 *         map
	 */
	public static ElementContainer createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		ElementContainer ret = new ElementContainer();
		ret.setMap(map);
		return ret;
	}

	@SuppressWarnings("unchecked")
	private void addDataElement(IDataElement property) {
		((Map<String, IDataElement>) get(SubModel.PROPERTIES)).put(property.getIdShort(), property);

	}

	@SuppressWarnings("unchecked")
	private void addOperation(IOperation operation) {
		((Map<String, IOperation>) get(SubModel.OPERATIONS)).put(operation.getIdShort(), operation);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addSubModelElement(ISubmodelElement element) {
		((Map<String, ISubmodelElement>) get(SubModel.SUBMODELELEMENT)).put(element.getIdShort(), element);
		if (element instanceof IDataElement) {
			addDataElement((IDataElement) element);
		} else if (element instanceof IOperation) {
			addOperation((IOperation) element);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IDataElement> getDataElements() {
		return (Map<String, IDataElement>) get(SubModel.PROPERTIES);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IOperation> getOperations() {
		return (Map<String, IOperation>) get(SubModel.OPERATIONS);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ISubmodelElement> getSubmodelElements() {
		Map<String, ISubmodelElement> ret = new HashMap<>();
		Collection<Object> smElems = ((Map<String, Object>) get(SubModel.SUBMODELELEMENT)).values();
		for(Object smElemO: smElems) {
			Map<String, Object> smElem = (Map<String, Object>) smElemO;
			ret.put((String) smElem.get(Referable.IDSHORT), SubmodelElementFacadeFactory.createSubmodelElement(smElem));
		}
		return ret;
	}
}
