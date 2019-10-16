package org.eclipse.basyx.submodel.metamodel.facade.reference;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;

/**
 * Facade providing access to a map containing the Key structure
 * 
 * @author rajashek
 *
 */
public class KeyFacade implements IKey {
	private Map<String, Object> map;

	public KeyFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@Override
	public String getType() {
		return (String) map.get(Key.TYPE);
	}

	@Override
	public boolean isLocal() {
		return (boolean) map.get(Key.LOCAL);
	}

	@Override
	public String getValue() {
		return (String) map.get(Key.VALUE);
	}

	@Override
	public String getidType() {
		return (String) map.get(Key.IDTYPE);
	}

	public void setType(String type) {
		map.put(Key.TYPE, type);
	}

	public void setLocal(boolean local) {
		map.put(Key.LOCAL, local);

	}

	public void setValue(String value) {
		map.put(Key.VALUE, value);
	}

	public void setIdType(String idType) {
		map.put(Key.IDTYPE, idType);
	}

}
