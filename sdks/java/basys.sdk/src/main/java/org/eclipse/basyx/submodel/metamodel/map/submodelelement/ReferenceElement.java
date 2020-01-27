package org.eclipse.basyx.submodel.metamodel.map.submodelelement;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.Property;

/**
 * A ReferenceElement as defined in DAAS document <br/>
 * A reference element is a data element that defines a reference to another
 * element within the same or another AAS or a reference to an external object
 * or entity.
 * 
 * @author schnicke, pschorn
 *
 */
public class ReferenceElement extends DataElement implements IReferenceElement {
	public static final String MODELTYPE = "ReferenceElement";

	/**
	 * Constructor
	 */
	public ReferenceElement() {
		// Add model type
		putAll(new ModelType(MODELTYPE));

		put(Property.VALUE, null);
	}

	/**
	 * @param ref
	 *            Reference to any other referable element of the same or any other
	 *            AAS or a reference to an external object or entity
	 */
	public ReferenceElement(Reference ref) {
		// Add model type
		putAll(new ModelType(MODELTYPE));
		put(Property.VALUE, ref);
	}
	
	/**
	 * Creates a ReferenceElement object from a map
	 * 
	 * @param obj a ReferenceElement object as raw map
	 * @return a ReferenceElement object, that behaves like a facade for the given map
	 */
	public static ReferenceElement createAsFacade(Map<String, Object> obj) {
		ReferenceElement ret = new ReferenceElement();
		ret.setMap(obj);
		return ret;
	}

	@Override
	public void setValue(IReference ref) {
		put(Property.VALUE, ref);
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public IReference getValue() {
		return Reference.createAsFacade((Map<String, Object>) get(Property.VALUE));
	}

}
