package org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.haskind;

import java.util.HashMap;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.haskind.IHasKind;
import org.eclipse.basyx.aas.impl.metamodel.facades.HasKindFacade;

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
	
	public static final String KIND="kind";

	/**
	 * Constructor
	 */
	public HasKind() {
		// Default value

		put(KIND, null);
	}

	/**
	 * Constructor that takes
	 * {@link org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.haskind.Kind
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

	@Override
	public void setHasKindReference(String kind) {
		new HasKindFacade(this).setHasKindReference(kind);
		
	}
}
