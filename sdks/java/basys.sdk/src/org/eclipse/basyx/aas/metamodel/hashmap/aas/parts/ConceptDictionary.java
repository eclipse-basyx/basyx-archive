package org.eclipse.basyx.aas.metamodel.hashmap.aas.parts;

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;

/**
 * ConceptDictionary class as described in DAAS document
 * 
 * @author elsheikh, schnicke
 *
 */
public class ConceptDictionary extends HashMap<String, Object> {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public ConceptDictionary() {
		// Add qualifier (Referable)
		putAll(new Referable());
		put("conceptDescription", new HashSet<String>());
	}
}
