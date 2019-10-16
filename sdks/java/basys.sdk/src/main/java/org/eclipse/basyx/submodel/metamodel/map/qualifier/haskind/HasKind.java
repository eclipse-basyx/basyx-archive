package org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind;

import java.util.HashMap;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.IHasKind;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.haskind.HasKindFacade;

/**
 * HasKind class
 * 
 * @author elsheikh, schnicke
 *
 */
public class HasKind extends HashMap<String, Object> implements IHasKind {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

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

	@Override
	public String getHasKindReference() {
		return new HasKindFacade(this).getHasKindReference();
	}

	public void setHasKindReference(String kind) {
		new HasKindFacade(this).setHasKindReference(kind);

	}
}
