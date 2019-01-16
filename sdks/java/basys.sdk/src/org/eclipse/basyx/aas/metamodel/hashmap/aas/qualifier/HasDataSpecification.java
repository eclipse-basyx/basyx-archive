package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier;

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;

/**
 * HasDataSpecification class
 * 
 * @author elsheikh, schnicke
 *
 */
public class HasDataSpecification extends HashMap<String, Object> {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public HasDataSpecification() {
		// Default values
		put("hasDataSpecification", new HashSet<Reference>());
	}
}
