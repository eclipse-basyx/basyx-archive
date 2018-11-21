package org.eclipse.basyx.aas.backend.connector;

import java.util.Map;

import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;

/**
 * Provider Class that receives a hashmap from its provider containing a message that was sent from the server. 
 * It removes the message header and returns the entity.
 * @author pschorn
 *
 */
public class ConnectedHashmapProvider implements IModelProvider {


	/**
	 * Reference to ConnectorProvider backend
	 */
	protected IModelProvider provider = null;
	
	

	public ConnectedHashmapProvider(IModelProvider provider) {
		
		this.provider = provider;
	}
	

	@Override
	public Object getModelPropertyValue(String path) {
		// Get element from server
		Map<String, Object> result = (Map<String, Object>) provider.getModelPropertyValue(path); 
		
		Object containedElement = result.get("entity");
		
		/**
		 * TODO process other message information like "success", "entityType", "isException", "messages"
		 */
		
		return containedElement;
	}


	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {

		provider.setModelPropertyValue(path, newValue);
	}


	@Override
	public void createValue(String path, Object newEntity) throws Exception {
		 
		provider.createValue(path, newEntity);
	}


	@Override
	public void deleteValue(String path) throws Exception {
		
		provider.deleteValue(path);
	}


	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		
		provider.deleteValue(path, obj);
	}


	@Override
	public Object invokeOperation(String path, Object[] parameter) throws Exception {

		Map<String, Object> returnMessage = (Map<String, Object>) provider.invokeOperation(path, parameter);
		
		return returnMessage.get("entity");
	}


	@Override
	public Map<String, IElementReference> getContainedElements(String path) {
		// TODO Auto-generated method stub
		return null;
	}
}