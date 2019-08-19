package org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement;

import java.util.ArrayList;
import java.util.Collection;
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
import org.eclipse.basyx.aas.metamodel.facades.HasDataSpecificationFacade;
import org.eclipse.basyx.aas.metamodel.facades.HasKindFacade;
import org.eclipse.basyx.aas.metamodel.facades.HasSemanticsFacade;
import org.eclipse.basyx.aas.metamodel.facades.OperationFacade;
import org.eclipse.basyx.aas.metamodel.facades.PropertyFacade;
import org.eclipse.basyx.aas.metamodel.facades.QualifiableFacade;
import org.eclipse.basyx.aas.metamodel.facades.ReferableFacade;
import org.eclipse.basyx.aas.metamodel.facades.SubmodelElementCollectionFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.VABElementContainer;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.Operation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.valuetypedef.PropertyValueTypeDefHelper;

/**
 * SubmodelElementCollection as defined by DAAS document <br/>
 * A submodel element collection is a set or list of submodel elements
 * 
 * @author schnicke
 *
 */
public class SubmodelElementCollection extends SubmodelElement implements IContainerProperty, VABElementContainer,ISubmodelElementCollection {
	private static final long serialVersionUID = 1L;
	
	public static final String ORDERED="ordered";
	public static final String ALLOWDUPLICATES= "allowDuplicates";
	public static final String ELEMENTS="elements";

	/**
	 * Constructor
	 */
	public SubmodelElementCollection() {
		// Put attributes
		put(Property.VALUE, new ArrayList<>());
		put(ORDERED, true);
		put(ALLOWDUPLICATES, true);

		put(ELEMENTS, new HashMap<>());

		// Helper for operations and properties
		put(SubModel.PROPERTIES, new HashMap<>());
		put(SubModel.OPERATIONS, new HashMap<>());
	}

	/**
	 * 
	 * @param value
	 *            Submodel element contained in the collection
	 * @param ordered
	 *            If ordered=false then the elements in the property collection are
	 *            not ordered. If ordered=true then the elements in the collection
	 *            are ordered.
	 * @param allowDuplicates
	 *            If allowDuplicates=true then it is allowed that the collection
	 *            contains the same element several times
	 */
	public SubmodelElementCollection(Collection<SubmodelElement> value, boolean ordered, boolean allowDuplicates) {
		// Put attributes
		put(Property.VALUE, value);
		put(ORDERED, ordered);
		put(ALLOWDUPLICATES, allowDuplicates);

		put(ELEMENTS, new HashMap<>());

		// Helper for operations and properties
		put(SubModel.PROPERTIES, new HashMap<>());
		put(SubModel.OPERATIONS, new HashMap<>());

		for (SubmodelElement elem : value) {
			if (elem instanceof IProperty) {
				getProperties().put(elem.getId(), (IProperty) elem);
			} else if (elem instanceof IOperation) {
				getOperations().put(elem.getId(), (IOperation) elem);
			}
		}
	}

	public void addSubmodelElement(SubmodelElement elem) {
		if (elem instanceof IProperty) {
			addProperty((IProperty) elem);
		} else if (elem instanceof IOperation) {
			addOperation((IOperation) elem);
		}
		getElements().put(elem.getId(), elem);
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
	public PropertyType getPropertyType() {
		return PropertyType.Container;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IProperty> getProperties() {
		return (Map<String, IProperty>) get(SubModel.PROPERTIES);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IOperation> getOperations() {
		return (Map<String, IOperation>) get(SubModel.OPERATIONS);
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


	


	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return new HasDataSpecificationFacade(this).getDataSpecificationReferences();
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		new HasDataSpecificationFacade(this).setDataSpecificationReferences(ref);
		
	}

	@Override
	public String getIdshort() {
	return new ReferableFacade(this).getIdshort();
	}

	@Override
	public String getCategory() {
		return new ReferableFacade(this).getCategory();
	}

	@Override
	public String getDescription() {
		return new ReferableFacade(this).getDescription();
	}

	@Override
	public IReference  getParent() {
		return new ReferableFacade(this).getParent();
	}

	@Override
	public void setIdshort(String idShort) {
		new ReferableFacade(this).setIdshort(idShort);
		
	}

	@Override
	public void setCategory(String category) {
		new ReferableFacade(this).setCategory(category);
		
	}

	@Override
	public void setDescription(String description) {
		new ReferableFacade(this).setDescription(description);
		
	}

	@Override
	public void setParent(IReference  obj) {
		new ReferableFacade(this).setParent(obj);
		
	}

	@Override
	public void setQualifier(Set<IConstraint> qualifiers) {
		new QualifiableFacade(this).setQualifier(qualifiers);
		
	}

	@Override
	public Set<IConstraint> getQualifier() {
		return new  QualifiableFacade(this).getQualifier();
	}

	@Override
	public IReference getSemanticId() {
		return new HasSemanticsFacade(this).getSemanticId();
	}

	@Override
	public void setSemanticID(IReference ref) {
		new HasSemanticsFacade(this).setSemanticID(ref);
		
	}

	@Override
	public String getHasKindReference() {
      return new HasKindFacade(this).getHasKindReference();
	}

	@Override
	public void setHasKindReference(String kind) {
		new HasKindFacade(this).setHasKindReference(kind);
		
	}

	@Override
	public String getId() {
	return new OperationFacade(this).getId();
	}

	@Override
	public void setId(String id) {
		new OperationFacade(this).setId(id);
		
	}

	@Override
	public void setValue(ArrayList<?> value) {
		new SubmodelElementCollectionFacade(this).setValue(value);
		
	}

	@Override
	public ArrayList<?> getValue() {
		return new SubmodelElementCollectionFacade(this).getValue();
	}

	@Override
	public void setOrdered(boolean value) {
		new SubmodelElementCollectionFacade(this).setOrdered(value);
		
	}

	@Override
	public boolean isOrdered() {
		return new SubmodelElementCollectionFacade(this).isOrdered();
	}

	@Override
	public void setAllowDuplicates(boolean value) {
		new SubmodelElementCollectionFacade(this).setAllowDuplicates(value);
		
	}

	@Override
	public boolean isAllowDuplicates() {
	return new SubmodelElementCollectionFacade(this).isAllowDuplicates();
	}

	@Override
	public void setElements(HashMap<String, ISubmodelElement> value) {
		new SubmodelElementCollectionFacade(this).setElements(value);
		
	}

	@Override
	public HashMap<String, ISubmodelElement> getElements() {
		return new SubmodelElementCollectionFacade(this).getElements();
	}

	@Override
	public void setValue(Object obj) {
		put(Property.VALUE, obj);
		put(Property.VALUETYPE, PropertyValueTypeDefHelper.fromObject(obj));
		
	}

	@Override
	public void setValueId(Object obj) {
		 new PropertyFacade(this).setValueId(obj);
		
	}

	@Override
	public Object getValueId() {
		return new PropertyFacade(this).getValueId();
	}





}
