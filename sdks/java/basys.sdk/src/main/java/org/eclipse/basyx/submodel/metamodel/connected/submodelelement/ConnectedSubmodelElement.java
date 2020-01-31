package org.eclipse.basyx.submodel.metamodel.connected.submodelelement;

import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedElement;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifiable;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * "Connected" implementation of SubmodelElement
 * @author rajashek
 *
 */
public abstract class ConnectedSubmodelElement extends ConnectedElement implements ISubmodelElement {
	public ConnectedSubmodelElement(VABElementProxy proxy) {
		super(proxy);		
	}

	@Override
	public String getIdShort() {
		return Referable.createAsFacade(getElem()).getIdShort();
	}

	@Override
	public String getCategory() {
		return Referable.createAsFacade(getElem()).getCategory();
	}

	@Override
	public LangStrings getDescription() {
		return Referable.createAsFacade(getElem()).getDescription();
	}

	@Override
	public IReference getParent() {
		return Referable.createAsFacade(getElem()).getParent();
	}

	@Override
	public Set<IConstraint> getQualifier() {
		return Qualifiable.createAsFacade(getElem()).getQualifier();
	}

	@Override
	public IReference getSemanticId() {
		return HasSemantics.createAsFacade(getElem()).getSemanticId();
	}

	@Override
	public ModelingKind getModelingKind() {
		return HasKind.createAsFacade(getElem()).getModelingKind();

	}

	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return HasDataSpecification.createAsFacade(getElem()).getDataSpecificationReferences();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public String getModelType() {
		return (String) ((Map<String, Object>) getElem().get(ModelType.MODELTYPE)).get(ModelType.NAME);
	}
}
