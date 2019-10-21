package org.eclipse.basyx.submodel.metamodel.map.submodelelement.property;

import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;

/**
 * Event property class
 * 
 * @author pschorn
 *
 */
public class Event extends SingleProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String MODELTYPE = "Event";

	/**
	 * Constructor
	 */
	public Event() {
		// Add model type
		putAll(new ModelType(MODELTYPE));
	}

}
