package org.eclipse.basyx.submodel.metamodel.facade.identifier;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

/**
 * Facade providing access to a map containing the Identifier structure
 * 
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

	public void setIdType(String newValue) {
		map.put(Identifier.IDTYPE, newValue);
	}

	@Override
	public String getId() {
		return (String) map.get(Identifier.ID);
	}

	public void setId(String newValue) {
		map.put(Identifier.ID, newValue);
	}

}
