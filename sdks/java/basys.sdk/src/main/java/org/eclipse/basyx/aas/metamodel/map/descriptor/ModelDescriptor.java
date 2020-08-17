package org.eclipse.basyx.aas.metamodel.map.descriptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * Abstract class for a model descriptor that contains:
 * 	- a short id
 *  - an identifier
 *  - endpoints
 * 
 * @author espen
 *
 */
public abstract class ModelDescriptor extends VABModelMap<Object> {

	protected ModelDescriptor() {
		putAll(new ModelType(getModelType()));
	}

	/**
	 * Create descriptor from existing hash map
	 */
	public ModelDescriptor(Map<String, Object> map) {
		this();
		// Put all elements from map into this descriptor
		this.putAll(map);
	}

	/**
	 * Create a new descriptor with minimal information
	 */
	public ModelDescriptor(String idShort, IIdentifier id, String httpEndpoint) {
		this();

		// Set idShort
		put(Referable.IDSHORT, idShort);

		// Set Identifier, make sure the stored data structure is a map
		Identifier identifierMap = new Identifier(id.getIdType(), id.getId());
		put(Identifiable.IDENTIFICATION, identifierMap);

		// Set Endpoints
		HashMap<String, String> endpointWrapper = new HashMap<>();
		endpointWrapper.put(AssetAdministrationShell.TYPE, "http");
		endpointWrapper.put(AssetAdministrationShell.ADDRESS, httpEndpoint);
		put(AssetAdministrationShell.ENDPOINTS, Arrays.asList(endpointWrapper));
	}

	/**
	 * Return AAS ID
	 */
	@SuppressWarnings("unchecked")
	public IIdentifier getIdentifier() {
		Map<String, Object> identifierModel = (Map<String, Object>) get(Identifiable.IDENTIFICATION);
		return Identifier.createAsFacade(identifierModel);
	}
	
	public String getIdShort() {
		// Passing null in KeyElement type since it doesn't matter while only retrieving idShort
		return Referable.createAsFacade(this, null).getIdShort();
	}

	/**
	 * Return first AAS endpoint
	 */
	@SuppressWarnings("unchecked")
	public String getFirstEndpoint() {
		Object e = get(AssetAdministrationShell.ENDPOINTS);
		// Extract String from endpoint for set or list representations of the endpoint wrappers
		if (e instanceof Collection<?>) {
			Collection<Map<?, ?>> endpoints = (Collection<Map<?, ?>>) e;
			if (endpoints.isEmpty()) {
				return "";
			} else {
				// assume the endpoint is wrapped in a map with address and address type
				// return the first endpoint address
				return (String) endpoints.iterator().next().get(AssetAdministrationShell.ADDRESS);
			}
		}
		return "";
	}
	
	/**
	 * Return all AAS endpoints
	 */
	@SuppressWarnings("unchecked")
	public Collection<Map<String, Object>> getEndpoints() {
		Object endpoints = get(AssetAdministrationShell.ENDPOINTS);
		// Extract String from endpoint for set or list representations of the endpoint wrappers
		if (endpoints instanceof Collection<?>) {
			return (Collection<Map<String, Object>>) endpoints;
		} else {
			return new ArrayList<>();
		}
	}
	
	/**
	 * Validates a model descriptor by checking whether
	 * idShort, identification and endpoints key is present in the given map
	 * @param map
	 */
	protected void validate(Map<String, Object> map) {
		if (!map.containsKey(Referable.IDSHORT) || !(map.get(Referable.IDSHORT) instanceof String)) 
			throw new MalformedRequestException(getModelType() + " is missing idShort entry");
		if (!map.containsKey(Identifiable.IDENTIFICATION) || !(map.get(Identifiable.IDENTIFICATION) instanceof Map<?, ?>))
			throw new MalformedRequestException(getModelType() + " is missing identification entry");
		if (!map.containsKey(AssetAdministrationShell.ENDPOINTS) || !(map.get(AssetAdministrationShell.ENDPOINTS) instanceof Collection<?>))
			throw new MalformedRequestException(getModelType() + " is missing endpoints entry");
	}

	protected abstract String getModelType();
}
