package org.eclipse.basyx.aas.metamodel.map.descriptor;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

/**
 * CustomId identifier
 * 
 * @author schnicke
 *
 */
public class CustomId extends Identifier {

	/**
	 * Creates a new Identifier with IdentifierType == IdentifierType.CUSTOM
	 * 
	 * @param id
	 */
	public CustomId(String id) {
		super(IdentifierType.CUSTOM, id);
	}
}
