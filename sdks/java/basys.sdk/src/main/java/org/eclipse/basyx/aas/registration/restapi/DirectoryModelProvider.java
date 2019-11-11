package org.eclipse.basyx.aas.registration.restapi;

import java.net.URLDecoder;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Connects an arbitrary IRegistryService implementation to the VAB
 * 
 * @author schnicke
 *
 */
public class DirectoryModelProvider implements IModelProvider {

	IAASRegistryService registry;
	
	private static final String PREFIX = "api/v1/registry";

	public DirectoryModelProvider(IAASRegistryService registry) {
		this.registry = registry;
	}

	public DirectoryModelProvider() {
		this(new InMemoryRegistry());
	}

	/**
	 * Check for correctness of path and returns a stripped path (i.e. no leading
	 * prefix)
	 * 
	 * @param path
	 * @return
	 */
	private String stripPrefix(String path) {
		path = VABPathTools.stripSlashes(path);
		if (!path.startsWith(PREFIX)) {
			throw new RuntimeException("Path " + path + " not recognized as registry path. Has to start with " + PREFIX);
		}
		path = path.replace(PREFIX, "");
		path = VABPathTools.stripSlashes(path);
		return path;
	}

	@Override
	public Object getModelPropertyValue(String path) throws Exception {
		path = stripPrefix(path);

		if (path.isEmpty()) { // Request for all AAS
			return registry.lookupAll();
		} else { // Request for specific AAS
			// Decode encoded path
			path = URLDecoder.decode(path, "UTF-8");
			return registry.lookupAAS(new ModelUrn(path));
		}
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		path = stripPrefix(path);

		if (!path.isEmpty()) { // Overwriting existing entry
			// Decode encoded path
			path = URLDecoder.decode(path, "UTF-8");
			ModelUrn identifier = new ModelUrn(path);

			// Make sure that the old value is overwritten
			// TODO: Exception in case of non existance
			registry.delete(identifier);
			registry.register((AASDescriptor) newValue);
		} else {
			throw new RuntimeException("Set with empty path is not supported by registry");
		}
	}

	@Override
	public void createValue(String path, Object newEntity) throws Exception {
		path = stripPrefix(path);

		if (path.isEmpty()) { // Creating new entry
			registry.register((AASDescriptor) newEntity);
		} else {
			throw new RuntimeException("Create with non-empty path is not supported by registry");
		}
	}

	@Override
	public void deleteValue(String path) throws Exception {
		path = stripPrefix(path);

		if (!path.isEmpty()) { // Deleting an entry
			// Decode encoded path
			path = URLDecoder.decode(path, "UTF-8");
			registry.delete(new ModelUrn(path));
		} else {
			throw new RuntimeException("Delete with empty path is not supported by registry");
		}
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		throw new RuntimeException("DeleteValue with parameter not supported by registry");
	}

	@Override
	public Object invokeOperation(String path, Object... parameter) throws Exception {
		throw new RuntimeException("Invoke not supported by registry");
	}

}
