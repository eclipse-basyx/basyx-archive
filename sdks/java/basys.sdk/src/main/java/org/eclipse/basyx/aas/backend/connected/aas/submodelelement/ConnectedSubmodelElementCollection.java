package org.eclipse.basyx.aas.backend.connected.aas.submodelelement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.api.exception.FeatureNotImplementedException;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.ISubmodelElement;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.ISubmodelElementCollection;
import org.eclipse.basyx.aas.api.resources.IContainerProperty;
import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.PropertyType;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasDataSpecificationFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasKindFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasSemanticsFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedQualifiableFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedReferableFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.VABElementContainer;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.DataElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.Operation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of SubmodelElementCollection
 * @author rajashek
 *
 */
public class ConnectedSubmodelElementCollection extends ConnectedSubmodelElement implements IContainerProperty, VABElementContainer,ISubmodelElementCollection {
	public ConnectedSubmodelElementCollection(String path, VABElementProxy proxy) {
		super(path, proxy);		
	}
	
	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return new ConnectedHasDataSpecificationFacade(getPath(),getProxy()).getDataSpecificationReferences();
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		new ConnectedHasDataSpecificationFacade(getPath(),getProxy()).setDataSpecificationReferences(ref);
		
	}
	
	@Override
	public String getIdshort() {
		return new ConnectedReferableFacade(getPath(),getProxy()).getIdshort();
	}

	@Override
	public String getCategory() {
		return new ConnectedReferableFacade(getPath(),getProxy()).getCategory();
	}

	@Override
	public String getDescription() {
		return new ConnectedReferableFacade(getPath(),getProxy()).getDescription();
	}

	@Override
	public IReference getParent() {
		return new ConnectedReferableFacade(getPath(),getProxy()).getParent();
	}

	@Override
	public void setIdshort(String idShort) {
		 new ConnectedReferableFacade(getPath(),getProxy()).setIdshort(idShort);
		
	}

	@Override
	public void setCategory(String category) {
		 new ConnectedReferableFacade(getPath(),getProxy()).setCategory(category);
		
	}

	@Override
	public void setDescription(String description) {
		 new ConnectedReferableFacade(getPath(),getProxy()).setDescription(description);
		
	}

	@Override
	public void setParent(IReference obj) {
		 new ConnectedReferableFacade(getPath(),getProxy()).setParent(obj);
		
	}
	@Override
	public Set<IConstraint> getQualifier() {
		return new ConnectedQualifiableFacade(getPath(),getProxy()).getQualifier();
	}
	
	@Override
	public IReference getSemanticId() {
		return new ConnectedHasSemanticsFacade(getPath(),getProxy()).getSemanticId();
	}

	@Override
	public void setSemanticID(IReference ref) {
		 new ConnectedHasSemanticsFacade(getPath(),getProxy()).setSemanticID(ref);
		
	}
	
	@Override
	public String getHasKindReference() {
		return  new ConnectedHasKindFacade(getPath(),getProxy()).getHasKindReference();
	}

	@Override
	public void setHasKindReference(String kind) {
		new ConnectedHasKindFacade(getPath(),getProxy()).setHasKindReference(kind);
		
	}
	
	@Override
	public String getId() {
	return (String) getProxy().readElementValue(constructPath(Referable.IDSHORT));
	}

	@Override
	public void setId(String id) {
		getProxy().updateElementValue(constructPath(Referable.IDSHORT), id);
		
	}
	
	@Override
	public void setValue(ArrayList<?> value) {
	getProxy().updateElementValue(constructPath(Property.VALUE), value);
		
	}

	@Override
	public ArrayList<?> getValue() {
		return (ArrayList<?>)getProxy().readElementValue(constructPath(Property.VALUE));
	}

	@Override
	public void setOrdered(boolean value) {
		getProxy().updateElementValue(constructPath(SubmodelElementCollection.ORDERED), value);
		
	}

	@Override
	public boolean isOrdered() {
	return (boolean)getProxy().readElementValue(constructPath(SubmodelElementCollection.ORDERED));
	}

	@Override
	public void setAllowDuplicates(boolean value) {
		getProxy().updateElementValue(constructPath(SubmodelElementCollection.ALLOWDUPLICATES), value);
		
	}

	@Override
	public boolean isAllowDuplicates() {
		return (boolean)getProxy().readElementValue(constructPath(SubmodelElementCollection.ALLOWDUPLICATES));
	}

	@Override
	public void setElements(HashMap<String, ISubmodelElement> value) {
		getProxy().updateElementValue(constructPath(SubmodelElementCollection.ELEMENTS), value);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, ISubmodelElement> getElements() {
		return (HashMap<String, ISubmodelElement>)getProxy().readElementValue(constructPath(SubmodelElementCollection.ELEMENTS));
	}
	
	@Override
	public void setValueId(Object obj) {
		getProxy().updateElementValue(constructPath(Property.VALUEID), obj);
		
	}

	@Override
	public Object getValueId() {
		return getProxy().readElementValue(constructPath(Property.VALUEID));
	}



	@Override
	public PropertyType getPropertyType() {
		return PropertyType.Container;
	}


	@Override
	public void addDataElement(DataElement element) {
		if (element instanceof IProperty) {
			addProperty((IProperty) element);
		} else {
			throw new RuntimeException("Tried to add DataElement with id " + element.getId() + " which is does not implement IProperty");
		}
	}

	@Override
	public void addOperation(Operation operation) {
		if (operation instanceof IOperation) {
			addOperation((IOperation) operation);
		} else {
			throw new RuntimeException("Tried to add Operation with id " + operation.getId() + " which is does not implement IOperation");
		}
	}

	@Override
	public void addEvent(Object event) {
		// TODO Auto-generated method stub
		throw new FeatureNotImplementedException();
		
	}

	@Override
	public void addElementCollection(SubmodelElementCollection collection) {
		getElements().put(collection.getId(), collection);
		if (collection instanceof IProperty) {
			getProperties().put(collection.getId(), collection);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IProperty> getProperties() {
		return (Map<String, IProperty>) getProxy().readElementValue(constructPath(SubModel.PROPERTIES));
	}

	@Override
	public Map<String, IOperation> getOperations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValue(Object obj) {
		getProxy().updateElementValue(constructPath(Property.VALUE), obj);
		getProxy().updateElementValue(constructPath(Property.VALUETYPE), PropertyValueTypeDefHelper.fromObject(obj));
		
	}
	
	public void addProperty(IProperty property) {
		getElements().put(property.getId(), (SubmodelElement) property);
		getProperties().put(property.getId(), property);
	}
	public void addOperation(IOperation operation) {
		getElements().put(operation.getId(), (SubmodelElement) operation);
		getOperations().put(operation.getId(), operation);
	}

	@Override
	public void setQualifier(Set<IConstraint> qualifiers) {
		new ConnectedQualifiableFacade(getPath(),getProxy()).setQualifier(qualifiers);
		
	}
	
}
