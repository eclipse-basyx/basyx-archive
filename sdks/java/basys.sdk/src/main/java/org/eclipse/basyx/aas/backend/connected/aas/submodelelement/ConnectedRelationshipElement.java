package org.eclipse.basyx.aas.backend.connected.aas.submodelelement;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IRelationshipElement;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasDataSpecificationFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasKindFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasSemanticsFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedQualifiableFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedReferableFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.RelationshipElement;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of RelationshipElement
 * @author rajashek
 *
 */
public class ConnectedRelationshipElement extends ConnectedSubmodelElement implements IRelationshipElement{
	public ConnectedRelationshipElement(String path, VABElementProxy proxy) {
		super(path, proxy);		
	}
	
	@Override
	public void setFirst(IReference first) {
	getProxy().updateElementValue(constructPath(RelationshipElement.FIRST), first);
		
	}

	@Override
	public IReference getFirst() {
	return (IReference)	getProxy().readElementValue(constructPath(RelationshipElement.FIRST));
	}

	@Override
	public void setSecond(IReference second) {
		getProxy().updateElementValue(constructPath(RelationshipElement.SECOND), second);
		
	}

	@Override
	public IReference getSecond() {
		return (IReference)	getProxy().readElementValue(constructPath(RelationshipElement.FIRST));
	}

	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return new ConnectedHasDataSpecificationFacade(getPath(),getProxy()).getDataSpecificationReferences();
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		new ConnectedHasDataSpecificationFacade(getPath(),getProxy()).setDataSpecificationReferences(ref);
		
	}
	
	@Override
	public String getIdshort() {
		return new ConnectedReferableFacade(getPath(),getProxy()).getIdshort();
	}

	@Override
	public String getCategory() {
		return new ConnectedReferableFacade(getPath(),getProxy()).getCategory();
	}

	@Override
	public String getDescription() {
		return new ConnectedReferableFacade(getPath(),getProxy()).getDescription();
	}

	@Override
	public IReference getParent() {
		return new ConnectedReferableFacade(getPath(),getProxy()).getParent();
	}

	@Override
	public void setIdshort(String idShort) {
		 new ConnectedReferableFacade(getPath(),getProxy()).setIdshort(idShort);
		
	}

	@Override
	public void setCategory(String category) {
		 new ConnectedReferableFacade(getPath(),getProxy()).setCategory(category);
		
	}

	@Override
	public void setDescription(String description) {
		 new ConnectedReferableFacade(getPath(),getProxy()).setDescription(description);
		
	}

	@Override
	public void setParent(IReference obj) {
		 new ConnectedReferableFacade(getPath(),getProxy()).setParent(obj);
		
	}
	
	@Override
	public void setQualifier(Set<IConstraint> qualifiers) {
		new ConnectedQualifiableFacade(getPath(),getProxy()).setQualifier(qualifiers);
		
	}

	@Override
	public Set<IConstraint> getQualifier() {
		return new ConnectedQualifiableFacade(getPath(),getProxy()).getQualifier();
	}
	@Override
	public IReference getSemanticId() {
		return new ConnectedHasSemanticsFacade(getPath(),getProxy()).getSemanticId();
	}

	@Override
	public void setSemanticID(IReference ref) {
		 new ConnectedHasSemanticsFacade(getPath(),getProxy()).setSemanticID(ref);
		
	}
	
	@Override
	public String getHasKindReference() {
		return  new ConnectedHasKindFacade(getPath(),getProxy()).getHasKindReference();
	}

	@Override
	public void setHasKindReference(String kind) {
		new ConnectedHasKindFacade(getPath(),getProxy()).setHasKindReference(kind);
		
	}
	
	@Override
	public String getId() {
	return (String) getProxy().readElementValue(constructPath(Referable.IDSHORT));
	}

	@Override
	public void setId(String id) {
		getProxy().updateElementValue(constructPath(Referable.IDSHORT), id);
		
	}

}
