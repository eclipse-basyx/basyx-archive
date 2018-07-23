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
	 * Create a new JavaHandler for property, event or operation and add it to javaHandlers
	 * 
	 * @param path Path to the requested value
	 * @param addedOject should be a 
	 */
	@Override
	public void createValue(String path, Object addedObject) {
		System.out.println("[JHP] CREATE PATH::"+path+ " ADD: " + addedObject);
		
		JavaHandler<?>  javaHandler = null;
		
		// Get JavaHandler
		if ((javaHandler = getJavaHandler(path)) != null) {
			
			// Get Property Path
			String propPath = BaSysID.instance.getPath(path);
			System.out.println("PID::"+propPath);
			
			// Cast Object to Property, Operation or Event and add it respectively
			//-  Add property, creates and registers new property
			//javaHandler.addProperty("sampleProperty1",                  (obj) -> {return obj.sampleProperty1;},                  (obj, val) -> {obj.sampleProperty1=(int) val;},                                  null,   null);
		}
	}

	@Override
	public void deleteValue(String path) {
		System.out.println("[JHP] DELETE PATH::"+path );
		
		// Get JavaHandler
		JavaHandler<?> javaHandler = getJavaHandler(path);
		
		removeHandler(javaHandler);
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



	/**
	 * Add a value to a collection or map
	 * 
	 * @param path Path to the requested value
	 * @param newValue (pair) to be added to collection or map
	 */
	@Override
	public void setContainedValue(String path, Object[] parameter) {
		System.out.println("[JHP] SET CONTAINED PATH::"+path+ " => " + parameter);
		
		// Get JavaHandler
		JavaHandler<?> javaHandler = getJavaHandler(path);
		
		// Get Property Path
		String propPath = BaSysID.instance.getPath(path);
		System.out.println("PID::"+propPath);
		
		// Set contained value
		javaHandler.setContainedValue(propPath, parameter);
		
	}


	/**
	 * Remove a value from a collection or map by key
	 */
	@Override
	public void deleteContainedValue(String path, Object[] parameter) throws Exception {
		System.out.println("[JHP] DELETE PATH::"+path+ " OBJECT: " + parameter);
		
		// Get JavaHandler
		JavaHandler<?> javaHandler = getJavaHandler(path);
		
		// Get Property Path
		String propPath = BaSysID.instance.getPath(path);
		System.out.println("PID::"+propPath);
			
		javaHandler.deleteContainedValue(propPath, parameter[0]);
				
	}


	


}
