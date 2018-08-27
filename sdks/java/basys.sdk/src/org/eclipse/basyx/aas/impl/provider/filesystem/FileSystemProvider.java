package org.eclipse.basyx.aas.impl.provider.filesystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.basyx.aas.api.exception.FeatureNotImplementedException;
import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.eclipse.basyx.aas.impl.provider.AbstractModelScopeProvider;
import org.eclipse.basyx.aas.impl.provider.filesystem.filesystem.File;
import org.eclipse.basyx.aas.impl.provider.filesystem.filesystem.FileSystem;
import org.eclipse.basyx.aas.impl.provider.filesystem.filesystem.FileType;
import org.eclipse.basyx.aas.impl.tools.BaSysID;
import org.json.JSONObject;

/**
 * Provides models based on a generic file system 
 * @author schnicke
 *
 */
public class FileSystemProvider extends AbstractModelScopeProvider {

	private FileSystem fileSystem;
	protected static final String DATA = "/data";
	private final String rootDir;

	public FileSystemProvider(FileSystem fileSystem, String rootDir) {
		this.fileSystem = fileSystem;
		this.rootDir = rootDir;
	}

	protected FileSystem getFileSystem() {
		return fileSystem;
	}
	
	protected String getRootDir() {
		return rootDir;
	}
	
	protected String getFolderPath(String address) {
		return getFolderPath(getRootDir(), address);
	}
	
	protected String getFolderPath(String root, String address) {
		return root + "/" + BaSysID.instance.getUnScopedServicePath(address);
	}

	@Override
	public Object getModelPropertyValue(String address) {
		String path = getFolderPath(address);
		if (path.endsWith("/operations") || path.endsWith("/properties") || path.endsWith("/events")
				|| path.endsWith("/submodels")) {
			List<File> files;
			try {
				files = fileSystem.readDirectory(path);
				List<String> directories = files.stream().filter(f -> f.getType() == FileType.DIRECTORY)
						.map(f -> f.getName()).collect(Collectors.toList());
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
	}

	@Override
	public void setModelPropertyValue(String address, Object newValue) throws Exception {
		String path = getFolderPath(address);
		writeObject(path, newValue);
	}

	@Override
	public void createValue(String address, Object newEntity) throws Exception {
		String path = getFolderPath(address);
		fileSystem.createDirectory(path);
		setModelPropertyValue(path, newEntity);
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

	private Object readObject(String path) throws Exception {
		String readPath = path + DATA;

		String serialized = fileSystem.readFile(readPath);
		if (serialized == null) {
			return null;
		} else {
			return JSONTools.Instance.deserialize(new JSONObject(serialized));
		}
	}

	private void writeObject(String path, Object o) throws Exception {
		String writePath = path + DATA;
		fileSystem.writeFile(writePath, JSONTools.Instance.serialize(o).toString());
	}

}
