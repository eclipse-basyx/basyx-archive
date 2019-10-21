package org.eclipse.basyx.submodel.metamodel.map.submodelelement;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IRelationshipElement;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;

/**
 * RelationshipElement as defined in DAAS document <br/>
 * A relationship element is used to define a relationship between two referable
 * elements.
 * 
 * 
 * @author schnicke
 *
 */
public class RelationshipElement extends SubmodelElement implements IRelationshipElement{
	private static final long serialVersionUID = 1L;

	public static final String FIRST="first";
	public static final String SECOND="second";
	public static final String MODELTYPE = "RelationshipElement";
	
	/**
	 * Constructor
	 */
	public RelationshipElement() {
		// Add model type
		putAll(new ModelType(MODELTYPE));

		put(FIRST, null);
		put(SECOND, null);
	}

	/**
	 * 
	 * @param first
	 *            First element in the relationship taking the role of the subject.
	 * @param second
	 *            Second element in the relationship taking the role of the object.
	 */
	public RelationshipElement(Reference first, Reference second) {
		put(FIRST, first);
		put(SECOND, second);
	}

	@Override
	public void setFirst(IReference first) {
		put(RelationshipElement.FIRST, first);
		
	}

	@Override
	public IReference getFirst() {
		return (IReference) get(RelationshipElement.FIRST);
	}

	@Override
	public void setSecond(IReference second) {
		put(RelationshipElement.SECOND, second);
		
	}

	@Override
	public IReference getSecond() {
		return (IReference) get(RelationshipElement.FIRST);
	}
}
