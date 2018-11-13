package org.eclipse.basyx.aas.metamodel.hashmap.aas.property.operation;


import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.Property;



/**
 * OperationEndpoint property class
 * 
 * @author pschorn
 *
 */
public class Endpoint extends Property {

	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	
	
	/**
	 * Constructor
	 */
	public Endpoint(String endpointAddress) {
		// Add qualifiers
		//putAll(new Property());
		
		super();
		
		put("endpointAddress", endpointAddress);
	}

}
