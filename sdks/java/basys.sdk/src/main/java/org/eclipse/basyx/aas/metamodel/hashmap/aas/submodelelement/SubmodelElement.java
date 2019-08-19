package org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement;

import java.util.HashMap;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.ISubmodelElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasDataSpecification;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.haskind.HasKind;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.qualifiable.Qualifiable;

public abstract class SubmodelElement extends HashMap<String, Object> implements ISubmodelElement {
	private static final long serialVersionUID = 1L;
	
	public SubmodelElement() {
		putAll(new HasDataSpecification());
		putAll(new Referable());
		putAll(new Qualifiable());
		putAll(new HasSemantics());
		putAll(new HasKind());
	}



}
