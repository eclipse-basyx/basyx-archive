package org.eclipse.basyx.aas.backend.connected.aas.submodelelement;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IRelationshipElement;
import org.eclipse.basyx.aas.backend.connected.aas.reference.ConnectedReference;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasDataSpecificationFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasKindFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasSemanticsFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedQualifiableFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedReferableFacade;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.RelationshipElement;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

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

	@Override
	public IReference getFirst() {
		return new ConnectedReference(getProxy().getDeepProxy(RelationshipElement.FIRST));
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
	public HashSet<IReference> getDataSpecificationReferences() {
		return new ConnectedHasDataSpecificationFacade(getProxy()).getDataSpecificationReferences();
	}

	@Override
	public String getIdshort() {
		return new ConnectedReferableFacade(getProxy()).getIdshort();
	}

	@Override
	public String getCategory() {
		return new ConnectedReferableFacade(getProxy()).getCategory();
	}

	@Override
	public String getDescription() {
		return new ConnectedReferableFacade(getProxy()).getDescription();
	}

	@Override
	public IReference getParent() {
		return new ConnectedReferableFacade(getProxy()).getParent();
	}

	@Override
	public Set<IConstraint> getQualifier() {
		return new ConnectedQualifiableFacade(getProxy()).getQualifier();
	}

	@Override
	public IReference getSemanticId() {
		return new ConnectedHasSemanticsFacade(getProxy()).getSemanticId();
	}

	@Override
	public String getHasKindReference() {
		return new ConnectedHasKindFacade(getProxy()).getHasKindReference();
	}

	@Override
	public String getId() {
		return (String) getProxy().getModelPropertyValue(Referable.IDSHORT);
	}

	@Override
	public void setId(String id) {
		getProxy().setModelPropertyValue(Referable.IDSHORT, id);
	}

}
