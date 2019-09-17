package org.eclipse.basyx.aas.backend.connected.aas.submodelelement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IDataElement;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.ISubmodelElement;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.ISubmodelElementCollection;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.operation.IOperation;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.IContainerProperty;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.IProperty;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.PropertyType;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasDataSpecificationFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasKindFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasSemanticsFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedQualifiableFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedReferableFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedVABElementContainerFacade;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.IVABElementContainer;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * "Connected" implementation of SubmodelElementCollection
 * 
 * @author rajashek
 *
 */
public class ConnectedSubmodelElementCollection extends ConnectedSubmodelElement implements IContainerProperty, IVABElementContainer, ISubmodelElementCollection {
	ConnectedVABElementContainerFacade facade;

	public ConnectedSubmodelElementCollection(VABElementProxy proxy) {
		super(proxy);
		facade = new ConnectedVABElementContainerFacade(proxy);
	}

	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return new ConnectedHasDataSpecificationFacade(getProxy()).getDataSpecificationReferences();
	}

	@Override
	public String getIdshort() {
		return new ConnectedReferableFacade(getProxy()).getIdshort();
	}

	@Override
	public String getCategory() {
		return new ConnectedReferableFacade(getProxy()).getCategory();
	}

	@Override
	public String getDescription() {
		return new ConnectedReferableFacade(getProxy()).getDescription();
	}

	@Override
	public IReference getParent() {
		return new ConnectedReferableFacade(getProxy()).getParent();
	}

	@Override
	public Set<IConstraint> getQualifier() {
		return new ConnectedQualifiableFacade(getProxy()).getQualifier();
	}

	@Override
	public IReference getSemanticId() {
		return new ConnectedHasSemanticsFacade(getProxy()).getSemanticId();
	}

	@Override
	public String getHasKindReference() {
		return new ConnectedHasKindFacade(getProxy()).getHasKindReference();
	}

	@Override
	public String getId() {
		return (String) getProxy().getModelPropertyValue(Referable.IDSHORT);
	}

	@Override
	public void setId(String id) {
		getProxy().setModelPropertyValue(Referable.IDSHORT, id);

	}

	@Override
	public void setValue(ArrayList<?> value) {
		getProxy().setModelPropertyValue(Property.VALUE, value);

	}

	@Override
	public ArrayList<?> getValue() {
		return (ArrayList<?>) getProxy().getModelPropertyValue(Property.VALUE);
	}

	@Override
	public void setOrdered(boolean value) {
		getProxy().setModelPropertyValue(SubmodelElementCollection.ORDERED, value);

	}

	@Override
	public boolean isOrdered() {
		return (boolean) getProxy().getModelPropertyValue(SubmodelElementCollection.ORDERED);
	}

	@Override
	public void setAllowDuplicates(boolean value) {
		getProxy().setModelPropertyValue(SubmodelElementCollection.ALLOWDUPLICATES, value);

	}

	@Override
	public boolean isAllowDuplicates() {
		return (boolean) getProxy().getModelPropertyValue(SubmodelElementCollection.ALLOWDUPLICATES);
	}

	@Override
	public void setElements(HashMap<String, ISubmodelElement> value) {
		getProxy().setModelPropertyValue(SubModel.SUBMODELELEMENT, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, ISubmodelElement> getElements() {
		return (HashMap<String, ISubmodelElement>) getProxy().getModelPropertyValue(SubModel.SUBMODELELEMENT);
	}

	@Override
	public void setValueId(Object obj) {
		getProxy().setModelPropertyValue(Property.VALUEID, obj);

	}

	@Override
	public Object getValueId() {
		return getProxy().getModelPropertyValue(Property.VALUEID);
	}

	@Override
	public PropertyType getPropertyType() {
		return PropertyType.Container;
	}

	@Override
	public void setValue(Object obj) {
		getProxy().setModelPropertyValue(Property.VALUE, obj);
		getProxy().setModelPropertyValue(Property.VALUETYPE, PropertyValueTypeDefHelper.fromObject(obj));

	}

	public void addProperty(IProperty property) {
		getElements().put(property.getId(), property);
		getDataElements().put(property.getId(), property);
	}

	public void addOperation(IOperation operation) {
		getElements().put(operation.getId(), operation);
		getOperations().put(operation.getId(), operation);
	}

	@Override
	public void addSubModelElement(ISubmodelElement element) {
		facade.addSubModelElement(element);
	}

	@Override
	public Map<String, IDataElement> getDataElements() {
		return facade.getDataElements();
	}

	@Override
	public Map<String, IOperation> getOperations() {
		return facade.getOperations();
	}
}
