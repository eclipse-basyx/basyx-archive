package org.eclipse.basyx.submodel.metamodel.connected.submodelelement;

import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedElement;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasDataSpecificationFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasSemanticsFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.ReferableFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.haskind.HasKindFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.qualifiable.QualifiableFacade;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Description;
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
		return new ReferableFacade(getElem()).getIdShort();
	}

	@Override
	public String getCategory() {
		return new ReferableFacade(getElem()).getCategory();
	}

	@Override
	public Description getDescription() {
		return new ReferableFacade(getElem()).getDescription();
	}

	@Override
	public IReference getParent() {
		return new ReferableFacade(getElem()).getParent();
	}

	@Override
	public Set<IConstraint> getQualifier() {
		return new QualifiableFacade(getElem()).getQualifier();
	}

	@Override
	public IReference getSemanticId() {
		return new HasSemanticsFacade(getElem()).getSemanticId();
	}

	@Override
	public String getHasKindReference() {
		return new HasKindFacade(getElem()).getHasKindReference();
	}

	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return new HasDataSpecificationFacade(getElem()).getDataSpecificationReferences();
	}
}
