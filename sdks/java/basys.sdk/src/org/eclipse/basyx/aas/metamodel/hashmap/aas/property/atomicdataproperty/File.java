package org.eclipse.basyx.aas.metamodel.hashmap.aas.property.atomicdataproperty;

/**
 * 
 * @author pschorn
 *
 */
public class File extends AtomicDataProperty {
	private static final long serialVersionUID = 1L;

	public File() {
		super();

		// Default value
		put("fileName", null);
		put("path", null);
	}

	public File(String fileName, String path) {
		super();

		// Save value
		put("fileName", fileName);
		put("path", path);
	}
}
