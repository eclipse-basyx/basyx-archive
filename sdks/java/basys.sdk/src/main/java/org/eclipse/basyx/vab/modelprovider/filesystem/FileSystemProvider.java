package org.eclipse.basyx.vab.modelprovider.filesystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.vab.coder.json.serialization.DefaultTypeFactory;
import org.eclipse.basyx.vab.coder.json.serialization.GSONTools;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.modelprovider.filesystem.filesystem.File;
import org.eclipse.basyx.vab.modelprovider.filesystem.filesystem.FileSystem;
import org.eclipse.basyx.vab.modelprovider.filesystem.filesystem.FileType;
import org.eclipse.basyx.vab.modelprovider.list.InvalidListReferenceException;

/**
 * Provides models based on a generic file system
 * 
 * @author schnicke, elsheikh, conradi
 *
 */
public class FileSystemProvider implements IModelProvider {

	private final FileSystem fileSystem;
	private final String rootDir;

	private final String collectionElemPrefix = "byRef_";
	private final String metaFileName = "_meta";
	private final String referenceFileName = "references";
	private final String regexCollectionElem = collectionElemPrefix + "(0|[1-9][0-9]*)";

	private final GSONTools tools = new GSONTools(new DefaultTypeFactory());

	/**
	 * Constructor which takes a file system and a root directory
	 * Removes the last '/' from the passed root directory if it exists
	 * Creates the root directory folder
	 */
	public FileSystemProvider(FileSystem fileSystem, String rootDir) throws Exception {
		this.fileSystem = fileSystem;
		this.rootDir = unifyPath(rootDir);

		fileSystem.createDirectory(rootDir + "/");
	}

	public FileSystemProvider(FileSystem fileSystem, String rootDir, Map<String, Object> VABelement) throws Exception {
		this.fileSystem = fileSystem;
		this.rootDir = unifyPath(rootDir);

		fileSystem.createDirectory(rootDir + "/");
		fromMapToDirectory("", VABelement);
	}

	/**
	 * Same constructor as the above one, only gets an additional boolean argument
	 * doEmptyDirectory which specifies whether to empty the root directory or not
	 */
	public FileSystemProvider(FileSystem fileSystem, String rootDir, Map<String, Object> VABelement,
			boolean doEmptyDirectory) throws Exception {
		this.fileSystem = fileSystem;
		this.rootDir = unifyPath(rootDir);

		fileSystem.createDirectory(rootDir + "/");
		if (doEmptyDirectory)
			fileSystem.deleteDirectory(rootDir);
		fromMapToDirectory("", VABelement);
	}

	/**
	 * Removes the first and last character from a String if it is a "/"
	 */
	private String unifyPath(String path) {
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		return path;
	}

	private String constructCollectionRefPath(String path, int ref) {
		return path + "/" + collectionElemPrefix + ref;
	}

	/**
	 * Reads the __meta file present in the specified directory. Returns null if the
	 * __meta file does not exist. Works only without "/" at the end
	 */
	@SuppressWarnings("unchecked")
	private HashSet<String> readMetaFile(String path) throws Exception {
		path = path.equals("") ? rootDir + "/" + metaFileName : rootDir + "/" + path + "/" + metaFileName;
		if (fileSystem.getType(path) == FileType.DATA) {
			Object deserialized = loadAndDeserialize(path);
			// Especially for "[]", deserialization can not differentiate between lists and sets
			if (deserialized instanceof HashSet) {
				return (HashSet<String>) deserialized;
			} else if (deserialized instanceof Collection) {
				return new HashSet<>((List<String>) deserialized);
			}
		}
		return null;
	}

