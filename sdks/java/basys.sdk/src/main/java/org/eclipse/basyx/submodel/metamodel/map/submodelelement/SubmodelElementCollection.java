package org.eclipse.basyx.submodel.metamodel.map.submodelelement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.facade.submodelelement.SubmodelElementFacadeFactory;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * SubmodelElementCollection as defined by DAAS document <br/>
 * A submodel element collection is a set or list of submodel elements
 * 
 * @author schnicke
 *
 */
public class SubmodelElementCollection extends SubmodelElement implements ISubmodelElementCollection {
	public static final String ORDERED = "ordered";
	public static final String ALLOWDUPLICATES = "allowDuplicates";
	public static final String MODELTYPE = "SubmodelElementCollection";


	/**
	 * Constructor
	 */
	public SubmodelElementCollection() {
		// Add model type
		putAll(new ModelType(MODELTYPE));

		// Put attributes
		put(Property.VALUE, new ArrayList<>());
		put(ORDERED, true);
		put(ALLOWDUPLICATES, true);
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
	public SubmodelElementCollection(Collection<ISubmodelElement> value, boolean ordered, boolean allowDuplicates) {
		// Add model type
		putAll(new ModelType(MODELTYPE));
		
		// Put attributes
		put(Property.VALUE, value);
		put(ORDERED, ordered);
		put(ALLOWDUPLICATES, allowDuplicates);
	}
	
	/**
	 * Creates a SubmodelElementCollection object from a map
	 * 
	 * @param obj a SubmodelElementCollection object as raw map
	 * @return a SubmodelElementCollection object, that behaves like a facade for the given map
	 */
	public static SubmodelElementCollection createAsFacade(Map<String, Object> obj) {
		SubmodelElementCollection ret = new SubmodelElementCollection();
		ret.setMap(obj);
		return ret;
	}
	
	/**
	 * Adds an element to the SubmodelElementCollection
	 * 
	 * @param elem
	 */
	public void addElement(ISubmodelElement elem) {
		getValue().add(elem);
	}

	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return HasDataSpecification.createAsFacade(this).getDataSpecificationReferences();
	}

	@Override
	public void setDataSpecificationReferences(Set<IReference> ref) {
		HasDataSpecification.createAsFacade(this).setDataSpecificationReferences(ref);

	}

	@Override
	public String getIdShort() {
		return Referable.createAsFacade(this).getIdShort();
	}

	@Override
	public String getCategory() {
		return Referable.createAsFacade(this).getCategory();
	}

	@Override
	public LangStrings getDescription() {
		return Referable.createAsFacade(this).getDescription();
	}

	public void setValue(Collection<ISubmodelElement> value) {
		put(Property.VALUE, value);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<ISubmodelElement> getValue() {
		Collection<ISubmodelElement> ret = new ArrayList<>();
		Collection<Object> smElems = (ArrayList<Object>) get(Property.VALUE);
		for(Object smElemO: smElems) {
			Map<String, Object> smElem = (Map<String, Object>) smElemO;
			ret.add(SubmodelElementFacadeFactory.createSubmodelElement(smElem));
		}
		return ret;
	}

	public void setOrdered(boolean value) {
		put(SubmodelElementCollection.ORDERED, value);
	}

	@Override
	public boolean isOrdered() {
		return (boolean) get(SubmodelElementCollection.ORDERED);
	}

	public void setAllowDuplicates(boolean value) {
		put(SubmodelElementCollection.ALLOWDUPLICATES, value);
	}

	@Override
	public boolean isAllowDuplicates() {
		return (boolean) get(SubmodelElementCollection.ALLOWDUPLICATES);
	}

	public void setElements(Map<String, ISubmodelElement> value) {
		put(SubModel.SUBMODELELEMENT, value);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, ISubmodelElement> getElements() {
		Map<String, ISubmodelElement> ret = new HashMap<>();
		Collection<Object> smElems = ((Map<String, Object>) get(SubModel.SUBMODELELEMENT)).values();
		for(Object smElemO: smElems) {
			Map<String, Object> smElem = (Map<String, Object>) smElemO;
			ret.put((String) smElem.get(Referable.IDSHORT), SubmodelElementFacadeFactory.createSubmodelElement(smElem));
		}
		return ret;
	}
}
