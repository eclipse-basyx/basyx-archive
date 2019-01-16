package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.haskind;

import java.util.HashMap;

/**
 * HasKind class
 * 
 * @author elsheikh, schnicke
 *
 */
public class HasKind extends HashMap<String, Object> {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public HasKind() {
		// Default value

		put("kind", null);
	}

	/**
	 * Constructor that takes
	 * {@link org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.haskind.Kind
	 * Kind}(either Kind.Instance or Kind.Type)
	 */
	public HasKind(String kind) {
		// Kind of the element: either type or instance.

		put("kind", kind);
	}
}