	/**
	 * Reads the object in the relative path specified
	 */
	private Object read(String path) throws Exception {
		String directory = VABPathTools.getParentPath(path);
		String fileName = VABPathTools.getLastElement(path);

		String fullDirPath = rootDir + "/" + directory;

		if (fileSystem.getType(fullDirPath) == FileType.DIRECTORY) {
			List<File> directoryFiles = fileSystem.readDirectory(fullDirPath);
			// The directory that contains the file, folder or collection to be read does
			// not exist, return null

			// Get the list of collections that are present as folders from the _meta file
			HashSet<String> collections = readMetaFile(directory);
			if (collections != null && collections.contains(fileName)) {
				// It's a collection
				return readCollection(path);
			} else {

				File file = findFileInList(directoryFiles, fileName);
				if (file != null) {
					if (file.getType() == FileType.DATA) {
						// It's a file
						return loadAndDeserialize(file.getName());
					} else {
						// It's a folder
						return readDirectory(path);
					}
				} else if (fileName.matches(regexCollectionElem)) {
					// We wanted to read an element of a collection. The element does not exist,
					// throw an Invalid List Reference Exception
					throw new InvalidListReferenceException(
							Integer.parseInt(fileName.substring(collectionElemPrefix.length())));
				}
			}
		}
		return null;
	}

	private File findFileInList(List<File> files, String fileName) {
		if (fileName.equals("")) {
			// The wanted File is the root
			return new File(rootDir, FileType.DIRECTORY);
		}
		for (File file : files) {
			String currentFileName = VABPathTools.getLastElement(file.getName());
			if (currentFileName.equals(fileName)) {
				return file;
			}
		}
		return null;
	}

	private Collection<Object> readCollection(String path) throws Exception {
		Collection<Object> c = new ArrayList<Object>();
		String fullPath = rootDir + "/" + path;
		for (int ref : readReferences(fullPath)) {
			FileType type = fileSystem.getType(constructCollectionRefPath(fullPath, ref));

			if (type == FileType.DATA) {
				c.add(loadAndDeserialize(constructCollectionRefPath(fullPath, ref)));
			} else if (type == FileType.DIRECTORY) {
				c.add(readDirectory(constructCollectionRefPath(path, ref)));
			}

		}
		return c;
	}

	/**
	 * Reads the folder in the relative path specified
	 */
	private HashMap<String, Object> readDirectory(String path) throws Exception {
		String fullPath = rootDir + "/" + path;
		HashMap<String, Object> returnData = new HashMap<String, Object>();
		HashSet<String> collections = readMetaFile(path);

		List<File> directoryFiles = fileSystem.readDirectory(fullPath);
		removeMetaFile(directoryFiles);

		for (File file : directoryFiles) {
			String currentFilePath = file.getName();
			String fileName = VABPathTools.getLastElement(currentFilePath);
			if (file.getType() == FileType.DATA) {
				// It's a file
				returnData.put(fileName, loadAndDeserialize(currentFilePath));
			} else if (collections != null && collections.contains(fileName)) {
				// It's a collection
				returnData.put(fileName, readCollection(stripRootDir(currentFilePath)));
			} else {
				// It's a folder
				returnData.put(fileName, readDirectory(stripRootDir(currentFilePath)));
			}
		}

		return returnData;
	}

	private List<File> removeMetaFile(List<File> list) {
		for (int i = 0; i < list.size(); i++) {
			if (VABPathTools.getLastElement(list.get(i).getName()).equals(metaFileName)) {
				list.remove(i);
				break;
			}
		}
		return list;
	}

	private String stripRootDir(String path) {
		return path.substring(rootDir.length() + 1);
	}

	/**
	 * Adds collection to the __meta file present in directoryPath
	 * Works whether "/" is at the end of path or not
	 */
	private void addCollectionToMetaFile(String directoryPath, String collectionName) throws Exception {
		HashSet<String> collections = readMetaFile(directoryPath);

		if (collections != null) {
			collections.add(collectionName);
		} else {
			collections = new HashSet<String>(Arrays.asList(new String[] { collectionName }));
		}

		serializeAndSave(rootDir + "/" + directoryPath + "/" + metaFileName, collections);
	}

