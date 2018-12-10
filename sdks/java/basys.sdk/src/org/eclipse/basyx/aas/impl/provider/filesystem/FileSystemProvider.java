package org.eclipse.basyx.aas.impl.provider.filesystem;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.basyx.aas.api.exception.FeatureNotImplementedException;
import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.ICollectionProperty;
import org.eclipse.basyx.aas.api.resources.IContainerProperty;
import org.eclipse.basyx.aas.api.resources.IMapProperty;
import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.eclipse.basyx.aas.impl.provider.filesystem.filesystem.File;
import org.eclipse.basyx.aas.impl.provider.filesystem.filesystem.FileSystem;
import org.eclipse.basyx.aas.impl.provider.filesystem.filesystem.FileType;
import org.eclipse.basyx.aas.impl.tools.BaSysID;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.json.JSONObject;

/**
 * Provides models based on a generic file system
 * 
 * @author schnicke
 *
 */
public class FileSystemProvider implements IModelProvider {

	private FileSystem fileSystem;
	protected static final String DATA = "/data.json";
	private final String rootDir;
	private final String metaDir;
	private final String metaId;

	private final String CONTAINER = "container";
	private final String SINGLE = "single";
	private final String MAP = "map";
	private final String COLLECTION = "collection";

	public FileSystemProvider(FileSystem fileSystem, String rootDir) throws Exception {
		this.fileSystem = fileSystem;
		this.rootDir = rootDir;

		// Create meta files if they do not already exist
		metaDir = rootDir + "/" + "_meta";
		metaId = metaDir + "/idMap";
		initMapIfNull(metaId);
	}

