package org.eclipse.basyx.submodel.restapi;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Provider that handles container properties. Container properties can contain other submodel elements.
 *
 * @author espen
 *
 */
public class SubmodelElementMapProvider extends AbstractSubmodelElementProvider {
	/**
	 * Constructor based on a model provider that contains the container property
	 */
	public SubmodelElementMapProvider(IModelProvider provider) {
		super(provider);
	}

	/**
	 * The elements are stored in a map => convert them to a list
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected Collection<Map<String, Object>> getElementsList() {
		Object elements = getProvider().getModelPropertyValue("");
		Map<String, Map<String, Object>> all = (Map<String, Map<String, Object>>) elements;
		return all.values().stream().collect(Collectors.toList());
	}
	
	/**
	 * Single elements can be directly accessed in maps => return a proxy
	 */
	@Override
	protected IModelProvider getElementProxy(String[] pathElements) {
		String idShort = pathElements[1];
		return new VABElementProxy(idShort, getProvider());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void createSubmodelElement(Object newEntity) {
		// Create Operation or DataElement in a map
		String id = SubmodelElement.createAsFacade((Map<String, Object>) newEntity).getIdShort();
		getProvider().createValue(id, newEntity);
	}
}
