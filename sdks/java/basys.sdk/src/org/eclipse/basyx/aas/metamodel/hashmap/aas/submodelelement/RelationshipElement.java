package org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IRelationshipElement;
import org.eclipse.basyx.aas.metamodel.facades.RelationshipElementFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;

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
	
	/**
	 * Constructor
	 */
	public RelationshipElement() {
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
	public void setFirst(Reference first) {
		new RelationshipElementFacade(this).setFirst(first);
		
	}

	@Override
	public Reference getFirst() {
		return new RelationshipElementFacade(this).getFirst();
	}

	@Override
	public void setSecond(Reference second) {
		new RelationshipElementFacade(this).setSecond(second);
		
	}

	@Override
	public Reference getSecond() {
		return new RelationshipElementFacade(this).getSecond();
	}
}
