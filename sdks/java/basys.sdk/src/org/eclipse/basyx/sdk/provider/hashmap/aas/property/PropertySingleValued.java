package org.eclipse.basyx.sdk.provider.hashmap.aas.property;

import java.util.HashMap;

import org.eclipse.basyx.sdk.provider.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.sdk.provider.hashmap.aas.qualifier.Identification;
import org.eclipse.basyx.sdk.provider.hashmap.aas.qualifier.Qualifiable;
import org.eclipse.basyx.sdk.provider.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.sdk.provider.hashmap.aas.qualifier.Typable;



/**
 * Single valued property
 * 
 * FIXME: Add TimeStamped qualifier
 * 
 * @author kuhn
 *
 */
public class PropertySingleValued extends HashMap<String, Object> {

	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	
	/**
	 * Constructor
	 */
	public PropertySingleValued() {
		// Add qualifiers
		putAll(new AtomicDataProperty());

		// Default value
		put("value", null);
	}


	/**
	 * Constructor
	 */
	public PropertySingleValued(Object value) {
		// Add qualifiers
		putAll(new AtomicDataProperty());

		// Default value
		put("value", value);
	}


	/**
	 * Constructor that creates a property with meta data
	 * 
	 * @param semanticsInternal String that describes the sub model semantics e.g. its type (e.g. basys.semantics.transportsystem)
	 * @param idShort           Short ID of the property (e.g. "subsystemTopology")
	 * @param category          Additional coded meta information regarding the element type that affects expected existence of attributes (e.g. "transportSystemTopology")
	 * @param description       Descriptive sub model description (e.g. "This is a machine readable description of the transport system topology")
	 * @param qualifier         The qualifier of this sub model (e.g. "plant.maintransport")
	 */
	public PropertySingleValued(Object value, String semanticsInternal, String idShort, String category, String description, String qualifier) {
		// Add qualifiers
		putAll(new AtomicDataProperty(new Property(
					new HasSemantics(new Identification(Identification.Internal, semanticsInternal)),
					new Referable(idShort, category, description),
					new Qualifiable(qualifier),
					new Typable(Typable.KIND_INSTANCE)
				)));

		// Default value
		put("value", value);
	}
}

