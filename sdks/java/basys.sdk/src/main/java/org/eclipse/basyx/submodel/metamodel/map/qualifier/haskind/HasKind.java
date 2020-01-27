package org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.IHasKind;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * HasKind class
 * 
 * @author elsheikh, schnicke
 *
 */
public class HasKind extends VABModelMap<Object> implements IHasKind {
	public static final String KIND = "kind";

	/**
	 * Constructor
	 */
	public HasKind() {
		// Default value

		put(KIND, null);
	}

	/**
	 * Constructor that takes
	 * {@link org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.Kind
	 * Kind}(either Kind.Instance or Kind.Type)
	 */
	public HasKind(String kind) {
		// Kind of the element: either type or instance.

		put(KIND, kind);
	}

	/**
	 * Creates a HasKind object from a map
	 * 
	 * @param obj
	 *            a HasKind object as raw map
	 * @return a HasKind object, that behaves like a facade for the given map
	 */
	public static HasKind createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		HasKind ret = new HasKind();
		ret.setMap(map);
		return ret;
	}

	@Override
	public String getKind() {
		return (String) get(HasKind.KIND);
	}

	public void setKind(String kind) {
		put(HasKind.KIND, kind);
	}

}
