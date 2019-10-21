package org.eclipse.basyx.submodel.metamodel.map.submodelelement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasDataSpecificationFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.ReferableFacade;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Description;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;

/**
 * SubmodelElementCollection as defined by DAAS document <br/>
 * A submodel element collection is a set or list of submodel elements
 * 
 * @author schnicke
 *
 */
public class SubmodelElementCollection extends SubmodelElement implements ISubmodelElementCollection {
	private static final long serialVersionUID = 1L;

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
		put(SingleProperty.VALUE, new ArrayList<>());
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
	public SubmodelElementCollection(Collection<SubmodelElement> value, boolean ordered, boolean allowDuplicates) {
		// Put attributes
		put(SingleProperty.VALUE, value);
		put(ORDERED, ordered);
		put(ALLOWDUPLICATES, allowDuplicates);
	}

	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return new HasDataSpecificationFacade(this).getDataSpecificationReferences();
	}

	@Override
	public void setDataSpecificationReferences(Set<IReference> ref) {
		new HasDataSpecificationFacade(this).setDataSpecificationReferences(ref);

	}

	@Override
	public String getIdShort() {
		return new ReferableFacade(this).getIdShort();
	}

	@Override
	public String getCategory() {
		return new ReferableFacade(this).getCategory();
	}

	@Override
	public Description getDescription() {
		return new ReferableFacade(this).getDescription();
	}

	@Override
	public void setValue(List<Object> value) {
		put(SingleProperty.VALUE, value);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getValue() {
		return (List<Object>) get(SingleProperty.VALUE);
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
	public void setElements(Map<String, ISubmodelElement> value) {
		put(SubModel.SUBMODELELEMENT, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ISubmodelElement> getElements() {
		return (Map<String, ISubmodelElement>) get(SubModel.SUBMODELELEMENT);
	}
}
