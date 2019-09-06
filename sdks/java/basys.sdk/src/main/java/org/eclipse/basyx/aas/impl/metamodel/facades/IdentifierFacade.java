package org.eclipse.basyx.aas.impl.metamodel.facades;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.identifier.Identifier;

/**
 * Facade providing access to a map containing the Identifier structure
 * @author rajashek
 *
 */
public class IdentifierFacade implements IIdentifier {
	
	private Map<String, Object> map;

	public IdentifierFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@Override
	public String getIdType() {
		return (String) map.get(Identifier.IDTYPE);
	}

	@Override
	public void setIdType(String newValue) {
	map.put(Identifier.IDTYPE, newValue);
		
	}

	@Override
	public String getId() {
		return (String) map.get(Identifier.ID);
	}

	@Override
	public void setId(String newValue) {
		map.put(Identifier.ID, newValue);
		
	}

}
