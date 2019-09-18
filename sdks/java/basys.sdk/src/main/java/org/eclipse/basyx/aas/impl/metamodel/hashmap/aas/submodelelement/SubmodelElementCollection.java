package org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement;

import java.util.ArrayList;
import java.util.Collection;
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
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.PropertyType;
import org.eclipse.basyx.aas.impl.metamodel.facades.HasDataSpecificationFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.HasKindFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.HasSemanticsFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.QualifiableFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.ReferableFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.VABElementContainerFacade;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.IVABElementContainer;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.valuetypedef.PropertyValueTypeDefHelper;

/**
 * SubmodelElementCollection as defined by DAAS document <br/>
 * A submodel element collection is a set or list of submodel elements
 * 
 * @author schnicke
 *
 */
public class SubmodelElementCollection extends SubmodelElement implements IContainerProperty, IVABElementContainer, ISubmodelElementCollection {
	private static final long serialVersionUID = 1L;

	public static final String ORDERED = "ordered";
	public static final String ALLOWDUPLICATES = "allowDuplicates";

	private VABElementContainerFacade containerFacade;

	/**
	 * Constructor
	 */
	public SubmodelElementCollection() {
		// Put attributes
		put(Property.VALUE, new ArrayList<>());
		put(ORDERED, true);
		put(ALLOWDUPLICATES, true);

		put(SubModel.SUBMODELELEMENT, new HashMap<>());

		// Helper for operations and properties
		put(SubModel.PROPERTIES, new HashMap<>());
		put(SubModel.OPERATIONS, new HashMap<>());
		containerFacade = new VABElementContainerFacade(this);
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

		put(SubModel.SUBMODELELEMENT, new HashMap<>());

		// Helper for operations and properties
		put(SubModel.PROPERTIES, new HashMap<>());
		put(SubModel.OPERATIONS, new HashMap<>());

		for (SubmodelElement elem : value) {
			containerFacade.addSubModelElement(elem);
		}
	}

	@Override
	public PropertyType getPropertyType() {
		return PropertyType.Container;
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
	public IReference getParent() {
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
	public void setParent(IReference obj) {
		new ReferableFacade(this).setParent(obj);

	}

	@Override
	public void setQualifier(Set<IConstraint> qualifiers) {
		new QualifiableFacade(this).setQualifier(qualifiers);

	}

	@Override
	public Set<IConstraint> getQualifier() {
		return new QualifiableFacade(this).getQualifier();
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
	public void setValue(ArrayList<?> value) {
		put(Property.VALUE, value);

	}

	@Override
	public ArrayList<?> getValue() {
		return (ArrayList<?>) get(Property.VALUE);
	}

	@Override
	public void setOrdered(boolean value) {
		put(SubmodelElementCollection.ORDERED, value);

	}

	@Override
	public boolean isOrdered() {
		return (boolean) get(SubmodelElementCollection.ORDERED);
	}

	@Override
	public void setAllowDuplicates(boolean value) {
		put(SubmodelElementCollection.ALLOWDUPLICATES, value);

	}

	@Override
	public boolean isAllowDuplicates() {
		return (boolean) get(SubmodelElementCollection.ALLOWDUPLICATES);
	}

	@Override
	public void setElements(HashMap<String, ISubmodelElement> value) {
		put(SubModel.SUBMODELELEMENT, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, ISubmodelElement> getElements() {
		return (HashMap<String, ISubmodelElement>) get(SubModel.SUBMODELELEMENT);
	}

	@Override
	public void setValue(Object obj) {
		put(Property.VALUE, obj);
		put(Property.VALUETYPE, PropertyValueTypeDefHelper.fromObject(obj));
	}

	@Override
	public void setValueId(Object obj) {
		put(Property.VALUEID, obj);
	}

	@Override
	public Object getValueId() {
		return get(Property.VALUEID);
	}

	@Override
	public void addSubModelElement(ISubmodelElement element) {
		containerFacade.addSubModelElement(element);
	}

	@Override
	public Map<String, IDataElement> getDataElements() {
		return containerFacade.getDataElements();
	}

	@Override
	public Map<String, IOperation> getOperations() {
		return containerFacade.getOperations();
	}
}