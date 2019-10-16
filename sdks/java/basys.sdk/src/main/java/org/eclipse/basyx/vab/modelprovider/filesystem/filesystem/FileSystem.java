package org.eclipse.basyx.vab.modelprovider.filesystem.filesystem;

import java.util.List;

/**
 * Abstracts from a generic file system
 * @author schnicke
 *
 */
public interface FileSystem {
	
	public String readFile(String path) throws Exception;

	public void writeFile(String path, String content) throws Exception;

	public void deleteFile(String path) throws Exception;

	public void createDirectory(String path) throws Exception;

	public List<File> readDirectory(String path) throws Exception;
	
	public void deleteDirectory(String path) throws Exception;
	
	public FileType getType(String path) throws Exception;
}
