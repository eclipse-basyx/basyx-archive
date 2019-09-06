package org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier;

import java.util.HashMap;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasSemantics;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.impl.metamodel.facades.HasSemanticsFacade;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.reference.Reference;

/**
 * HasSemantics class
 * 
 * @author kuhn, schnicke
 *
 */
public class HasSemantics extends HashMap<String, Object> implements IHasSemantics {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String SEMANTICID="semanticId";

	/**
	 * Constructor
	 */
	public HasSemantics() {
		// Default values
		put(SEMANTICID, null);
	}

	/**
	 * Constructor
	 */
	public HasSemantics(Reference idSemantics) {
		put(SEMANTICID, idSemantics);
	}

	@Override
	public IReference getSemanticId() {
		return new HasSemanticsFacade(this).getSemanticId();
	}

	@Override
	public void setSemanticID(IReference ref) {
		new HasSemanticsFacade(this).setSemanticID(ref);
		
	}
}
