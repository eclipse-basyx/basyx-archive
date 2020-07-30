package org.eclipse.basyx.submodel.metamodel.map.submodelelement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.facade.submodelelement.SubmodelElementFacadeFactory;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;

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
	 * Returns true if the given submodel element map is recognized as a submodel element collection
	 */
	public static boolean isSubmodelElementCollection(Map<String, Object> map) {
		String modelType = ModelType.createAsFacade(map).getName();
		// Either model type is set or the element type specific attributes are contained (fallback)
		return MODELTYPE.equals(modelType)
				|| (map.containsKey(Property.VALUE) && map.containsKey(ORDERED) && map.containsKey(ALLOWDUPLICATES));
	}

	/**
	 * Adds an element to the SubmodelElementCollection
	 * 
	 * @param elem
	 */
	@SuppressWarnings("unchecked")
	public void addElement(ISubmodelElement elem) {
		if (elem instanceof SubmodelElement) {
			((SubmodelElement) elem).setParent(getReference());
		}
		((List<Object>) get(Property.VALUE)).add(elem);
	}

	@Override
	public Collection<IReference> getDataSpecificationReferences() {
		return HasDataSpecification.createAsFacade(this).getDataSpecificationReferences();
	}

	@Override
	public void setDataSpecificationReferences(Collection<IReference> ref) {
		HasDataSpecification.createAsFacade(this).setDataSpecificationReferences(ref);

	}

	@Override
	public String getIdShort() {
		return Referable.createAsFacade(this, getKeyElement()).getIdShort();
	}

	@Override
	public String getCategory() {
		return Referable.createAsFacade(this, getKeyElement()).getCategory();
	}

	@Override
	public LangStrings getDescription() {
		return Referable.createAsFacade(this, getKeyElement()).getDescription();
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
		put(Property.VALUE, value.values());
	}

	public void setElements(Collection<ISubmodelElement> value) {
		put(Property.VALUE, value);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, ISubmodelElement> getSubmodelElements() {
		Map<String, ISubmodelElement> ret = new HashMap<>();
		Collection<Object> smElems = (Collection<Object>) get(Property.VALUE);
		for(Object smElemO: smElems) {
			Map<String, Object> smElem = (Map<String, Object>) smElemO;
			ret.put((String) smElem.get(Referable.IDSHORT), SubmodelElementFacadeFactory.createSubmodelElement(smElem));
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IProperty> getProperties() {
		Map<String, IProperty> ret = new HashMap<>();
		Collection<Object> smElems = (Collection<Object>) get(Property.VALUE);
		for (Object smElemO : smElems) {
			Map<String, Object> smElem = (Map<String, Object>) smElemO;
			if (Property.isProperty(smElem)) {
				String idShort = Referable.createAsFacade(smElem, KeyElements.DATAELEMENT).getIdShort();
				IProperty dataElement = (IProperty) SubmodelElementFacadeFactory.createSubmodelElement(smElem);
				ret.put(idShort, dataElement);
			}
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IOperation> getOperations() {
		Map<String, IOperation> ret = new HashMap<>();
		Collection<Object> smElems = (Collection<Object>) get(Property.VALUE);
		for (Object smElemO : smElems) {
			Map<String, Object> smElem = (Map<String, Object>) smElemO;
			if (Operation.isOperation(smElem)) {
				String idShort = Referable.createAsFacade(smElem, KeyElements.OPERATION).getIdShort();
				ret.put(idShort, Operation.createAsFacade(smElem));
			}
		}
		return ret;
	}
	
	@Override
	protected KeyElements getKeyElement() {
		return KeyElements.SUBMODELELEMENTCOLLECTION;
	}
}
