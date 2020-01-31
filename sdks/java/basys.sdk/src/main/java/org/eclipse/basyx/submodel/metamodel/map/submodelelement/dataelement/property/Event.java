package org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;

/**
 * Event property class
 * 
 * @author pschorn
 *
 */
public class Event extends Property {
	public static final String MODELTYPE = "Event";

	/**
	 * Constructor
	 */
	public Event() {
		// Add model type
		putAll(new ModelType(MODELTYPE));
	}
	
	/**
	 * Creates an Event object from a map
	 * 
	 * @param obj an Event object as raw map
	 * @return an Event object, that behaves like a facade for the given map
	 */
	public static Event createAsFacade(Map<String, Object> obj) {
		Event facade = new Event();
		facade.setMap(obj);
		return facade;
	}

}
