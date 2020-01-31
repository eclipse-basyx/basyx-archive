package org.eclipse.basyx.submodel.metamodel.map.reference;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * Key as defined in DAAS document <br/>
 * <br/>
 * A key is a reference to an element by its id.
 * 
 * @author schnicke
 *
 */
public class Key extends VABModelMap<Object> implements IKey {
	public static final String TYPE = "type";
	public static final String LOCAL = "local";
	public static final String VALUE = "value";
	public static final String IDTYPE = "idType";

	/**
	 * 
	 * @param type
	 *            Denote which kind of entity is referenced. See
	 *            {@link org.eclipse.basyx.submodel.metamodel.map.reference.enums.KeyElements
	 *            KeyElements} and its children for possible values
	 * @param local
	 *            Denotes if the key references a model element of the same AAS
	 *            (=true) or not (=false).
	 * @param value
	 *            The key value, for example an IRDI if the idType=IRDI.
	 * @param idType
	 *            Type of the key value. See
	 *            {@link org.eclipse.basyx.submodel.metamodel.map.reference.enums.KeyType
	 *            KeyType} for valid values. In case of idType = idShort local shall
	 *            be true. In case type=GlobalReference idType shall not be IdShort.
	 */
	public Key(String type, boolean local, String value, String idType) {
		put(TYPE, type);
		put(LOCAL, local);
		put(VALUE, value);
		put(IDTYPE, idType);
	}

	/**
	 * Private constructor enabling createAsFacade pattern
	 */
	private Key() {

	}

	/**
	 * Creates a Key object from a map
	 * 
	 * @param obj
	 *            a Key object as raw map
	 * @return a Key object, that behaves like a facade for the given map
	 */
	public static Key createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		Key ret = new Key();
		ret.setMap(map);
		return ret;
	}

	@Override
	public String getType() {
		return (String) get(Key.TYPE);
	}

	@Override
	public boolean isLocal() {
		return (boolean) get(Key.LOCAL);
	}

	@Override
	public String getValue() {
		return (String) get(Key.VALUE);
	}

	@Override
	public String getidType() {
		return (String) get(Key.IDTYPE);
	}

	public void setType(String type) {
		put(Key.TYPE, type);
	}

	public void setLocal(boolean local) {
		put(Key.LOCAL, local);
	}

	public void setValue(String value) {
		put(Key.VALUE, value);
	}

	public void setIdType(String idType) {
		put(Key.IDTYPE, idType);
	}

}
