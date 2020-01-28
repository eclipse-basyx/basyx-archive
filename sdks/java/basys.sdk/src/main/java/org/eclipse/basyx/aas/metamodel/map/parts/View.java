package org.eclipse.basyx.aas.metamodel.map.parts;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.metamodel.api.parts.IView;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.reference.ReferenceHelper;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * View as defined by DAAS document. <br/>
 * A view is a collection of referable elements w.r.t. to a specific viewpoint
 * of one or more stakeholders.
 * 
 * @author kuhn, schnicke
 *
 */
public class View extends VABModelMap<Object> implements IView {
	public static final String CONTAINEDELEMENT = "containedElement";
	public static final String MODELTYPE = "View";

	/**
	 * Constructor
	 */
	public View() {
		// Add model type
		putAll(new ModelType(MODELTYPE));

		// Add qualifiers
		putAll(new HasSemantics());
		putAll(new HasDataSpecification());
		putAll(new Referable());

		// Default values
		put(CONTAINEDELEMENT, new HashSet<Reference>());
	}

	/**
	 * 
	 * @param references
	 *            Referable elements that are contained in the view.
	 */
	public View(Set<IReference> references) {
		this();
		put(CONTAINEDELEMENT, references);
	}

	/**
	 * Creates a View object from a map
	 * 
	 * @param obj
	 *            a View object as raw map
	 * @return a View object, that behaves like a facade for the given map
	 */
	public static View createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		View ret = new View();
		ret.setMap(map);
		return ret;
	}

	public void setContainedElement(Set<IReference> references) {
		put(View.CONTAINEDELEMENT, references);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IReference> getContainedElement() {
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) get(View.CONTAINEDELEMENT);
		return ReferenceHelper.transform(set);
	}

	@Override
	public IReference getSemanticId() {
		return HasSemantics.createAsFacade(this).getSemanticId();
	}

	public void setSemanticId(IReference ref) {
		HasSemantics.createAsFacade(this).setSemanticID(ref);

	}

	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return HasDataSpecification.createAsFacade(this).getDataSpecificationReferences();
	}

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

	@Override
	public IReference getParent() {
		return Referable.createAsFacade(this).getParent();
	}

	public void setIdShort(String idShort) {
		Referable.createAsFacade(this).setIdShort(idShort);
	}

	public void setCategory(String category) {
		Referable.createAsFacade(this).setCategory(category);
	}

	public void setDescription(LangStrings description) {
		Referable.createAsFacade(this).setDescription(description);
	}

	public void setParent(IReference obj) {
		Referable.createAsFacade(this).setParent(obj);
	}

}
