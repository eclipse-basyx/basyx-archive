package org.eclipse.basyx.submodel.metamodel.map.reference;

import java.util.HashMap;

import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.facade.reference.KeyFacade;

/**
 * Key as defined in DAAS document <br/>
 * <br/>
 * A key is a reference to an element by its id.
 * 
 * @author schnicke
 *
 */
public class Key extends HashMap<String, Object> implements IKey {
	private static final long serialVersionUID = 1L;

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

	@Override
	public String getType() {
		return new KeyFacade(this).getType();
	}

	@Override
	public boolean isLocal() {
		return new KeyFacade(this).isLocal();
	}

	@Override
	public String getValue() {
		return new KeyFacade(this).getValue();
	}

	@Override
	public String getidType() {
		return new KeyFacade(this).getidType();
	}

	public void setType(String type) {
		new KeyFacade(this).setType(type);

	}

	public void setLocal(boolean local) {
		new KeyFacade(this).setLocal(local);

	}

	public void setValue(String value) {
		new KeyFacade(this).setValue(value);

	}

	public void setIdType(String idType) {
		new KeyFacade(this).setIdType(idType);

	}

}
