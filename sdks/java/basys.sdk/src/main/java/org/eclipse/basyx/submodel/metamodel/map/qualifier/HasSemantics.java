package org.eclipse.basyx.submodel.metamodel.map.qualifier;

import java.util.HashMap;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasSemantics;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasSemanticsFacade;

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

	public static final String SEMANTICID = "semanticId";

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
	public HasSemantics(IReference idSemantics) {
		put(SEMANTICID, idSemantics);
	}

	@Override
	public IReference getSemanticId() {
		return new HasSemanticsFacade(this).getSemanticId();
	}

	public void setSemanticID(IReference ref) {
		new HasSemanticsFacade(this).setSemanticID(ref);
	}
}
