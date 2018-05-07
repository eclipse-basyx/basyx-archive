package org.eclipse.basyx.aas.impl.provider;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.resources.basic.IElement;
import org.eclipse.basyx.aas.api.resources.basic.IElementContainer;
import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.impl.provider.javahandler.JavaHandler;
import org.eclipse.basyx.aas.impl.tools.BaSysID;




/**
 * Provider class that export a Java object as BaSys model via HTTP REST interface 
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
	 * @param handler added handler
	 */
	public void addHandler(JavaHandler<?> handler) {
		System.out.println("**:"+handler.getIElement().getId());
		// Add Java handler
		javaHandlers.put(handler.getIElement().getId(), handler);
	}
	
	
	/**
	 * Remove a handler
	 * 
	 * @param handler removed handler
	 */
	public void removeHandler(JavaHandler<?> handler) {
		// Remove Java handler
		javaHandlers.remove(handler.getIElement().getId());
	}
	
	/**
	 * Get JavaHandler
	 */
	private JavaHandler<?> getJavaHandler(String path) {
		
		// Get JavaHandler
		JavaHandler<?> javaHandler = null;
		
		// Get model IDs and path
		String   aasID      = BaSysID.instance.getAASID(path);
		String   submodelID = BaSysID.instance.getSubmodelID(path);
		
		System.out.println("AID::"+aasID);
		System.out.println("SID::"+submodelID);
		
		// Check sub model ID
		if (aasID      != null) javaHandler = javaHandlers.get(aasID);
		if (submodelID != null) javaHandler = javaHandlers.get(submodelID);
		
		return javaHandler;
	}
	
	
	
	/**
	 * Get a sub model property value
	 * 
	 * @param path Path to the requested value
	 */
	@Override
	public Object getModelPropertyValue(String path) {
		System.out.println("[JHP] GET PATH::"+path);
		
		// Get JavaHandler
		JavaHandler<?> javaHandler = getJavaHandler(path);
		
		// Get Property Path
		String propPath = BaSysID.instance.getPath(path);
		System.out.println("PID::"+propPath);
		
		// Return element value
		return javaHandler.getValue(propPath);
	}

	
	/**
	 * Set a submodel property value
	 * 
	 * @param path Path to the requested value
	 * @param newValue New value to be assigned
	 */
	@Override
	public void setModelPropertyValue(String path, Object newValue) {
		System.out.println("[JHP] SET PATH::"+path+ " => " + newValue);
		
		// Get JavaHandler
		JavaHandler<?> javaHandler = getJavaHandler(path);
		
		// Get Property Path
		String propPath = BaSysID.instance.getPath(path);
		System.out.println("PID::"+propPath);
		
		// Return element value
		javaHandler.setValue(propPath, newValue);
		
	}

	/**
	 * Set a submodel property value
	 * 
	 * @param path Path to the requested value
	 */
	@Override
	public void createValue(String path, Object addedValue) {
		System.out.println("[JHP] CREATE PATH::"+path+ " ADD: " + addedValue);
		
		// Get JavaHandler
		JavaHandler<?> javaHandler = getJavaHandler(path);
		
		// Get Property Path
		String propPath = BaSysID.instance.getPath(path);
		System.out.println("PID::"+propPath);
		
		// Return element value
		javaHandler.createValue(propPath, addedValue);
		
	}

	@Override
	public void deleteValue(String path, Object deletedValue) {
		System.out.println("[JHP] DELETE PATH::"+path+ " OBJECT: " + deletedValue);
		
		// Get JavaHandler
		JavaHandler<?> javaHandler = getJavaHandler(path);
		
		// Get Property Path
		String propPath = BaSysID.instance.getPath(path);
		System.out.println("PID::"+propPath);
		
		// Return element value
		javaHandler.deleteValue(propPath, deletedValue);
		
	}

	@Override
	public Object invokeOperation(String path, Object[] parameter) {
		System.out.println("[JHP] INVOKE PATH::"+path+ " PARAMETER: " + parameter);
		
		// Get JavaHandler
		JavaHandler<?> javaHandler = getJavaHandler(path);
		
		// Get Property Path
		String propPath = BaSysID.instance.getPath(path);
		System.out.println("PID::"+propPath);
		
		// Return element value
		return javaHandler.invokeOperation(propPath, parameter);
	}

	@Override
	public Map<String, IElementReference> getContainedElements(String path) {
		// Return value
		Map<String, IElementReference> result = new HashMap<>();

		System.out.println("GetContained:"+path);

		// Get collection reference
		Object target = getModelPropertyValue(path);
		
		System.out.println("ContainedProperties: "+target);
		
		// Process return types
		if (target instanceof Map)               return JavaObjectProvider.createMapFromMap((Map<String, IElement>) target);
		if (target instanceof Collection)        return JavaObjectProvider.createMapFromCollection((Collection<IElement>) target);
		if (target instanceof IElementContainer) return JavaObjectProvider.createMapFromIElementContainer((IElementContainer) target);
		
		// No contained properties
		return result;
	}



}
