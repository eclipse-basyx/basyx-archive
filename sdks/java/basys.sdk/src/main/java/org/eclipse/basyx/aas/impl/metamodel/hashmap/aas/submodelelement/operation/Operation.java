package org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.operation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.operation.IOperation;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.aas.impl.metamodel.facades.HasDataSpecificationFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.HasKindFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.HasSemanticsFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.QualifiableFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.ReferableFacade;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.SubmodelElement;

/**
 * Operation as defined in DAAS document <br/>
 * An operation is a submodel element with input and output variables.
 * 
 * @author schnicke
 *
 */
public class Operation extends SubmodelElement implements IOperation {
	private static final long serialVersionUID = -1381491542617026911L;

	public static final String IN = "in";
	public static final String OUT = "out";
	public static final String INVOKABLE = "invokable";

	/**
	 * Constructor
	 */
	public Operation() {
		// Input variables
		put(IN, new ArrayList<OperationVariable>());

		// Output variables
		put(OUT, new ArrayList<OperationVariable>());

		// Extension of DAAS specification for function storage
		put(INVOKABLE, null);
	}

	/**
	 * 
	 * @param in
	 *            Input parameter of the operation.
	 * @param out
	 *            Output parameter of the operation.
	 * @param function
	 *            the concrete function
	 * 
	 */
	public Operation(List<OperationVariable> in, List<OperationVariable> out, Function<Object[], Object> function) {
		// Input variables
		put(IN, in);

		// Output variables
		put(OUT, out);

		// Extension of DAAS specification for function storage
		put(INVOKABLE, function);
	}

	@Override
	public String getId() {
		return (String) get(Referable.IDSHORT);
	}

	@Override
	public void setId(String id) {
		put(Referable.IDSHORT, id);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IOperationVariable> getParameterTypes() {
		return (List<IOperationVariable>) get(Operation.IN);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IOperationVariable> getReturnTypes() {
		return (List<IOperationVariable>) get(Operation.OUT);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object invoke(Object... params) throws Exception {
		return ((Function<Object[], Object>) get(INVOKABLE)).apply(params);
	}

	public void SetParameterTypes(List<OperationVariable> in) {
		put(Operation.IN, in);
	}

	public void setReturnTypes(List<OperationVariable> out) {
		put(Operation.OUT, out);
	}

	public void setInvocable(Function<Object[], Object> endpoint) {
		put(Operation.INVOKABLE, endpoint);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Function<Object[], Object> getInvocable() {
		return (Function<Object[], Object>) get(Operation.INVOKABLE);
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
	public IReference getParent() {
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
	public void setParent(IReference obj) {
		new ReferableFacade(this).setParent(obj);

	}

	@Override
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

}