	/**
	 * Serializes and writes the object in the path specified. The path is relative to the RootDir
	 * If the write folder is not present, it is created
	 * If the object is an array or a collection, a collection is written in a folder
	 */
	@SuppressWarnings("unchecked")
	private void writeObject(String path, Object o) throws Exception {
		path = unifyPath(path);
		String directory = VABPathTools.getParentPath(path);
		String fullPath = rootDir + "/" + path;
		Collection<?> collection = null;

		if (o instanceof Collection<?>) {
			collection = (Collection<?>) o;
		}

		if (collection != null) {
			// It's a collection given as an Array or Collection instance
			addCollectionToMetaFile(directory, VABPathTools.getLastElement(path));
			fileSystem.createDirectory(fullPath);
			Iterator<?> iterator = collection.iterator();
			List<Integer> references = new ArrayList<>();

			for (int counter = 0; iterator.hasNext(); counter++) {
				Object item = iterator.next();
				references.add(counter);
				if (item instanceof Map) {
					fileSystem.createDirectory(constructCollectionRefPath(fullPath, counter));
					fromMapToDirectory(constructCollectionRefPath(path, counter), (Map<String, Object>) item);
				} else {
					serializeAndSave(constructCollectionRefPath(fullPath, counter), item);
				}
			}

			writeReferences(fullPath, references);
		} else {
			// Otherwise, it's an Object
			fileSystem.createDirectory(rootDir + "/" + directory);
			serializeAndSave(fullPath, o);
		}
	}

