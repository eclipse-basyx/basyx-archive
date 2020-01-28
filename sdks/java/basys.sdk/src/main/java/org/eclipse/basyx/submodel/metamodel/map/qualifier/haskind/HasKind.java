package org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.IHasKind;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
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
	public HasKind(ModelingKind kind) {
		// Kind of the element: either type or instance.

		put(KIND, kind.toString());
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
	public ModelingKind getModelingKind() {
		String str = (String) get(HasKind.KIND);
		return ModelingKind.fromString(str);
	}

	public void setModelingKind(ModelingKind kind) {
		put(HasKind.KIND, kind.toString());
	}

}
