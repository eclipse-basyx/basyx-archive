package org.eclipse.basyx.submodel.metamodel.map.submodelelement;

import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifiable;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * SubmodelElement as defined in "Details of the Asset Administration Shell"
 * 
 * @author schnicke
 *
 */
public class SubmodelElement extends VABModelMap<Object> implements ISubmodelElement {
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
		SubmodelElement ret = new SubmodelElement();
		ret.setMap(obj);
		return ret;
	}

	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return HasDataSpecification.createAsFacade(this).getDataSpecificationReferences();
	}

	public void setDataSpecificationReferences(Set<IReference> ref) {
		HasDataSpecification.createAsFacade(this).setDataSpecificationReferences(ref);
	}

	public void addDataSpecificationReference(IReference ref) {
		getDataSpecificationReferences().add(ref);
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

	@Override
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

	public void setQualifier(Set<IConstraint> qualifiers) {
		Qualifiable.createAsFacade(this).setQualifier(qualifiers);
	}

	@Override
	public Set<IConstraint> getQualifier() {
		return Qualifiable.createAsFacade(this).getQualifier();
	}

	@Override
	public IReference getSemanticId() {
		return HasSemantics.createAsFacade(this).getSemanticId();
	}

	public void setSemanticID(IReference ref) {
		HasSemantics.createAsFacade(this).setSemanticID(ref);
	}

	@Override
	public String getKind() {
		return HasKind.createAsFacade(this).getKind();
	}

	public void setKind(String kind) {
		HasKind.createAsFacade(this).setKind(kind);
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getModelType() {
		return (String) ((Map<String, Object>) get(ModelType.MODELTYPE)).get(ModelType.NAME);
	}
}