	private void initMapIfNull(String path) throws Exception {
		String map = null;
		try {
			map = fileSystem.readFile(path + DATA);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		if (map == null) {
			createDirectory(path);
			writeObject(path, new HashMap<String, String>());
		}
	}

	protected FileSystem getFileSystem() {
		return fileSystem;
	}

	protected String getRootDir() {
		return rootDir;
	}

	protected String getFolderPath(String address) throws Exception {
		return getFolderPath(getRootDir(), address);
	}

	protected String getFolderPath(String root, String address) throws Exception {
		String smId = BaSysID.instance.getSubmodelID(address);
		if (BaSysID.instance.getAASID(address).isEmpty() && !smId.isEmpty() && getSubModelPath(smId) != null) {
			String subModelPath = getSubModelPath(smId);
			String servicePath = getQualifierPath(address);
			return subModelPath + "/" + servicePath;
		} else {
			return root + "/" + BaSysID.instance.getUnScopedServicePath(address);
		}
	}

	private String getQualifierPath(String address) {
		if (address.contains("properties")) {
			return address.substring(address.indexOf("properties"));
		} else if (address.contains("operations")) {
			return address.substring(address.indexOf("operations"));
		} else if (address.contains("events")) {
			return address.substring(address.indexOf("evens"));
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	private String getSubModelPath(String smId) throws Exception {
		Map<String, String> map = (Map<String, String>) readObject(metaDir + "/idMap");
		return map.get(smId);
	}

	@SuppressWarnings("unchecked")
	private void setSubModelPath(String smId, String path) throws Exception {
		String metaIdPath = metaDir + "/idMap";
		Map<String, String> map = (Map<String, String>) readObject(metaIdPath);
		String p = getRootDir() + "/" + path;
		map.put(smId, p);
		writeObject(metaIdPath, map);
	}

	@SuppressWarnings("unchecked")
	private void registerProperty(String path, IProperty prop) throws Exception {
		String metaProperty = getFolderPath(path);
		initMapIfNull(metaProperty);
		Map<String, String> map = (Map<String, String>) readObject(metaProperty);

		String propType = null;
		if (prop instanceof ISingleProperty) {
			propType = SINGLE;
		} else if (prop instanceof ICollectionProperty) {
			propType = COLLECTION;
		} else if (prop instanceof IMapProperty) {
			propType = MAP;
		} else if (prop instanceof IContainerProperty) {
			propType = CONTAINER;
		} else {
			throw new RuntimeException("Unrecognized property type " + prop);
		}
		map.put(prop.getId(), propType);

		writeObject(metaProperty, map);

	}

	@SuppressWarnings("unchecked")
	private String getPropertyType(String path, String name) throws Exception {
		String metaProperty = getFolderPath(path);
		Map<String, String> map = (Map<String, String>) readObject(metaProperty);
		return map.get(name);
	}

	private String replacePath(String fileName, String path) {
		return fileName.replace(path + "/", "");
	}

	@Override
	public Object getModelPropertyValue(String address) {
		try {
			String path = getFolderPath(address).replace(".", "/");
			if (path.endsWith("/operations") || path.endsWith("/properties") || path.endsWith("/events")
					|| path.endsWith("/submodels")) {
				List<File> files;
				try {
					files = fileSystem.readDirectory(path);
					List<String> directories = files.stream().filter(f -> f.getType() == FileType.DIRECTORY)
							.map(f -> replacePath(f.getName(), path)).collect(Collectors.toList());
					Map<String, IElementReference> refMap = new HashMap<>();
					for (String s : directories) {
						if (path.endsWith("/operations") || path.endsWith("/properties") || path.endsWith("/events")) {
							String propPath;
							if (address.contains(".")) {
								propPath = address.substring(address.lastIndexOf("/") + 1, address.indexOf(".") + 1)
										+ "properties." + s;
							} else {
								propPath = s;
							}
							ElementRef ref = new ElementRef(BaSysID.instance.getAASID(address),
									BaSysID.instance.getSubmodelID(address), propPath);
							if (path.endsWith("/properties")) {
								String type = getPropertyType(address, s);
								ref.setKind(type);
							}
							refMap.put(s, ref);
						} else if (path.endsWith("/submodels")) {
							refMap.put(s, new ElementRef(BaSysID.instance.getAASID(address), s, ""));
						}
					}
					return refMap;
				} catch (Exception e) {
					e.printStackTrace();
					return new HashMap<>();
				}
			} else {
				try {
					return readObject(path);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void setModelPropertyValue(String address, Object newValue) throws Exception {
		String path = getFolderPath(address);
		writeObject(path, newValue);
	}

	@Override
	public void createValue(String address, Object newEntity) throws Exception {
		// TODO: Create super class containing generic creation
		if (newEntity instanceof IAssetAdministrationShell) {
			IAssetAdministrationShell shell = (IAssetAdministrationShell) newEntity;
			for (String key : shell.getSubModels().keySet()) {
				createValue(shell.getId() + "/aas/submodels", shell.getSubModels().get(key));
			}
		} else if (newEntity instanceof ISubModel) {
			ISubModel sm = (ISubModel) newEntity;
			String subModelPath = address + "/" + sm.getId();
			setSubModelPath(sm.getId(), subModelPath);
			fileSystem.createDirectory(getFolderPath(subModelPath) + "/properties");
			fileSystem.createDirectory(getFolderPath(subModelPath) + "/operations");
			fileSystem.createDirectory(getFolderPath(subModelPath) + "/events");
			for (String key : sm.getProperties().keySet()) {
				createValue(address + "/" + sm.getId() + "/properties", sm.getProperties().get(key));
			}
		} else if (newEntity instanceof IProperty) {
			String newAddress;
			// Check if a property is created inside a nested property
			if (isNestedProperty(address)) {
				newAddress = address + "." + ((IProperty) newEntity).getId();
			} else {
				newAddress = address + "/" + ((IProperty) newEntity).getId();
			}

			if (newEntity instanceof ISingleProperty) {
				ISingleProperty prop = (ISingleProperty) newEntity;
				createValue(newAddress, prop.get());
			} else if (newEntity instanceof ICollectionProperty) {
				createValue(newAddress, ((ICollectionProperty) newEntity).getElements());
			} else if (newEntity instanceof IMapProperty) {
				// Copy map since there is no getter for the contained map
				Map<String, Object> map = new HashMap<>();
				IMapProperty mapProp = (IMapProperty) newEntity;
				for (String key : mapProp.getKeys()) {
					map.put(key, mapProp.getValue(key));
				}
				createValue(newAddress, map);
			} else if (newEntity instanceof IContainerProperty) {
				IContainerProperty container = (IContainerProperty) newEntity;
				for (String key : container.getProperties().keySet()) {
					createValue(newAddress + "/properties", container.getProperties().get(key));
				}
			} else {
				throw new RuntimeException("Unknown property " + newEntity);
			}
			registerProperty(address, (IProperty) newEntity);
		} else {
			String path = getFolderPath(address);
			createDirectory(path);
			setModelPropertyValue(path, newEntity);
		}
	}

	// TODO: Move this to BaSysID
	private boolean isNestedProperty(String path) {
		int index = path.indexOf("/properties");
		return path.length() > index + "/properties".length();
	}

	@Override
	public void deleteValue(String address) throws Exception {
		String path = getFolderPath(address);
		fileSystem.deleteDirectory(path);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteValue(String address, Object obj) throws Exception {
		String path = getFolderPath(address);
		Object o = readObject(path);
		if (o instanceof Collection<?>) {
			Collection<Object> c = (Collection<Object>) ((Collection<?>) o);
			c.remove(obj);
			writeObject(path, c);
		}
	}

	@Override
	public Object invokeOperation(String path, Object[] parameter) throws Exception {
		// TODO: Support script languages?
		throw new FeatureNotImplementedException();
	}

	@Override
	public Map<String, IElementReference> getContainedElements(String path) {
		// TODO: Implement!
		throw new FeatureNotImplementedException();
	}

	private void createDirectory(String path) throws Exception {
		fileSystem.createDirectory(pathFilter(path));
	}

	private String pathFilter(String path) {
		// Replace "." with "/" to ensure that a nested property's properties are
		// contained in a common folder
		return path.replace(".", "/");
	}

	private Object readObject(String path) throws Exception {
		String readPath = pathFilter(path) + DATA;

		String serialized = fileSystem.readFile(readPath);
		if (serialized == null) {
			return null;
		} else {
			return JSONTools.Instance.deserialize(new JSONObject(serialized));
		}
	}

	private void writeObject(String path, Object o) throws Exception {
		String writePath = pathFilter(path) + DATA;
		fileSystem.writeFile(writePath, JSONTools.Instance.serialize(o).toString());
	}

}
