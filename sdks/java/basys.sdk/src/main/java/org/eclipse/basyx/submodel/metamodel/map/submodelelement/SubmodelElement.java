package org.eclipse.basyx.submodel.metamodel.map.submodelelement;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
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
	public Collection<IReference> getDataSpecificationReferences() {
		return HasDataSpecification.createAsFacade(this).getDataSpecificationReferences();
	}

	public void setDataSpecificationReferences(Collection<IReference> ref) {
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

	public void setQualifier(Collection<IConstraint> qualifiers) {
		Qualifiable.createAsFacade(this).setQualifier(qualifiers);
	}

	@Override
	public Collection<IConstraint> getQualifier() {
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
	public ModelingKind getModelingKind() {
		return HasKind.createAsFacade(this).getModelingKind();
	}

	public void setModelingKind(ModelingKind kind) {
		HasKind.createAsFacade(this).setModelingKind(kind);
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getModelType() {
		return (String) ((Map<String, Object>) get(ModelType.MODELTYPE)).get(ModelType.NAME);
	}
}
