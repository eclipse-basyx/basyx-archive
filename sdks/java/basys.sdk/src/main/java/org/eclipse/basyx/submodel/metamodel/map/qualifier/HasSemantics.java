package org.eclipse.basyx.submodel.metamodel.map.qualifier;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasSemantics;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * HasSemantics class
 * 
 * @author kuhn, schnicke
 *
 */
public class HasSemantics extends VABModelMap<Object> implements IHasSemantics {
	public static final String SEMANTICID = "semanticId";

	/**
	 * Constructor
	 */
	public HasSemantics() {
		// Default values
		put(SEMANTICID, new Reference());
	}

	/**
	 * Constructor
	 */
	public HasSemantics(IReference ref) {
		this.setSemanticID(ref);
	}

	/**
	 * Creates a HasSemantics object from a map
	 * 
	 * @param obj
	 *            a HasSemantics object as raw map
	 * @return a HasSemantics object, that behaves like a facade for the given map
	 */
	public static HasSemantics createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		HasSemantics ret = new HasSemantics();
		ret.setMap(map);
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getSemanticId() {
		return Reference.createAsFacade((Map<String, Object>) get(HasSemantics.SEMANTICID));
	}

	public void setSemanticID(IReference ref) {
		put(HasSemantics.SEMANTICID, ref);
	}
}
