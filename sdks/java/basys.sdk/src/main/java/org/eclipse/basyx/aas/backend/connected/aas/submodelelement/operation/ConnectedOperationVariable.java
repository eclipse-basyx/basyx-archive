package org.eclipse.basyx.aas.backend.connected.aas.submodelelement.operation;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.ISubmodelElement;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.aas.backend.connected.aas.submodelelement.ConnectedSubmodelElement;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasDataSpecificationFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasKindFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasSemanticsFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedQualifiableFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedReferableFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IOperationVariable
 * @author rajashek
 *
 */
public class ConnectedOperationVariable extends ConnectedSubmodelElement implements IOperationVariable {
	public ConnectedOperationVariable(String path, VABElementProxy proxy) {
		super(path, proxy);		
	}
	
	@Override
	public void setValue(ISubmodelElement value) {
	getProxy().updateElementValue(constructPath(Property.VALUE), value);
		
	}

	@Override
	public ISubmodelElement getValue() {
	return (ISubmodelElement)getProxy().readElementValue(constructPath(Property.VALUE));
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
