package org.eclipse.basyx.submodel.metamodel.map.submodelelement;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;

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

	private static final long serialVersionUID = 1L;
	public static final String MODELTYPE = "ReferenceElement";

	/**
	 * Constructor
	 */
	public ReferenceElement() {
		// Add model type
		putAll(new ModelType(MODELTYPE));

		put(SingleProperty.VALUE, null);
	}

	/**
	 * @param ref
	 *            Reference to any other referable element of the same or any other
	 *            AAS or a reference to an external object or entity
	 */
	public ReferenceElement(Reference ref) {
		put(SingleProperty.VALUE, ref);
	}

	@Override
	public void setValue(IReference ref) {
		put(SingleProperty.VALUE, ref);
		
	}

	@Override
	public IReference getValue() {
		return (IReference) get(SingleProperty.VALUE);
	}

}
