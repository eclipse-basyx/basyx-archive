package org.eclipse.basyx.aas.impl.provider.filesystem.filesystem;

/**
 * Represents a generic file which is either data or a directory
 * @author schnicke
 *
 */
public class File {
	private String name;
	private FileType type;

	public File(String name, FileType type) {
		super();
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public FileType getType() {
		return type;
	}

}
