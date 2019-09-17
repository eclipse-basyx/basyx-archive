package org.eclipse.basyx.aas.backend.connected.facades;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IDataElement;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.ISubmodelElement;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.operation.IOperation;
import org.eclipse.basyx.aas.backend.connected.ConnectedVABModelMap;
import org.eclipse.basyx.aas.backend.connected.aas.submodelelement.operation.ConnectedOperation;
import org.eclipse.basyx.aas.backend.connected.aas.submodelelement.property.ConnectedPropertyFactory;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.IVABElementContainer;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.eclipse.basyx.vab.core.tools.VABPathTools;

/**
 * Facade providing access to a remove VABElementContainer
 * 
 * @author schnicke
 *
 */
public class ConnectedVABElementContainerFacade extends ConnectedVABModelMap<Object> implements IVABElementContainer {
	ConnectedPropertyFactory factory = new ConnectedPropertyFactory();

	public ConnectedVABElementContainerFacade(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public void addSubModelElement(ISubmodelElement element) {
		getProxy().createValue(VABPathTools.append(SubModel.SUBMODELELEMENT, element.getId()), element);
		if (element instanceof IDataElement) {
			getProxy().createValue(VABPathTools.append(SubModel.PROPERTIES, element.getId()), element);
		} else if (element instanceof IOperation) {
			getProxy().createValue(VABPathTools.append(SubModel.OPERATIONS, element.getId()), element);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IDataElement> getDataElements() {
		// Store operations as map
		Map<String, Object> des = new HashMap<>();

		// Create return value
		Map<String, IDataElement> ret = new HashMap<>();

		// Sub model operation list
		Object smDeList = getProxy().getModelPropertyValue(SubModel.PROPERTIES);

		// RTTI check
		if (smDeList instanceof HashSet) {
			// Read values
			Collection<Map<String, Object>> dataElemNodes = (Collection<Map<String, Object>>) smDeList;

			// Convert to IOperation
			for (Map<String, Object> deNode : dataElemNodes) {
				String id = (String) deNode.get(Referable.IDSHORT);
				ret.put(id, factory.createProperty(getProxy().getDeepProxy(VABPathTools.concatenatePaths(SubModel.PROPERTIES, id))));
			}
		} else {
			// Properties already arrive as Map
			des = (Map<String, Object>) smDeList;

			for (String s : des.keySet()) {
				ret.put(s, factory.createProperty(getProxy().getDeepProxy(VABPathTools.concatenatePaths(SubModel.PROPERTIES, s))));
			}
		}

		// Return result
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IOperation> getOperations() {
		// Store operations as map
		Map<String, Object> ops = new HashMap<>();

		// Create return value
		Map<String, IOperation> ret = new HashMap<>();

		// Sub model operation list
		Object smOpList = getProxy().getModelPropertyValue(SubModel.OPERATIONS);

		// RTTI check (c# specific)
		if (smOpList instanceof HashSet) {
			// Read values
			Collection<Map<String, Object>> operationNodes = (Collection<Map<String, Object>>) smOpList;

			// Convert to IOperation
			for (Map<String, Object> opNode : operationNodes) {
				String id = (String) opNode.get(Referable.IDSHORT);

				ConnectedOperation conOp = new ConnectedOperation(getProxy().getDeepProxy(VABPathTools.concatenatePaths(SubModel.OPERATIONS, id)));
				// Cache operation properties
				conOp.putAllLocal(opNode);
				ret.put(id, conOp);
			}
		} else {
			// Operations already arrive as Map (java specific)
			ops = (Map<String, Object>) smOpList;

			for (String s : ops.keySet()) {
				ret.put(s, new ConnectedOperation(getProxy().getDeepProxy(VABPathTools.concatenatePaths(SubModel.OPERATIONS, s))));
			}
		}

		// Return result
		return ret;
	}

}
