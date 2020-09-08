package org.eclipse.basyx.submodel.metamodel.map.submodelelement.relationship;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.relationship.IRelationshipElement;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;

/**
 * RelationshipElement as defined in DAAS document <br/>
 * A relationship element is used to define a relationship between two referable
 * elements.
 * 
 * 
 * @author schnicke
 *
 */
public class RelationshipElement extends SubmodelElement implements IRelationshipElement {
	public static final String FIRST = "first";
	public static final String SECOND = "second";
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
		// Add model type
		putAll(new ModelType(MODELTYPE));
		
		put(FIRST, first);
		put(SECOND, second);
	}
	
	/**
	 * Creates a RelationshipElement object from a map
	 * 
	 * @param obj a RelationshipElement object as raw map
	 * @return a RelationshipElement object, that behaves like a facade for the given map
	 */
	public static RelationshipElement createAsFacade(Map<String, Object> obj) {
		RelationshipElement ret = new RelationshipElement();
		ret.setMap(obj);
		return ret;
	}

	/**
	 * Returns true if the given submodel element map is recognized as a RelationshipElement
	 */
	public static boolean isRelationshipElement(Map<String, Object> map) {
		String modelType = ModelType.createAsFacade(map).getName();
		// Either model type is set or the element type specific attributes are contained
		return MODELTYPE.equals(modelType) || (modelType == null && map.containsKey(FIRST) && map.containsKey(SECOND));
	}

	public void setFirst(IReference first) {
		put(RelationshipElement.FIRST, first);
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public IReference getFirst() {
		return Reference.createAsFacade((Map<String, Object>) get(RelationshipElement.FIRST));
	}

	public void setSecond(IReference second) {
		put(RelationshipElement.SECOND, second);
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public IReference getSecond() {
		return Reference.createAsFacade((Map<String, Object>) get(RelationshipElement.SECOND));
	}
	
	@Override
	protected KeyElements getKeyElement() {
		return KeyElements.RELATIONSHIPELEMENT;
	}
}
