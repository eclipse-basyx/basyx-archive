package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier;

import java.util.HashMap;



/**
 * Identifiable class 
 * 
 * @author kuhn
 *
 */
public class Identifiable extends HashMap<String, Object> {


	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;



	/**
	 * Default constructor
	 */
	public Identifiable() {
		// Add all attributes form Packageable and Referable
		this.putAll(new Packageable());
		this.putAll(new Referable());


		// Administrative information of an element. (AdministrativeInformation)
		put("administration", null);
		// The globally unique identification of an element. (Identificator)
		put("identification", new Identification());
	}
	
	
	/**
	 * Constructor that accepts values for most relevant properties
	 */
	public Identifiable(String version, String revision, String idShort, String category, String description, int idType, String id) {
		// Add all attributes form Packageable and Referable
		this.putAll(new Packageable());
		this.putAll(new Referable(idShort, category, description));

		// Create administrative information of an element. (AdministrativeInformation)
		put("administration", new AdministrativeInformation(version, revision));
		// The globally unique identification of an element. (Identificator)
		put("identification", new Identification(idType, id));
	}
}
