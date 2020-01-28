package org.eclipse.basyx.submodel.metamodel.map.identifier;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * Identification class
 * 
 * @author kuhn, schnicke
 *
 */
public class Identifier extends VABModelMap<Object> implements IIdentifier {

	public static final String IDTYPE = "idType";
	public static final String ID = "id";

	/**
	 * Constructor
	 */
	public Identifier() {
		// Default values
		put(IDTYPE, IdentifierType.IRDI.toString());
		put(ID, "");
	}

	/**
	 * Creates a Identifier object from a map
	 * 
	 * @param obj
	 *            a Identifier object as raw map
	 * @return a Identifier object, that behaves like a facade for the given map
	 */
	public static Identifier createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		Identifier ret = new Identifier();
		ret.setMap(map);
		return ret;
	}

	/**
	 * Constructor that accepts parameter
	 */
	public Identifier(IdentifierType idType, String id) {
		// Load values
		if (idType == null) {
			put(IDTYPE, null);
		} else {
			put(IDTYPE, idType.toString());
		}
		put(ID, id);
	}

	@Override
	public IdentifierType getIdType() {
		return IdentifierType.fromString((String) get(Identifier.IDTYPE));
	}

	public void setIdType(IdentifierType newValue) {
		put(Identifier.IDTYPE, newValue.toString());
	}

	@Override
	public String getId() {
		return (String) get(Identifier.ID);
	}

	public void setId(String newValue) {
		put(Identifier.ID, newValue);
	}
}
