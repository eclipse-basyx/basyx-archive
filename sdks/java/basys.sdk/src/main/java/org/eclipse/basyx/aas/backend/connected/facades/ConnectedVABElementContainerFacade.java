package org.eclipse.basyx.aas.backend.connected.facades;

import java.util.Collection;
import java.util.HashMap;
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

}
