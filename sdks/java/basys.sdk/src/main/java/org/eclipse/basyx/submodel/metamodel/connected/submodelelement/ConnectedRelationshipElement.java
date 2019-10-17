package org.eclipse.basyx.submodel.metamodel.connected.submodelelement;

import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IRelationshipElement;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasDataSpecificationFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasSemanticsFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.ReferableFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.haskind.HasKindFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.qualifiable.QualifiableFacade;
import org.eclipse.basyx.submodel.metamodel.facade.reference.ReferenceFacade;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.RelationshipElement;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * "Connected" implementation of RelationshipElement
 * 
 * @author rajashek
 *
 */
public class ConnectedRelationshipElement extends ConnectedSubmodelElement implements IRelationshipElement {
	public ConnectedRelationshipElement(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public void setFirst(IReference first) {
		getProxy().setModelPropertyValue(RelationshipElement.FIRST, first);

	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getFirst() {
		return new ReferenceFacade((Map<String, Object>) getElem().getPath(RelationshipElement.FIRST));
	}

	@Override
	public void setSecond(IReference second) {
		getProxy().setModelPropertyValue(RelationshipElement.SECOND, second);

	}

	@Override
	public IReference getSecond() {
		return (IReference) getProxy().getModelPropertyValue(RelationshipElement.FIRST);
	}

	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return new HasDataSpecificationFacade(getElem()).getDataSpecificationReferences();
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
	public String getDescription() {
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
}
