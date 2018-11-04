package org.eclipse.basyx.aas.impl.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.resources.basic.IContainerProperty;
import org.eclipse.basyx.aas.api.resources.basic.IElement;
import org.eclipse.basyx.aas.api.resources.basic.IElementContainer;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.impl.provider.javahandler.JavaHandler;
import org.eclipse.basyx.aas.impl.tools.BaSysID;

/**
 * Provider class that export a Java object and uses handlers to access
 * properties
 * 
 * @author kuhn
 *
 */
public class JavaHandlerProvider extends AbstractModelScopeProvider implements IModelProvider {

	/**
	 * Store Java handlers
	 */
	protected Map<String, JavaHandler<?>> javaHandlers = new HashMap<>();

	/**
	 * Add a handler
	 * 
	 * @param handler
	 *            added handler
	 */
	public void addHandler(JavaHandler<?> handler) {
		// Add Java handler
		String id;
		IElement elem = handler.getIElement();
		if (elem instanceof IContainerProperty) {
			id = getContainerPropertyUniqueId((IContainerProperty) elem);
		} else {
			id = elem.getId();
		}

		System.out.println("**:" + id);
		javaHandlers.put(id, handler);
	}

	private String getContainerPropertyUniqueId(IContainerProperty prop) {
		IElement elem = prop;
		List<String> ids = new ArrayList<>();
		while (!(elem instanceof ISubModel)) { 
			ids.add(elem.getId());
			elem = elem.getParent();
		}
		
		String smId = elem.getId();
		
		Collections.reverse(ids);
		
		StringBuilder propPath= new StringBuilder();
		propPath.append(ids.get(0));
		
		for(int i= 1; i < ids.size(); i++) {
			propPath.append(".").append(ids.get(i));
		}
		
		return smId + "/" + propPath;
	}

	/**
	 * Remove a handler
	 * 
	 * @param handler
	 *            removed handler
	 */
	public void removeHandler(JavaHandler<?> handler) {
		// Remove Java handler
		javaHandlers.remove(handler.getIElement().getId());
	}

	/**
	 * Get JavaHandler
	 */
	private JavaHandler<?> getJavaHandler(String path, String propPath) {

		// Get JavaHandler
		JavaHandler<?> javaHandler = null;

		// Get handler by ID
		if (propPath.equals("submodels") || BaSysID.instance.getSubmodelID(path).equals("")) {
			path = BaSysID.instance.getAASID(path); // return AAS
		} else if(propPath.contains(".")) { // Handle PropertyContainer
			String smId = BaSysID.instance.getSubmodelID(path);
			String stripped = propPath.replace(".properties.", "."); // Map to Id
			if(stripped.contains(".operations.")) { // Remove .operations.
				stripped = stripped.substring(0, stripped.indexOf(".operations."));
			}
			if(stripped.contains(".")) { // Only get parent element
				stripped =  stripped.substring(0, stripped.lastIndexOf("."));
			}
			path = smId + "/" + stripped;
		}
		else
			path = BaSysID.instance.getSubmodelID(path); // return SubmodelID

		if (path != null)
			javaHandler = javaHandlers.get(path);
		System.out.println("JH::" + javaHandler);

		return javaHandler;
	}

	/**
	 * Get a sub model property value
	 * 
	 * @param path
	 *            Path to the requested value
	 */
	@Override
	public Object getModelPropertyValue(String path) {
		System.out.println("[JHP] GET PATH::" + path);

		// Get Property Path
		String propPath = BaSysID.instance.getPath(path);
		System.out.println("PID::" + propPath);

		// Get JavaHandler
		JavaHandler<?> javaHandler = getJavaHandler(path, propPath);

		// Return element value
		return javaHandler.getValue(propPath);
	}

