package org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement;

import java.util.HashMap;

import org.eclipse.basyx.aas.api.resources.IElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasDataSpecification;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.haskind.HasKind;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.qualifiable.Qualifiable;

public abstract class SubmodelElement extends HashMap<String, Object> implements IElement {
	private static final long serialVersionUID = 1L;

	public SubmodelElement() {
		putAll(new HasDataSpecification());
		putAll(new Referable());
		putAll(new Qualifiable());
		putAll(new HasSemantics());
		putAll(new HasKind());
	}

	/**
	 * Return the unique ID that identifies an VAB element
	 * 
	 * @return unique ID
	 */
	@Override
	public String getId() {
		return (String) get("idShort");
	}

	/**
	 * Set the ID of an element
	 * 
	 * @param id
	 *            New/updated element id
	 */
	@Override
	public void setId(String id) {
		put("idShort", id);
	}

}
