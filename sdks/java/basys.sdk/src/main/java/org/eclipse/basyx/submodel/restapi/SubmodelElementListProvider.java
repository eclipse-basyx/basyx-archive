package org.eclipse.basyx.submodel.restapi;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.modelprovider.map.VABMapProvider;

/**
 * Provider that handles container properties. Container properties can contain other submodel elements.
 *
 * @author espen
 *
 */
public class SubmodelElementListProvider extends AbstractSubmodelElementProvider {
	/**
	 * Constructor based on a model provider that contains the container property
	 */
	public SubmodelElementListProvider(IModelProvider provider) {
		super(provider);
	}

	/**
	 * Elements are assumed to be already stored in a list => return it
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Collection<Map<String, Object>> getElementsList() {
		return (Collection<Map<String, Object>>) getProvider().getModelPropertyValue("");
	}

	/**
	 * Single list elements cannot be directly accessed => resolve it and return a provider
	 * for the single element
	 */
	@Override
	protected IModelProvider getElementProxy(String[] pathElements) {
		String idShort = pathElements[1];
		// Resolve the list and return a wrapper provider for the queried element
		Collection<Map<String, Object>> list = getElementsList();
		for ( Map<String, Object> elem : list ) {
			if ( idShort.equals(Referable.createAsFacade(elem).getIdShort()) ) {
				return new VABMapProvider(elem);
			}
		}
		throw new ResourceNotFoundException("The element \"" + idShort + "\" could not be found");
	}

	@Override
	protected void createSubmodelElement(Object newEntity) {
		// Directly create the new element in the list
		getProvider().createValue("", newEntity);
	}
}
