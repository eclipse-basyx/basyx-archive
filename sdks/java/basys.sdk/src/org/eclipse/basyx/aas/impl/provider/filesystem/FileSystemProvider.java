package org.eclipse.basyx.aas.impl.provider.filesystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.basyx.aas.api.exception.FeatureNotImplementedException;
import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ICollectionProperty;
import org.eclipse.basyx.aas.api.resources.basic.IMapProperty;
import org.eclipse.basyx.aas.api.resources.basic.IProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.eclipse.basyx.aas.impl.provider.AbstractModelScopeProvider;
import org.eclipse.basyx.aas.impl.provider.filesystem.filesystem.File;
import org.eclipse.basyx.aas.impl.provider.filesystem.filesystem.FileSystem;
import org.eclipse.basyx.aas.impl.provider.filesystem.filesystem.FileType;
import org.eclipse.basyx.aas.impl.resources.basic.PropertyContainer;
import org.eclipse.basyx.aas.impl.tools.BaSysID;
import org.json.JSONObject;

/**
 * Provides models based on a generic file system
 * 
 * @author schnicke
 *
 */
public class FileSystemProvider extends AbstractModelScopeProvider {

	private FileSystem fileSystem;
	protected static final String DATA = "/data";
	private final String rootDir;
	private final String metaDir;
	private final String metaId;

	public FileSystemProvider(FileSystem fileSystem, String rootDir) throws Exception {
		this.fileSystem = fileSystem;
		this.rootDir = rootDir;

		// Create meta files if they do not already exist
		metaDir = rootDir + "/" + "_meta";
		metaId = metaDir + "/idMap";
		String idMap = null;
		try {
			idMap = fileSystem.readFile(metaId + DATA);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		if (idMap == null || idMap.isEmpty()) {
			createDirectory(metaId);
			writeObject(metaId, new HashMap<String, String>());
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
		if (!smId.isEmpty() && getSubModelPath(smId) != null) {
			String path = BaSysID.instance.getPath(address);
			String add = "";
			// Readd properties
			// TODO: Create method in BaSysID to return the path after <ID>
			if (address.contains("properties")) {
				add = "properties/";
			}
			return getSubModelPath(smId) + "/" + add + path;
		} else {
			return root + "/" + BaSysID.instance.getUnScopedServicePath(address);
		}
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

	@Override
	public Object getModelPropertyValue(String address) {
		try {
			String path = getFolderPath(address);
			if (path.endsWith("/operations") || path.endsWith("/properties") || path.endsWith("/events") || path.endsWith("/submodels")) {
				List<File> files;
				try {
					files = fileSystem.readDirectory(path);
					List<String> directories = files.stream().filter(f -> f.getType() == FileType.DIRECTORY).map(f -> f.getName()).collect(Collectors.toList());
					return directories;
				} catch (Exception e) {
					e.printStackTrace();
					return new ArrayList<String>();
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
			setSubModelPath(sm.getId(), address + "/" + sm.getId());
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
				Map<Object, Object> map = new HashMap<>();
				IMapProperty mapProp = (IMapProperty) newEntity;
				for (Object key : mapProp.getKeys()) {
					map.put(key, mapProp.getValue(key));
				}
				createValue(newAddress, map);
			} else if (newEntity instanceof PropertyContainer) {
				PropertyContainer container = (PropertyContainer) newEntity;
				for (String key : container.getProperties().keySet()) {
					createValue(newAddress, container.getProperties().get(key));
				}
			} else {
				throw new RuntimeException("Unknown property " + newEntity);
			}
		} else {
			String path = getFolderPath(address);
			createDirectory(path);
			setModelPropertyValue(path, newEntity);
		}
	}

	private boolean isNestedProperty(String path) {
		int index = path.indexOf("/properties");
		return path.length() > index + "/properties".length();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setModelPropertyValue(String address, Object... newEntry) throws Exception {
		String path = getFolderPath(address);
		Object obj = readObject(path);
		if (obj == null) {
			obj = new ArrayList<Object>();
		}

		if (obj instanceof Collection<?>) {
			Collection<Object> c = (Collection<Object>) ((Collection<?>) obj);
			c.add(newEntry[0]);
			writeObject(path, c);
		}
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
