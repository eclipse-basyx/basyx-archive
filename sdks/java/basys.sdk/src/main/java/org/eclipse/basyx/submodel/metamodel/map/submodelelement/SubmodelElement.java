package org.eclipse.basyx.submodel.metamodel.map.submodelelement;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasDataSpecificationFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasSemanticsFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.ReferableFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.haskind.HasKindFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.qualifiable.QualifiableFacade;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifiable;

/**
 * SubmodelElement as defined in "Details of the Asset Administration Shell"
 * 
 * @author schnicke
 *
 */
public class SubmodelElement extends HashMap<String, Object> implements ISubmodelElement {
	private static final long serialVersionUID = 1L;
	public static final String MODELTYPE = "SubmodelElement";

	public SubmodelElement() {
		// Add model type
		putAll(new ModelType(MODELTYPE));

		putAll(new HasDataSpecification());
		putAll(new Referable());
		putAll(new Qualifiable());
		putAll(new HasSemantics());
		putAll(new HasKind());
	}
	
	/**
	 * Creates a SubmodelElement object from a map
	 * 
	 * @param obj a SubmodelElement object as raw map
	 * @return a SubmodelElement object, that behaves like a facade for the given map
	 */
	public static SubmodelElement createAsFacade(Map<String, Object> obj) {
		SubmodelElement facade = new SubmodelElement();
		facade.putAll(obj);
		return facade;
	}

	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return new HasDataSpecificationFacade(this).getDataSpecificationReferences();
	}

	public void setDataSpecificationReferences(Set<IReference> ref) {
		new HasDataSpecificationFacade(this).setDataSpecificationReferences(ref);
	}

	public void addDataSpecificationReference(IReference ref) {
		getDataSpecificationReferences().add(ref);
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
	public LangStrings getDescription() {
		return new ReferableFacade(this).getDescription();
	}

	@Override
	public IReference getParent() {
		return new ReferableFacade(this).getParent();
	}

	@Override
	public void setIdShort(String idShort) {
		new ReferableFacade(this).setIdShort(idShort);
	}

	public void setCategory(String category) {
		new ReferableFacade(this).setCategory(category);
	}

	public void setDescription(LangStrings description) {
		new ReferableFacade(this).setDescription(description);
	}

	public void setParent(IReference obj) {
		new ReferableFacade(this).setParent(obj);
	}

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

	public void setSemanticID(IReference ref) {
		new HasSemanticsFacade(this).setSemanticID(ref);
	}

	@Override
	public String getKind() {
		return new HasKindFacade(this).getKind();
	}

	public void setKind(String kind) {
		new HasKindFacade(this).setKind(kind);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public String getModelType() {
		return (String) ((Map<String, Object>) get(ModelType.MODELTYPE)).get(ModelType.NAME);
	}
}
