package org.eclipse.basyx.submodel.metamodel.connected.submodelelement;

import java.util.List;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * "Connected" implementation of SubmodelElementCollection
 * 
 * @author rajashek
 *
 */
public class ConnectedSubmodelElementCollection extends ConnectedSubmodelElement implements ISubmodelElementCollection {
	public ConnectedSubmodelElementCollection(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public void setValue(List<Object> value) {
		getProxy().setModelPropertyValue(SingleProperty.VALUE, value);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getValue() {
		return (List<Object>) getProxy().getModelPropertyValue(SingleProperty.VALUE);
	}

	@Override
	public void setOrdered(boolean value) {
		getProxy().setModelPropertyValue(SubmodelElementCollection.ORDERED, value);
	}

	@Override
	public boolean isOrdered() {
		return (boolean) getElem().getPath(SubmodelElementCollection.ORDERED);
	}

	@Override
	public void setAllowDuplicates(boolean value) {
		getProxy().setModelPropertyValue(SubmodelElementCollection.ALLOWDUPLICATES, value);

	}

	@Override
	public boolean isAllowDuplicates() {
		return (boolean) getElem().getPath(SubmodelElementCollection.ALLOWDUPLICATES);
	}

	@Override
	public void setElements(Map<String, ISubmodelElement> value) {
		getProxy().setModelPropertyValue(SubModel.SUBMODELELEMENT, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ISubmodelElement> getElements() {
		return (Map<String, ISubmodelElement>) getProxy().getModelPropertyValue(SubModel.SUBMODELELEMENT);
	}
}
