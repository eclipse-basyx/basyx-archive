package org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.identifier;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.impl.metamodel.facades.IdentifierFacade;

/**
 * Identification class
 * 
 * @author kuhn, schnicke
 *
 */
public class Identifier extends HashMap<String, Object> implements IIdentifier {
	
	
	public static final String IDTYPE="idType";
	public static final String ID="id";

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public Identifier() {
		// Default values
		put(IDTYPE, IdentifierType.IRDI);
		put(ID, "");
	}

	public Identifier(Map<String, Object> copy) {
		putAll(copy);
	}

	/**
	 * Constructor that accepts parameter
	 */
	public Identifier(String idType, String id) {
		// Load values
		put(IDTYPE, idType);
		put(ID, id);
	}

	/**
	 * Get value of 'idType' property
	 */
	@Override
	public String getIdType() {
		return new IdentifierFacade(this).getIdType();
	}

	/**
	 * Update value of 'idType' property
	 */
	public void setIdType(String newValue) {
		new IdentifierFacade(this).setIdType(newValue);
	}

	/**
	 * Get value of 'id' property
	 */
	@Override
	public String getId() {
		return new IdentifierFacade(this).getId();
	}

	/**
	 * Update value of 'id' property
	 */
	public void setId(String newValue) {
		new IdentifierFacade(this).setId(newValue);
	}
}
