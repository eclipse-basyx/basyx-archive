package org.eclipse.basyx.submodel.metamodel.connected.facades;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.IElementContainer;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedVABModelMap;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedRelationshipElement;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.property.ConnectedPropertyFactory;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.operation.ConnectedOperation;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.relationship.RelationshipElement;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;

/**
 * Facade providing access to a remove VABElementContainer
 * 
 * @author schnicke
 *
 */
public class ConnectedVABElementContainerFacade extends ConnectedVABModelMap<Object> implements IElementContainer {
	ConnectedPropertyFactory factory = new ConnectedPropertyFactory();

	public ConnectedVABElementContainerFacade(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public void addSubModelElement(ISubmodelElement element) {
		getProxy().createValue(SubModel.SUBMODELELEMENT, element);
		if (element instanceof IDataElement) {
			getProxy().createValue(SubModel.PROPERTIES, element);
		} else if (element instanceof IOperation) {
			getProxy().createValue(SubModel.OPERATIONS, element);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IDataElement> getDataElements() {
		// Create return value
		Map<String, IDataElement> ret = new HashMap<>();

		// Sub model operation list
		Object smDeList = getProxy().getModelPropertyValue(SubModel.PROPERTIES);

		// Read values
		Collection<Map<String, Object>> dataElemNodes = (Collection<Map<String, Object>>) smDeList;

		// Convert to IOperation
		for (Map<String, Object> deNode : dataElemNodes) {
			String id = (String) deNode.get(Referable.IDSHORT);
			ret.put(id, factory
					.createProperty(getProxy().getDeepProxy(VABPathTools.concatenatePaths(SubModel.PROPERTIES, id))));
		}
		// Return result
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IOperation> getOperations() {

		// Create return value
		Map<String, IOperation> ret = new HashMap<>();

		// Sub model operation list
		Object smOpList = getProxy().getModelPropertyValue(SubModel.OPERATIONS);
		// Read values
		Collection<Map<String, Object>> operationNodes = (Collection<Map<String, Object>>) smOpList;

		// Convert to IOperation
		for (Map<String, Object> opNode : operationNodes) {
			String id = (String) opNode.get(Referable.IDSHORT);

			ConnectedOperation conOp = new ConnectedOperation(
					getProxy().getDeepProxy(VABPathTools.concatenatePaths(SubModel.OPERATIONS, id)));
			// Cache operation properties
			conOp.putAllLocal(opNode);
			ret.put(id, conOp);
		}

		// Return result
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ISubmodelElement> getSubmodelElements() {

		// Create return value
		Map<String, ISubmodelElement> ret = new HashMap<>();

		// Sub model operation list
		Object opList = getProxy().getModelPropertyValue(SubModel.OPERATIONS);
		// Read values
		Collection<Map<String, Object>> opNodes = (Collection<Map<String, Object>>) opList;
		
		// Sub model dataElement list
		Object deList = getProxy().getModelPropertyValue(SubModel.PROPERTIES);
		// Read values
		Collection<Map<String, Object>> deNodes = (Collection<Map<String, Object>>) deList;
		

		// submodel element list; this list will contain all submodelElements
		Object smElemList = getProxy().getModelPropertyValue(SubModel.SUBMODELELEMENT);
		// Read values
		Collection<Map<String, Object>> smElemNodes = (Collection<Map<String, Object>>) smElemList;
		//remove all submodelElements already contained in on of the other lists
		smElemNodes.removeAll(opNodes);
		smElemNodes.removeAll(deNodes);
		

		// Convert to IOperation
		for (Map<String, Object> opNode : opNodes) {
			String id = (String) opNode.get(Referable.IDSHORT);

			ConnectedOperation conOp = new ConnectedOperation(
					getProxy().getDeepProxy(VABPathTools.concatenatePaths(SubModel.OPERATIONS, id)));
			// Cache operation properties
			conOp.putAllLocal(opNode);
			ret.put(id, conOp);
		}
		
		// Convert to IProperty
		for (Map<String, Object> deNode : deNodes) {
			String id = (String) deNode.get(Referable.IDSHORT);
			ret.put(id, factory
					.createProperty(getProxy().getDeepProxy(VABPathTools.concatenatePaths(SubModel.PROPERTIES, id))));
		}
		
		// Convert to ISubmodelElement
		for (Map<String, Object> smElemNode : smElemNodes) {
			String id = (String) smElemNode.get(Referable.IDSHORT);
			String modelType = ((String)((Map<String, Object>)(getProxy().getDeepProxy(
							VABPathTools.concatenatePaths(ModelType.MODELTYPE, id)))).get(ModelType.NAME));
			
			//convert ISubmodelElementCollection
			if(modelType.equals(SubmodelElementCollection.MODELTYPE)) {
				ret.put(id, new ConnectedSubmodelElementCollection(
						getProxy().getDeepProxy(VABPathTools.concatenatePaths(SubModel.SUBMODELELEMENT, id))));
			}

			//convert IRelationshipElement
			if(modelType.equals(RelationshipElement.MODELTYPE)) {
				ret.put(id, new ConnectedRelationshipElement(
						getProxy().getDeepProxy(VABPathTools.concatenatePaths(SubModel.SUBMODELELEMENT, id))));
			}
		}

		// Return result
		return ret;
	}

}
