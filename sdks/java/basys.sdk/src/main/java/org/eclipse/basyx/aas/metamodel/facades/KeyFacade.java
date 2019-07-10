package org.eclipse.basyx.aas.metamodel.facades;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.reference.IKey;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Key;
/**
 * Facade providing access to a map containing the Key structure
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
	return (String)map.get(Key.TYPE);
	}
	@Override
	public boolean isLocal() {
		return (boolean)map.get(Key.LOCAL);
	}
	@Override
	public String getValue() {
		return (String)map.get(Key.VALUE);
	}
	@Override
	public String getidType() {
		return (String)map.get(Key.IDTYPE);
	}
	@Override
	public void setType(String type) {
		map.put(Key.TYPE,type);
	}
	@Override
	public void setLocal(boolean local) {
		map.put(Key.LOCAL, local);
		
	}
	@Override
	public void setValue(String value) {
		map.put(Key.VALUE,value );
	}
	@Override
	public void setIdType(String idType) {
		map.put(Key.IDTYPE, idType);
	}



}
