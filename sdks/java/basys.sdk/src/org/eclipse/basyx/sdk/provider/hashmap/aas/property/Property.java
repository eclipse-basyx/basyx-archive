package org.eclipse.basyx.sdk.provider.hashmap.aas.property;

import java.util.HashMap;

import org.eclipse.basyx.sdk.provider.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.sdk.provider.hashmap.aas.qualifier.HasTemplate;
import org.eclipse.basyx.sdk.provider.hashmap.aas.qualifier.Qualifiable;
import org.eclipse.basyx.sdk.provider.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.sdk.provider.hashmap.aas.qualifier.Typable;



/**
 * Abstract property class
 * 
 * @author kuhn
 *
 */
public class Property extends HashMap<String, Object> {

	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	
	/**
	 * Default constructor
	 */
	public Property() {
		// Add qualifiers
		putAll(new HasTemplate());
		putAll(new Referable());
		putAll(new Qualifiable());
		putAll(new HasSemantics());
		putAll(new Typable());
	}

	
	/**
	 * Constructor
	 */
	public Property(HasSemantics semantics, Referable referable, Qualifiable qualifiable, Typable typeable) {
		// Add qualifiers
		putAll(new HasTemplate());
		putAll(referable);
		putAll(qualifiable);
		putAll(semantics);
		putAll(typeable);
	}
}
