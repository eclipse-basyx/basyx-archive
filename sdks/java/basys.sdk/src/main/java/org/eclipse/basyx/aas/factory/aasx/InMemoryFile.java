package org.eclipse.basyx.aas.factory.aasx;

/**
 * Container class for the content of a File and its Path
 * 
 * @author conradi
 *
 */
public class InMemoryFile {

	private byte[] fileContent;
	private String path;
	
	public InMemoryFile(byte[] fileContent, String path) {
		this.fileContent = fileContent;
		this.path = path;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public String getPath() {
		return path;
	}
}
