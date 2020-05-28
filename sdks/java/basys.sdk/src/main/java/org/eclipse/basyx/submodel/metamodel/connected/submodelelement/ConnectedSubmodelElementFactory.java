package org.eclipse.basyx.submodel.metamodel.connected.submodelelement;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedBlob;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedFile;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedProperty;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.operation.ConnectedOperation;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.Blob;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.File;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.relationship.RelationshipElement;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;


/**
 * Factory creating connected ISubmodelElements from a given VABElementProxy
 * 
 * @author conradi, espen
 *
 */
public class ConnectedSubmodelElementFactory {

	/**
	 * Creates connected ISubmodelElements from a VABElementProxy
	 * 
	 * @param rootProxy      proxy for the root element
	 * @param collectionPath path in the proxy for accessing all elements
	 * @param elementPath    path in the proxy for accessing single elements by short ids
	 * @return A Map containing the created connected ISubmodelElements and their IDs
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, ISubmodelElement> getConnectedSubmodelElements(VABElementProxy rootProxy,
			String collectionPath, String elementPath) {
		// Query the whole list of elements
		Collection<Map<String, Object>> mapElemList = (Collection<Map<String, Object>>) rootProxy
				.getModelPropertyValue(collectionPath);
		// Get the type and idShort for each element and create the corresponding connected variant
		Map<String, ISubmodelElement> ret = new HashMap<>();
		for (Map<String, Object> node : mapElemList) {
			String idShort = Referable.createAsFacade(node).getIdShort();
			ISubmodelElement element = getConnectedSubmodelElement(rootProxy, elementPath, idShort, node);
			ret.put(idShort, element);
		}
		return ret;
	}

	/**
	 * Creates a Collection of connected ISubmodelElements from a VABElementProxy
	 * 
	 * @param rootProxy      proxy for the root element
	 * @param collectionPath path in the proxy for accessing all elements
	 * @param elementPath    path in the proxy for accessing single elements by short ids
	 * @return A Collection containing the created connected ISubmodelElements
	 */
	@SuppressWarnings("unchecked")
	public static Collection<ISubmodelElement> getElementCollection(VABElementProxy rootProxy,
			String collectionPath, String elementPath) {
		// Query the whole list of elements
		Collection<Map<String, Object>> mapElemList = (Collection<Map<String, Object>>) rootProxy
				.getModelPropertyValue(collectionPath);
		// Get the type and idShort for each element and create the corresponding connected variant
		Collection<ISubmodelElement> ret = new ArrayList<>();
		for (Map<String, Object> node : mapElemList) {
			String idShort = Referable.createAsFacade(node).getIdShort();
			ISubmodelElement element = getConnectedSubmodelElement(rootProxy, elementPath, idShort, node);
			ret.add(element);
		}
		return ret;
	}
	
	/**
	 * Creates a connected ISubmodelElement by idShort, proxy and map content
	 * 
	 * @param rootProxy      proxy for the root element
	 * @param collectionPath path in the proxy for accessing all elements
	 * @param elementPath    path in the proxy for accessing single elements by short ids
	 * @return The connected variant of the requested submodel element
	 */
	private static ISubmodelElement getConnectedSubmodelElement(VABElementProxy rootProxy,
			String elementPath, String idShort, Map<String, Object> mapContent) {
		String subPath = VABPathTools.concatenatePaths(elementPath, idShort);
		VABElementProxy proxy = rootProxy.getDeepProxy(subPath);
		if (Property.isProperty(mapContent)) {
			return new ConnectedProperty(proxy);
		} else if (Blob.isBlob(mapContent)) {
			return new ConnectedBlob(proxy);
		} else if (File.isFile(mapContent)) {
			return new ConnectedFile(proxy);
		} else if (SubmodelElementCollection.isSubmodelElementCollection(mapContent)) {
			return new ConnectedSubmodelElementCollection(proxy);
		} else if (RelationshipElement.isRelationshipElement(mapContent)) {
			return new ConnectedRelationshipElement(proxy);
		} else if (Operation.isOperation(mapContent)) {
			return new ConnectedOperation(proxy);
		} else {
			return null;
		}
	}
	
	/**
	 * Creates connected IOperations from a VABElementProxy
	 * 
	 * @param rootProxy      proxy for the root element
	 * @param collectionPath path in the proxy for accessing all elements
	 * @param elementPath    path in the proxy for accessing single elements by short ids
	 * @return A Map containing the created connected IOperations and their IDs
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, IOperation> getOperations(VABElementProxy rootProxy, String collectionPath,
			String elementPath) {
		// Query the whole list of elements
		Collection<Map<String, Object>> mapElemList = (Collection<Map<String, Object>>) rootProxy
				.getModelPropertyValue(collectionPath);

		// Get the type and idShort for each operation and create the corresponding connected variant
		Map<String, IOperation> ret = new HashMap<>();
		for (Map<String, Object> node : mapElemList) {
			String idShort = Referable.createAsFacade(node).getIdShort();
			String subPath = VABPathTools.concatenatePaths(elementPath, idShort);
			VABElementProxy proxy = rootProxy.getDeepProxy(subPath);
			if (Operation.isOperation(node)) {
				ret.put(idShort, new ConnectedOperation(proxy));
			}
		}
		return ret;
	}
	
	/**
	 * Creates connected IDataElements from a VABElementProxy
	 * 
	 * @param rootProxy      proxy for the root element
	 * @param collectionPath path in the proxy for accessing all elements
	 * @param elementPath    path in the proxy for accessing single elements by short ids
	 * @return A Map containing the created connected IDataElement and their IDs
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, IDataElement> getDataElements(VABElementProxy rootProxy, String collectionPath,
			String elementPath) {
		// Query the whole list of elements
		Collection<Map<String, Object>> mapElemList = (Collection<Map<String, Object>>) rootProxy
				.getModelPropertyValue(collectionPath);

		// Get the type and idShort for each operation and create the corresponding connected variant
		Map<String, IDataElement> ret = new HashMap<>();
		for (Map<String, Object> node : mapElemList) {
			String idShort = Referable.createAsFacade(node).getIdShort();
			String subPath = VABPathTools.concatenatePaths(elementPath, idShort);
			VABElementProxy proxy = rootProxy.getDeepProxy(subPath);
			if (Property.isProperty(node)) {
				ret.put(idShort, new ConnectedProperty(proxy));
			} else if (Blob.isBlob(node)) {
				ret.put(idShort, new ConnectedBlob(proxy));
			} else if (File.isFile(node)) {
				ret.put(idShort, new ConnectedFile(proxy));
			}
		}
		return ret;
	}
}
