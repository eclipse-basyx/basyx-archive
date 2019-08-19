package org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.ISubmodelElement;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.aas.metamodel.facades.HasDataSpecificationFacade;
import org.eclipse.basyx.aas.metamodel.facades.HasKindFacade;
import org.eclipse.basyx.aas.metamodel.facades.HasSemanticsFacade;
import org.eclipse.basyx.aas.metamodel.facades.OperationFacade;
import org.eclipse.basyx.aas.metamodel.facades.OperationVariableFacade;
import org.eclipse.basyx.aas.metamodel.facades.QualifiableFacade;
import org.eclipse.basyx.aas.metamodel.facades.ReferableFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;

/**
 * OperationVariable as described by DAAS document An operation variable is a
 * submodel element that is used as input or output variable of an operation.
 * 
 * @author schnicke
 *
 */
public class OperationVariable extends SubmodelElement implements IOperationVariable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param value
	 *            Describes the needed argument for an operation via a submodel
	 *            element of kind=Type
	 */
	public OperationVariable(SubmodelElement value) {
		put(Property.VALUE, value);
	}
	
	public OperationVariable() {
		
	}

	@Override
	public void setValue(ISubmodelElement value) {
		new OperationVariableFacade(this).setValue(value);
		
	}

	@Override
	public ISubmodelElement getValue() {
		return new OperationVariableFacade(this).getValue();
	}


	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return new HasDataSpecificationFacade(this).getDataSpecificationReferences();
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		new HasDataSpecificationFacade(this).setDataSpecificationReferences(ref);
		
	}

	@Override
	public String getIdshort() {
	return new ReferableFacade(this).getIdshort();
	}

	@Override
	public String getCategory() {
		return new ReferableFacade(this).getCategory();
	}

	@Override
	public String getDescription() {
		return new ReferableFacade(this).getDescription();
	}

	@Override
	public IReference  getParent() {
		return new ReferableFacade(this).getParent();
	}

	@Override
	public void setIdshort(String idShort) {
		new ReferableFacade(this).setIdshort(idShort);
		
	}

	@Override
	public void setCategory(String category) {
		new ReferableFacade(this).setCategory(category);
		
	}

	@Override
	public void setDescription(String description) {
		new ReferableFacade(this).setDescription(description);
		
	}

	@Override
	public void setParent(IReference  obj) {
		new ReferableFacade(this).setParent(obj);
		
	}

	@Override
	public void setQualifier(Set<IConstraint> qualifiers) {
		new QualifiableFacade(this).setQualifier(qualifiers);
		
	}

	@Override
	public Set<IConstraint> getQualifier() {
		return new  QualifiableFacade(this).getQualifier();
	}

	@Override
	public IReference getSemanticId() {
		return new HasSemanticsFacade(this).getSemanticId();
	}

	@Override
	public void setSemanticID(IReference ref) {
		new HasSemanticsFacade(this).setSemanticID(ref);
		
	}

	@Override
	public String getHasKindReference() {
      return new HasKindFacade(this).getHasKindReference();
	}

	@Override
	public void setHasKindReference(String kind) {
		new HasKindFacade(this).setHasKindReference(kind);
		
	}

	@Override
	public String getId() {
	return new OperationFacade(this).getId();
	}

	@Override
	public void setId(String id) {
		new OperationFacade(this).setId(id);
		
	}

}
