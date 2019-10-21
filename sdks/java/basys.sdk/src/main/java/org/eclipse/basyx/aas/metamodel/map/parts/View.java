package org.eclipse.basyx.aas.metamodel.map.parts;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.aas.metamodel.api.parts.IView;
import org.eclipse.basyx.aas.metamodel.facade.parts.ViewFacade;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasDataSpecificationFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasSemanticsFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.ReferableFacade;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Description;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;

/**
 * View as defined by DAAS document. <br/>
 * A view is a collection of referable elements w.r.t. to a specific viewpoint
 * of one or more stakeholders.
 * 
 * @author kuhn, schnicke
 *
 */
public class View extends HashMap<String, Object> implements IView {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

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

	public void setContainedElement(Set<IReference> references) {
		new ViewFacade(this).setContainedElement(references);

	}

	@Override
	public Set<IReference> getContainedElement() {
		return new ViewFacade(this).getContainedElement();
	}

	@Override
	public IReference getSemanticId() {
		return new HasSemanticsFacade(this).getSemanticId();
	}

	public void setSemanticID(IReference ref) {
		new HasSemanticsFacade(this).setSemanticID(ref);

	}

	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return new HasDataSpecificationFacade(this).getDataSpecificationReferences();
	}

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
	public IReference getParent() {
		return new ReferableFacade(this).getParent();
	}

	public void setIdShort(String idShort) {
		new ReferableFacade(this).setIdShort(idShort);
	}

	public void setCategory(String category) {
		new ReferableFacade(this).setCategory(category);
	}

	public void setDescription(Description description) {
		new ReferableFacade(this).setDescription(description);
	}

	public void setParent(IReference obj) {
		new ReferableFacade(this).setParent(obj);
	}

}
