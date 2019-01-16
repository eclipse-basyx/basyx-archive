package org.eclipse.basyx.aas.metamodel.hashmap.aas.parts;

import java.util.HashMap;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasDataSpecification;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.haskind.HasKind;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;

/**
 * Asset class as described in DAAS document<br/>
 * An Asset describes meta data of an asset that is represented by an AAS. <br/>
 * The asset may either represent an asset type or an asset instance.<br/>
 * The asset has a globally unique identifier plus – if needed – additional
 * domain specific (proprietary) identifiers.
 * 
 * @author kuhn, elsheikh, schnicke
 *
 */
public class Asset extends HashMap<String, Object> {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public Asset() {
		// Add qualifiers
		putAll(new HasDataSpecification());
		putAll(new HasKind());
		putAll(new Identifiable());

		// Default values
		put("assetIdentificationModel", null);
	}

	/**
	 * 
	 * @param submodel
	 *            A reference to a Submodel that defines the handling of additional
	 *            domain specific (proprietary) Identifiers for the asset like e.g.
	 *            serial number etc.
	 */
	public Asset(Reference submodel) {
		this();
		put("assetIdentificationModel", submodel);
	}
}
