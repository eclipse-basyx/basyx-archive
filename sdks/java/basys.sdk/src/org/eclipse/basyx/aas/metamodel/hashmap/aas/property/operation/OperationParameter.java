package org.eclipse.basyx.aas.metamodel.hashmap.aas.property.operation;


import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.Property;



/**
 * OperationParameter property class
 * 
 * @author pschorn
 *
 */
public class OperationParameter extends Property {

	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	
	
	/**
	 * Constructor, TODO Unreadable tag in vwid document
	 */
	public OperationParameter(Property property) {
		// Add qualifiers
		//putAll(new Property());
		
		super();
		
		put("ref_property", property);
	}
}
