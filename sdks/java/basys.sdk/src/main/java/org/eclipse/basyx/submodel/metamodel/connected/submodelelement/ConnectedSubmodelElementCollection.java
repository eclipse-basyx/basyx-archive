package org.eclipse.basyx.submodel.metamodel.connected.submodelelement;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.IElementContainer;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * "Connected" implementation of SubmodelElementCollection
 * 
 * @author rajashek
 *
 */
public class ConnectedSubmodelElementCollection extends ConnectedSubmodelElement implements ISubmodelElementCollection, IElementContainer  {
	public ConnectedSubmodelElementCollection(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public Map<String, ISubmodelElement> getValue() {
		return getSubmodelElements();
	}

	@Override
	public boolean isOrdered() {
		return (boolean) getElem().getPath(SubmodelElementCollection.ORDERED);
	}

	@Override
	public boolean isAllowDuplicates() {
		return (boolean) getElem().getPath(SubmodelElementCollection.ALLOWDUPLICATES);
	}

	@Override
	public Map<String, ISubmodelElement> getSubmodelElements() {
		return ConnectedSubmodelElementFactory.getConnectedSubmodelElements(getProxy(), Property.VALUE, "");
	}

	@Override
	public Map<String, IProperty> getProperties() {
		return ConnectedSubmodelElementFactory.getProperties(getProxy(), Property.VALUE, "");
	}

	@Override
	public Map<String, IOperation> getOperations() {
		return ConnectedSubmodelElementFactory.getOperations(getProxy(), Property.VALUE, "");
	}
	
	@Override
	protected KeyElements getKeyElement() {
		return KeyElements.SUBMODELELEMENTCOLLECTION;
	}
	
	/**
	 * Get submodel element by given id
	 * @param id
	 * @return specific submodel element
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ISubmodelElement getSubmodelElement(String id) {
		Map<String, Object> node =(Map<String, Object>) getProxy().getModelPropertyValue(id);
		ISubmodelElement element = ConnectedSubmodelElementFactory.getConnectedSubmodelElement(getProxy(), "", id, node);
		return element;			
	}

	/**
	 * Delete a submodel element by given id
	 * @param id
	 */
	@Override
	public void deleteSubmodelElement(String id) {
		getProxy().deleteValue(id);
	}
	
	/**
	 * adds a submodel element to the collection
	 * @param element
	 */
	@Override
	public void addSubModelElement(ISubmodelElement element) {
		if (element instanceof SubmodelElement) {
			((SubmodelElement) element).setParent(getReference());
		}
		
		getProxy().setModelPropertyValue(element.getIdShort(), element);
	}
}
