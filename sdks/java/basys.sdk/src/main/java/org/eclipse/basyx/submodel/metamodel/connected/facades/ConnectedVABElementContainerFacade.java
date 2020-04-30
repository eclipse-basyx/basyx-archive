package org.eclipse.basyx.submodel.metamodel.connected.facades;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.IElementContainer;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedVABModelMap;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElementFactory;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Facade providing access to a remote VABElementContainer
 * 
 * @author schnicke
 *
 */
public class ConnectedVABElementContainerFacade extends ConnectedVABModelMap<Object> implements IElementContainer {
	public ConnectedVABElementContainerFacade(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public void addSubModelElement(ISubmodelElement element) {
		if (element instanceof IDataElement) {
			getProxy().createValue(SubModel.PROPERTIES, element);
		} else if (element instanceof IOperation) {
			getProxy().createValue(SubModel.OPERATIONS, element);
		} else if (element instanceof ISubmodelElement) {
			getProxy().createValue(SubModel.SUBMODELELEMENT, element);
		}
	}

	@Override
	public Map<String, IDataElement> getDataElements() {
		return ConnectedSubmodelElementFactory.getDataElements(getProxy().getDeepProxy(SubModel.PROPERTIES));
	}

	@Override
	public Map<String, IOperation> getOperations() {
		return ConnectedSubmodelElementFactory.getOperations(getProxy().getDeepProxy(SubModel.OPERATIONS));
	}

	@Override
	public Map<String, ISubmodelElement> getSubmodelElements() {
		return ConnectedSubmodelElementFactory.getConnectedSubmodelElements(
				getProxy().getDeepProxy(SubModel.SUBMODELELEMENT));
	}

}
