package org.eclipse.basyx.submodel.restapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.facade.SubmodelElementMapCollectionConverter;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.restapi.api.ISubmodelAPI;
import org.eclipse.basyx.submodel.restapi.vab.VABSubmodelAPI;
import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;

/**
 * Additional VAB provider specific for providing submodels together
 * with other SDKs by implementing the submodel API.
 *
 * The VAB provides generic models without considering high-level information.
 * For example, Operation submodel elements are realized as a Map. The function
 * is contained and can be accessed at /operations/opId/invokable. Therefore
 * invoking the Operation directly at /operations/opId/ attempts to invoke the
 * Map, which does not have any effect for the VABModelProvider.
 *
 * This provider maps these requests to the VAB primitives to generate the
 * desired behavior as it is described in the BaSys documentation.
 *
 * @author espen, schnicke
 *
 */
public class SubModelProvider extends MetaModelProvider {

	ISubmodelAPI submodelAPI;

	/**
	 * Default constructor - based on an empty submodel with a lambda provider
	 */
	public SubModelProvider() {
		this(new SubModel());
	}

	/**
	 * Creates a SubmodelProvider based on the VAB API, wrapping the passed provider
	 * 
	 * @param provider
	 *            to be wrapped by submodel API
	 */
	public SubModelProvider(IModelProvider provider) {
		submodelAPI = new VABSubmodelAPI(provider);
	}

	/**
	 * Creates a SubModelProvider based on a lambda provider and a given model
	 */
	public SubModelProvider(SubModel model) {
		submodelAPI = new VABSubmodelAPI(new VABLambdaProvider(model));
	}

	/**
	 * Creates a SubModelProvider based on a given ISubmodelAPI.
	 */
	public SubModelProvider(ISubmodelAPI submodelAPI) {
		this.submodelAPI = submodelAPI;
	}

	/**
	 * Strips submodel prefix if it exists
	 *
	 * @param path
	 * @return
	 */
	private String removeSubmodelPrefix(String path) {
		path = VABPathTools.stripSlashes(path);
		if (path.startsWith("submodel/")) {
			path = path.replaceFirst("submodel/", "");
		} else if (path.equals("submodel")) {
			path = "";
		}
		path = VABPathTools.stripSlashes(path);
		return path;
	}

	@Override
	public Object getModelPropertyValue(String path) throws ProviderException {
		VABPathTools.checkPathForNull(path);
		path = removeSubmodelPrefix(path);
		if (path.isEmpty()) {
			ISubModel sm = submodelAPI.getSubmodel();

			// Change internal map representation to set
			if (sm instanceof SubModel) {
				return SubmodelElementMapCollectionConverter.smToMap((SubModel) sm);
			} else {
				return sm;
			}
		} else {
			String[] splitted = VABPathTools.splitPath(path);
			// Request for submodelElements
			if (splitted.length == 1 && splitted[0].equals(SubmodelElementProvider.ELEMENTS)) {
				return submodelAPI.getElements();
			} else if (splitted.length >= 2 && isQualifier(splitted[0])) { // Request for element with specific idShort
				String idShort = splitted[1];
				if (splitted.length == 2) {
					return submodelAPI.getSubmodelElement(idShort);
				} else if (isPropertyValuePath(splitted)) { // Request for the value of an property
					return submodelAPI.getPropertyValue(idShort);
				} else if (isSubmodelElementListPath(splitted)) {
					// Create list from array and wrap it in ArrayList to ensure modifiability
					List<String> idShorts = getIdShorts(splitted);
					
					if (endsWithValue(splitted)) {
						return submodelAPI.getNestedPropertyValue(idShorts);
					} else {
						return submodelAPI.getNestedSubmodelElement(idShorts);
					}
				}
			}
		}
		throw new MalformedRequestException("Unknown path " + path + " was requested");
	}

	private List<String> getIdShorts(String[] splitted) {
		// Create list from array and wrap it in ArrayList to ensure modifiability
		List<String> idShorts = new ArrayList<>(Arrays.asList(splitted));

		// Drop inital "submodels"
		idShorts.remove(0);

		// If value is contained in path, remove it
		if (splitted[splitted.length - 1].equals(Property.VALUE)) {
			idShorts.remove(idShorts.size() - 1);
		}
		return idShorts;
	}

	private boolean isPropertyValuePath(String[] splitted) {
		return splitted.length == 3 && splitted[0].equals(SubmodelElementProvider.ELEMENTS) && endsWithValue(splitted);
	}

	private boolean endsWithValue(String[] splitted) {
		return splitted[splitted.length - 1].equals(Property.VALUE);
	}

	private boolean isSubmodelElementListPath(String[] splitted) {
		return splitted.length > 2 && splitted[0].equals(SubmodelElementProvider.ELEMENTS);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setModelPropertyValue(String path, Object newValue) throws ProviderException {
		path = removeSubmodelPrefix(path);
		if (path.isEmpty()) {
			throw new MalformedRequestException("Set on \"submodel\" not supported");
		} else {
			String[] splitted = VABPathTools.splitPath(path);
			if (endsWithValue(splitted)) {
				submodelAPI.updateNestedProperty(getIdShorts(splitted), newValue);
			} else {
				submodelAPI.addSubmodelElement(SubmodelElement.createAsFacade((Map<String, Object>) newValue));
			}
		}
	}

	@Override
	public void createValue(String path, Object newEntity) throws ProviderException {
		throw new MalformedRequestException("Invalid access");
	}

	@Override
	public void deleteValue(String path) throws ProviderException {
		path = removeSubmodelPrefix(path);
		if (!path.isEmpty()) {
			String[] splitted = VABPathTools.splitPath(path);
			if (isQualifier(splitted[0])) {
				submodelAPI.deleteNestedSubmodelElement(getIdShorts(splitted));
			} else {
				throw new MalformedRequestException("Path " + path + " not supported for delete");
			}
		} else {
			throw new MalformedRequestException("Path \"submodel\" not supported for delete");
		}
	}

	private boolean isQualifier(String str) {
		return str.equals(SubmodelElementProvider.ELEMENTS);
	}

	@Override
	public void deleteValue(String path, Object obj) throws ProviderException {
		throw new MalformedRequestException("Delete with a passed argument not allowed");
	}

	@Override
	public Object invokeOperation(String path, Object... parameters) throws ProviderException {
		path = removeSubmodelPrefix(path);
		if (path.isEmpty()) {
			throw new MalformedRequestException("Invalid access");
		} else {
			String[] splitted = VABPathTools.splitPath(path);
			if (VABPathTools.isOperationInvokationPath(path)) {
				return submodelAPI.invokeNestedOperation(getIdShorts(splitted), parameters);
			} else {
				throw new MalformedRequestException("Unknown path " + path);
			}
		}
	}

	protected void setAPI(ISubmodelAPI api) {
		this.submodelAPI = api;
	}
}