	/**
	 * Mirrors a Map<String, Object> folder structure in the specified relative path
	 * Works whether "/" is at the end of path or not
	 * Does not create the directory "path"
	 */
	@SuppressWarnings("unchecked")
	private void fromMapToDirectory(String path, Map<String, Object> map) throws Exception {
		path = unifyPath(path);
		String fullPath = rootDir + "/" + path;
		fileSystem.createDirectory(fullPath);

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() instanceof Map)
				fromMapToDirectory(path + "/" + entry.getKey(), (Map<String, Object>) entry.getValue());
			else
				writeObject(path + "/" + entry.getKey(), entry.getValue());
		}
	}

	private void writeReferences(String path, List<Integer> ref) throws Exception {
		serializeAndSave(path + "/" + referenceFileName, ref);
	}

	@SuppressWarnings("unchecked")
	private List<Integer> readReferences(String path) throws Exception {
		return (List<Integer>) loadAndDeserialize(path + "/" + referenceFileName);
	}

	private Object loadAndDeserialize(String path) throws Exception {
		String serialized = fileSystem.readFile(path);
		return tools.deserialize(serialized);
	}

	private void serializeAndSave(String path, Object o) throws Exception {
		fileSystem.writeFile(path, tools.serialize(o));
	}

	@Override
	public synchronized Object getModelPropertyValue(String path) throws Exception {
		return read(unifyPath(path));
	}

	/**
	 * Sets the file, folder, or collection at the specified path to newValue
	 * Only works if the types match (i.e. file ↔ file, folder ↔ folder, etc...)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public synchronized void setModelPropertyValue(String path, Object newValue) throws Exception {
		path = unifyPath(path);
		String fileName = VABPathTools.getLastElement(path);
		String fullPath = rootDir + "/" + path;
		HashSet<String> collections = readMetaFile(VABPathTools.getParentPath(path));
		FileType type = fileSystem.getType(fullPath);

		if (type == FileType.DATA) {
			// File with the same name exists, replace it with newValue >IF< newValue is
			// neither a folder nor a Map
			if (!(newValue instanceof Map) && !(newValue instanceof Collection<?>)) {
				serializeAndSave(fullPath, newValue);
			}
		} else if (type == FileType.DIRECTORY) {
			if ((collections == null || !collections.contains(fileName)) && newValue instanceof Map) {
				fileSystem.deleteDirectory(fullPath);
				fromMapToDirectory(path, (Map<String, Object>) newValue);
			} else {
				fileSystem.deleteDirectory(fullPath);
				writeObject(path, newValue);
			}
		}
	}

	/**
	 * Creates newEntity at the specified path
	 * If a collection exists at the specified path, add newEntity to it >IF< newEntity is not a collection
	 */
	@Override
	@SuppressWarnings("unchecked")
	public synchronized void createValue(String path, Object newEntity) throws Exception {
		path = unifyPath(path);
		String directory = VABPathTools.getParentPath(path);
		String fileName = VABPathTools.getLastElement(path);
		fileSystem.createDirectory(rootDir + "/" + directory);
		String fullPath = rootDir + "/" + path;
		FileType type = fileSystem.getType(fullPath);

		if (type == FileType.DATA) {
			return; // A file with this name already exists, quit the method
		} else if (type == FileType.DIRECTORY) {
			HashSet<String> collections = readMetaFile(directory);

			if (collections != null && collections.contains(fileName)) { // A Collection already exists

				List<Integer> references = readReferences(fullPath);

				// Get maximum reference to be able to add an additional entry
				int max = 0;
				for (Integer i : references) {
					if (i > max) {
						max = i;
					}
				}

				// Add the entry to the collection
				if (newEntity instanceof Map) {
					fromMapToDirectory(constructCollectionRefPath(path, max + 1), (Map<String, Object>) newEntity);
				} else if (!(newEntity instanceof Collection<?>)) {
					// If the new Object is a Collection, don't add it to the existing one
					serializeAndSave(constructCollectionRefPath(fullPath, max + 1), newEntity);
				}

				references.add(max + 1);
				writeReferences(fullPath, references);
			}
		} else if (type == null) { // The Object doesn't exist and has to be created
			if (newEntity instanceof Map) {
				fromMapToDirectory(path, (Map<String, Object>) newEntity);
			} else {
				writeObject(path, newEntity);
			}
		}
	}

	/**
	 * Deletes the Object, folder or collection at the specified path
	 * If it is a collection, remove its name from the meta file of
	 * the folder that contains the collection
	 */
	@Override
	public synchronized void deleteValue(String path) throws Exception {
		path = unifyPath(path);
		String directory = VABPathTools.getParentPath(path);
		String fileName = VABPathTools.getLastElement(path);

		String fullDirPath = rootDir + "/" + directory;
		String fullPath = rootDir + "/" + path;
		HashSet<String> collections = readMetaFile(directory);

		FileType type = fileSystem.getType(fullPath);

		if (type == FileType.DATA) {
			fileSystem.deleteFile(fullPath);
			if (fileName.matches(regexCollectionElem)) {
				// The deleted file was an element of a collection (It is named "byRef_*")
				int deletedElementIndex = Integer.parseInt(fileName.substring(collectionElemPrefix.length()));
				List<Integer> references = readReferences(fullDirPath);
				references.remove(Integer.valueOf(deletedElementIndex));
				writeReferences(fullDirPath, references);
			}
		} else if (type == FileType.DIRECTORY) {
			if (collections != null && collections.contains(fileName)) {
				// The folder to delete is a collection
				collections.remove(fileName);
				serializeAndSave(fullDirPath + "/" + metaFileName, collections);
			}
			fileSystem.deleteDirectory(fullPath);
		}

		// If no file, folder or collection exists at the path specified,
		// the method will effectively do nothing (delete nothing)
	}

	/**
	 * Deletes the Object or Map that is equal to obj from the collection
	 * in the specified path
	 * Otherwise, doesn't do anything
	 */
	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		path = unifyPath(path);
		String directory = VABPathTools.getParentPath(path);
		String fileName = VABPathTools.getLastElement(path);

		String fullCollectionPath = rootDir + "/" + path;
		HashSet<String> collections = readMetaFile(directory);

		FileType type = fileSystem.getType(rootDir + "/" + directory);
		if (collections != null && type == FileType.DIRECTORY && collections.contains(fileName)) {
			// Collection in specified path exists

			List<Integer> references = readReferences(fullCollectionPath);

			for (int i = 0; i < references.size(); i++) {
				int j = references.get(i);
				String currentPath = constructCollectionRefPath(fullCollectionPath, j);
				type = fileSystem.getType(currentPath);

				// If the File exists it's a Java Object, else it's a Map
				if (type == FileType.DATA) {
					Object o = loadAndDeserialize(currentPath);
					if (o.equals(obj)) {
						fileSystem.deleteFile(currentPath);
						references.remove(Integer.valueOf(j));
						break;
					}
				} else if (type == FileType.DIRECTORY) {
					Object o = readDirectory(constructCollectionRefPath(path, j));
					if (o.equals(obj)) {
						fileSystem.deleteDirectory(currentPath);
						references.remove(Integer.valueOf(j));
						break;
					}
				}
			}
			writeReferences(fullCollectionPath, references);
		}
	}

	@Override
	public Object invokeOperation(String path, Object... parameter) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}