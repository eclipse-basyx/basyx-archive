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


/**
 * Factory creating connected ISubmodelElements from a given VABElementProxy
 * 
 * @author conradi
 *
 */
public class ConnectedSubmodelElementFactory {

	/**
	 * Creates connected ISubmodelElements from a VABElementProxy
	 * 
	 * @param smElemProxy pointing directly to "/submodelElement" in its root
	 * @return A Map containing the created connected ISubmodelElements and their IDs
	 */
	public static Map<String, ISubmodelElement> getConnectedSubmodelElements(VABElementProxy smElemProxy) {
		Map<String, ISubmodelElement> ret = new HashMap<>();
		
		// Add all DataElements and Operations
		ret.putAll(getDataElements(smElemProxy));
		ret.putAll(getOperations(smElemProxy));
		
		// SubmodelElement list; this list will contain all submodelElements
		Object smElemList = smElemProxy.getModelPropertyValue("/");
		// Read values
		Collection<Map<String, Object>> smElemNodes = getCollection(smElemList);
		
		// Convert to ISubmodelElement
		for (Map<String, Object> smElemNode : smElemNodes) {
			String id = (String) smElemNode.get(Referable.IDSHORT);
			
			if (SubmodelElementCollection.isSubmodelElementCollection(smElemNode)) {
				ret.put(id, new ConnectedSubmodelElementCollection(smElemProxy.getDeepProxy(id)));
			} else if (RelationshipElement.isRelationshipElement(smElemNode)) {
				ret.put(id, new ConnectedRelationshipElement(smElemProxy.getDeepProxy(id)));
			}
		}
		
		return ret;
	}
	
	/**
	 * Creates connected IOperations from a VABElementProxy
	 * 
	 * @param operationsProxy pointing directly to "/operations" in its root
	 * @return A Map containing the created connected IOperations and their IDs
	 */
	public static Map<String, IOperation> getOperations(VABElementProxy operationsProxy) {
		if (operationsProxy == null)
			return new HashMap<>();

		Map<String, IOperation> ret = new HashMap<>();
		Collection<Map<String, Object>> dataElemNodes = getCollection(operationsProxy.getModelPropertyValue("/"));
		for (Map<String, Object> deNode : dataElemNodes) {
			String id = Referable.createAsFacade(deNode).getIdShort();
			if (Operation.isOperation(deNode)) {
				ret.put(id, new ConnectedOperation(operationsProxy.getDeepProxy(id)));
			}
		}
		return ret;
	}
	
	
	
	
	/**
	 * Creates connected IDataElements from a VABElementProxy
	 * 
	 * @param dataElemProxy pointing directly to "/properties" in its root
	 * @return A Map containing the created connected IDataElements and their IDs
	 */
	public static Map<String, IDataElement> getDataElements(VABElementProxy dataElemProxy) {
		if (dataElemProxy == null)
			return new HashMap<>();
		
		Map<String, IDataElement> ret = new HashMap<>();
		
		Collection<Map<String, Object>> dataElemNodes = getCollection(dataElemProxy.getModelPropertyValue("/"));

		for (Map<String, Object> deNode : dataElemNodes) {
			String id = Referable.createAsFacade(deNode).getIdShort();
			if (Property.isProperty(deNode)) {
				ret.put(id, new ConnectedProperty(dataElemProxy.getDeepProxy(id)));
			} else if (Blob.isBlob(deNode)) {
				ret.put(id, new ConnectedBlob(dataElemProxy.getDeepProxy(id)));
			} else if (File.isFile(deNode)) {
				ret.put(id, new ConnectedFile(dataElemProxy.getDeepProxy(id)));
			}
		}
		return ret;
	}
	
	/**
	 * Casts an Object to an Collection of Maps.</br>
	 */
	@SuppressWarnings("unchecked")
	private static Collection<Map<String, Object>> getCollection(Object o) {
		if(o instanceof Map<?, ?>) {
			return (Collection<Map<String, Object>>) ((Map<String, Object>) o).get(Property.VALUE);
		} else if (o instanceof Collection<?>) {
			return (Collection<Map<String, Object>>) o;
		}
		return new ArrayList<>();
	}

	/**
	 * Returns a collection of ISubmodelElements
	 */
	public static Collection<ISubmodelElement> getElementCollection(VABElementProxy proxy) {
		Collection<Map<String, Object>> nodes = getCollection(proxy.getModelPropertyValue("/"));
		ArrayList<ISubmodelElement> ret = new ArrayList<>();
		for (Map<String, Object> node : nodes) {
			String id = Referable.createAsFacade(node).getIdShort();
			proxy = proxy.getDeepProxy(id);
			if (Property.isProperty(node)) {
				ret.add(new ConnectedProperty(proxy));
			} else if (Blob.isBlob(node)) {
				ret.add(new ConnectedBlob(proxy));
			} else if (File.isFile(node)) {
				ret.add(new ConnectedFile(proxy));
			} else if (SubmodelElementCollection.isSubmodelElementCollection(node)) {
				ret.add(new ConnectedSubmodelElementCollection(proxy));
			} else if (RelationshipElement.isRelationshipElement(node)) {
				ret.add(new ConnectedRelationshipElement(proxy));
			} else if (Operation.isOperation(node)) {
				ret.add(new ConnectedOperation(proxy));
			}
		}
		return ret;
	}
	
}
