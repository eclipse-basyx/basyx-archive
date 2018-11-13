package org.eclipse.basyx.aas.metamodel.hashmap.aas.property.atomicdataproperty;

/**
 * 
 * @author pschorn
 *
 */
public class BLOB extends AtomicDataProperty {
	private static final long serialVersionUID = 1L;

	public BLOB() {
		super();

		// Default value
		put("value", null);
	}

	public BLOB(Byte[] value) {
		super();

		// Save value
		put("value", value);
	}
}
