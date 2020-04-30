package org.eclipse.basyx.submodel.metamodel.connected.submodelelement;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.property.ConnectedPropertyFactory;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.property.blob.ConnectedBlob;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.property.file.ConnectedFile;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.operation.ConnectedOperation;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.Blob;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.DataElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.File;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDefHelper;
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
			String modelType = ModelType.createAsFacade(smElemNode).getName();
			
			// Create the SubmodelElement based on its ModelType
			if(modelType.equals(SubmodelElementCollection.MODELTYPE)) {
				ret.put(id, new ConnectedSubmodelElementCollection(
						smElemProxy.getDeepProxy(id)));
			} else if(modelType.equals(RelationshipElement.MODELTYPE)) {
				ret.put(id, new ConnectedRelationshipElement(
						smElemProxy.getDeepProxy(id)));
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
		if(operationsProxy == null)
			return null;
		
		// Create return value
		Map<String, IOperation> ret = new HashMap<>();

		// Read values
		Collection<Map<String, Object>> operationNodes = getCollection(operationsProxy.getModelPropertyValue("/"));

		// Convert to IOperation
		for (Map<String, Object> opNode : operationNodes) {
			String id = (String) opNode.get(Referable.IDSHORT);
			String modelType = ModelType.createAsFacade(opNode).getName();
			
			if(modelType.equals(Operation.MODELTYPE)) {
				ConnectedOperation conOp = new ConnectedOperation(operationsProxy.getDeepProxy(id));
				ret.put(id, conOp);
			}
		}

		// Return result
		return ret;
	}
	
	
	/**
	 * Creates connected IDataElements from a VABElementProxy
	 * 
	 * @param dataElemProxy pointing directly to "/properties" in its root
	 * @return A Map containing the created connected IDataElements and their IDs
	 */
	public static Map<String, IDataElement> getDataElements(VABElementProxy dataElemProxy) {
		if(dataElemProxy == null)
			return null;
		
		Map<String, IDataElement> ret = new HashMap<>();
		
		Collection<Map<String, Object>> dataElemNodes = getCollection(dataElemProxy.getModelPropertyValue("/"));
		
		for (Map<String, Object> deNode : dataElemNodes) {
			String id = (String) deNode.get(Referable.IDSHORT);
			
			// Get the ModelType of the DataElement
			String modelType = ModelType.createAsFacade(deNode).getName();
			
			// Create the DataElement based on its ModelType
			if(modelType == null || modelType.equals(DataElement.MODELTYPE) || modelType.equals(Property.MODELTYPE)) {
				PropertyValueTypeDef type = getType(deNode);
				ret.put(id, ConnectedPropertyFactory.createProperty(
						dataElemProxy.getDeepProxy(id), type));
			} else if(modelType.equals(Blob.MODELTYPE)) {
				ret.put(id, new ConnectedBlob(dataElemProxy.getDeepProxy(id)));
			} else if(modelType.equals(File.MODELTYPE)) {
				ret.put(id, new ConnectedFile(dataElemProxy.getDeepProxy(id)));
			}
		}
		return ret;
	}
	
	
	/**
	 * Finds the PropertyValueType of a given IDataElement
	 */
	private static PropertyValueTypeDef getType(Map<String, Object> deNode) {
		if (deNode.containsKey(SubModel.PROPERTIES)) {
			return PropertyValueTypeDef.Container;
		} else {
			return PropertyValueTypeDefHelper.readTypeDef(deNode.get(Property.VALUETYPE));
		}
	}
	
	
	/**
	 * Casts an Object to an Collection of Maps.</br>
	 * 
	 * If the Collection of Maps is contained in a Property,
	 * Property.VALUE gets extracted and casted to the required
	 * Collection of Maps.</br></br>
	 * 
	 * This is a necessary hack because getModelPropertyValue wraps values
	 * into Properties if they are not in the "/dataElements" "/operations"
	 * or "/submodelElement" path.
	 */
	@SuppressWarnings("unchecked")
	private static Collection<Map<String, Object>> getCollection(Object o) {
		if(o instanceof Map<?, ?>) {
			return (Collection<Map<String, Object>>) ((Map<String, Object>) o).get(Property.VALUE);
		} else if (o instanceof Collection<?>) {
			return (Collection<Map<String, Object>>) o;
		}
		return null;
	}
	
}