	/**
	 * Set a submodel property value
	 * 
	 * @param path
	 *            Path to the requested value
	 * @param newValue
	 *            New value to be assigned
	 */
	@Override
	public void setModelPropertyValue(String path, Object newValue) {
		System.out.println("[JHP] SET PATH::" + path + " => " + newValue);

		// Get Property Path
		String propPath = BaSysID.instance.getPath(path);
		System.out.println("PID::" + propPath);

		// Get JavaHandler
		JavaHandler<?> javaHandler = getJavaHandler(path, propPath);

		// Set element value
		javaHandler.setValue(propPath, newValue);

	}

	/**
	 * Adds a new Entry to a map or collection. // TODO enable adding nested
	 * properties
	 */
	@Override
	public void setModelPropertyValue(String path, Object... newValue) throws Exception {
		System.out.println("[JHP] SET PATH::" + path + " => " + newValue);

		// Get Property Path
		String propPath = BaSysID.instance.getPath(path);
		System.out.println("PID::" + propPath);

		// Get JavaHandler
		JavaHandler<?> javaHandler = getJavaHandler(path, propPath);

		// Add entry to container
		javaHandler.setContainedValue(propPath, newValue);
	}

	/**
	 * Creates a new Property, Operation, Event, Submodel or AAS.
	 * 
	 * @param path
	 *            Path to the requested value
	 * @param newElement
	 *            the element to be created on the server
	 */
	@Override
	public void createValue(String path, Object newElement) {
		System.out.println("[JHP] CREATE PATH::" + path + " ADD: " + newElement);

		// Get Property Path
		String propPath = BaSysID.instance.getPath(path);
		System.out.println("PID::" + propPath);

		// TODO Create new Javahandler for the new Property, Operation, Event, Submodel
		// or AAS.
		System.out.println("Creating new Entity " + newElement);
	}

	/**
	 * Deletes an entry from a collection or map
	 */
	@Override
	public void deleteValue(String path) {
		System.out.println("[JHP] DELETE PATH::" + path);

		// Get Property Path
		String propPath = BaSysID.instance.getPath(path);
		System.out.println("PID::" + propPath);

		// Get JavaHandler
		JavaHandler<?> javaHandler = getJavaHandler(path, propPath);

		// TODO Delete JavaHandler for Property, Operation, Event, Submodel or AAS.
		System.out.println("Deleting Entity " + javaHandler);

	}

	/**
	 * Deletes an entry from a collection or map
	 */
	@Override
	public void deleteValue(String path, Object deletedValue) {
		System.out.println("[JHP] DELETE PATH::" + path + " OBJECT: " + deletedValue);

		// Get Property Path
		String propPath = BaSysID.instance.getPath(path);
		System.out.println("PID::" + propPath);

		// Get JavaHandler
		JavaHandler<?> javaHandler = getJavaHandler(path, propPath);

		// Return element value
		javaHandler.deleteContainedValue(propPath, deletedValue);

	}

	@Override
	public Object invokeOperation(String path, Object[] parameter) {
		System.out.println("[JHP] INVOKE PATH::" + path + " PARAMETER: " + parameter);

		// Get Property Path
		String propPath = BaSysID.instance.getPath(path);
		System.out.println("PID::" + propPath);

		// Get JavaHandler
		JavaHandler<?> javaHandler = getJavaHandler(path, propPath);

		// Return element value
		return javaHandler.invokeOperation(propPath, parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IElementReference> getContainedElements(String path) {
		// Return value
		Map<String, IElementReference> result = new HashMap<>();

		System.out.println("GetContained:" + path);

		// Get collection reference
		Object target = getModelPropertyValue(path);

		System.out.println("ContainedProperties: " + target);

		// Process return types
		if (target instanceof Map)
			return JavaObjectProvider.createMapFromMap((Map<String, IElement>) target);
		if (target instanceof Collection)
			return JavaObjectProvider.createMapFromCollection((Collection<IElement>) target);
		if (target instanceof IElementContainer)
			return JavaObjectProvider.createMapFromIElementContainer((IElementContainer) target);

		// No contained properties
		return result;
	}
}
