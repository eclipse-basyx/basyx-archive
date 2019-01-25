package org.eclipse.basyx.aas.metamodel.hashmap.aas.reference;

import java.util.HashMap;

/**
 * Key as defined in DAAS document <br/>
 * <br/>
 * A key is a reference to an element by its id.
 * 
 * @author schnicke
 *
 */
public class Key extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	private static final String TYPE = "type";
	private static final String LOCAL = "local";
	private static final String VALUE = "value";
	private static final String IDTYPE = "idType";

	/**
	 * 
	 * @param type
	 *            Denote which kind of entity is referenced. See
	 *            {@link org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.enums.KeyElements
	 *            KeyElements} and its children for possible values
	 * @param local
	 *            Denotes if the key references a model element of the same AAS
	 *            (=true) or not (=false).
	 * @param value
	 *            The key value, for example an IRDI if the idType=IRDI.
	 * @param idType
	 *            Type of the key value. See
	 *            {@link org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.enums.KeyType
	 *            KeyType} for valid values. In case of idType = idShort local shall
	 *            be true. In case type=GlobalReference idType shall not be IdShort.
	 */
	public Key(String type, boolean local, String value, String idType) {
		put(TYPE, type);
		put(LOCAL, local);
		put(VALUE, value);
		put(IDTYPE, idType);
	}

	public String getType() {
		return (String) get(TYPE);
	}

	public boolean isLocal() {
		return (boolean) get(LOCAL);
	}

	public String getValue() {
		return (String) get(VALUE);
	}

	public String idType() {
		return (String) get(IDTYPE);
	}
}
