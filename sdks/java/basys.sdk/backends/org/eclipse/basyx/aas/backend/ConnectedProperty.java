package org.eclipse.basyx.aas.backend;

import org.eclipse.basyx.aas.api.exception.UnknownElementTypeException;
import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.IProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.impl.resources.basic.DataType;
import org.eclipse.basyx.aas.impl.resources.basic.DataTypeMapping;
import org.eclipse.basyx.aas.impl.tools.BaSysID;

/**
 * Implement a connected AAS property that communicates via HTTP/REST
 * 
 * @author kuhn
 *
 */
public class ConnectedProperty extends ConnectedElement implements IProperty {

	/**
	 * Store AAS manager
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = null;

	/**
	 * Store AAS ID
	 */
	protected String aasID = null;

	/**
	 * Store sub model ID of this property
	 */
	protected String aasSubmodelID = null;

	/**
	 * Store path to this property
	 */
	protected String propertyPath = null;

	/**
	 * Store collection info
	 */
	protected boolean isCollection = false;

	/**
	 * Store map info
	 */
	protected boolean isMap = false;

	/**
	 * Store url reference
	 */
	protected static String type = "properties";

	/**
	 * Constructor - expect the URL to the sub model
	 * 
	 * @param connector
	 */
	public ConnectedProperty(String aasId, String submodelId, String path, IModelProvider provider, ConnectedAssetAdministrationShellManager aasMngr) {
		// Invoke base constructor
		super(provider);

		// Store parameter values
		aasID = aasId;
		aasSubmodelID = submodelId;
		propertyPath = BaSysID.instance.buildPath(aasID, aasSubmodelID, path, "properties");
		aasManager = aasMngr;
	}

	/**
	 * Returns property value
	 */
	public Object getElement() {
		if (this.isValid()) {

			// Get element from cache
			return this.getCachedElement();
		} else {

			// Get element from server
			Object result = provider.getModelPropertyValue(propertyPath);

			// Return if element is no reference
			if (!(result instanceof IElementReference))
				return result;

			// Resolve references
			while (result instanceof IElementReference) {

				// Cast to reference
				IElementReference elementReference = (IElementReference) result;

				// Resolve reference
				if (elementReference.isAASReference()) {
					// Resolve AAS reference
					result = aasManager.retrieveAASProxy(elementReference);
					// Reference to AAS is always last reference, stop loop here
					break;
				} else if (elementReference.isSubModelReference()) {
					// Resolve AAS reference
					result = aasManager.retrieveSubModelProxy(elementReference);
					// Reference to AAS is always last reference, stop loop here
					break;
				} else if (elementReference.isPropertyReference()) {
					// Property may be a reference to another property. This needs to be resolved
					// recursively.
					result = aasManager.retrievePropertyProxy(elementReference);
				}
			}

			// Type check
			if (result instanceof IAssetAdministrationShell)
				return result;
			if (result instanceof ISubModel)
				return result;
			if (!(result instanceof ConnectedProperty))
				throw new UnknownElementTypeException();

			// Get referenced element from server
			return ((ConnectedProperty) result).getElement();
		}
	}

	/**
	 * Get property data type
	 * 
	 * @return Property data type
	 */
	@Override
	public DataType getDataType() {
		Object o = provider.getModelPropertyValue(propertyPath);
		return DataTypeMapping.map(o.getClass());
	}

	/**
	 * Indicate if this property is a collection type
	 * 
	 * @return Collection indicator
	 */
	@Override
	public boolean isCollection() {
		// Check data type
		return (getDataType() == DataType.COLLECTION);

	}

	/**
	 * Indicate if this property is a map type
	 * 
	 * @return Map indicator
	 */
	@Override
	public boolean isMap() {
		// Check data type
		return (getDataType() == DataType.MAP);

	}

	@Override
	public boolean isContainer() {
		return (getDataType() == DataType.CONTAINER);
	}

}
