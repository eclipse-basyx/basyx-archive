package org.eclipse.basyx.submodel.metamodel.connected.submodelelement;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.restapi.SubmodelElementMapProvider;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;

/**
 * "Connected" implementation of SubmodelElementCollection
 * 
 * @author rajashek
 *
 */
public class ConnectedSubmodelElementCollection extends ConnectedSubmodelElement implements ISubmodelElementCollection {
	public ConnectedSubmodelElementCollection(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public Collection<ISubmodelElement> getValue() {
		String elementPath = VABPathTools.concatenatePaths(Property.VALUE, SubmodelElementMapProvider.ELEMENTS);
		VABElementProxy proxy = getProxy().getDeepProxy(elementPath);
		return ConnectedSubmodelElementFactory.getElementCollection(proxy);
	}

	@Override
	public boolean isOrdered() {
		return (boolean) getElem().getPath(SubmodelElementCollection.ORDERED);
	}

	@Override
	public boolean isAllowDuplicates() {
		return (boolean) getElem().getPath(SubmodelElementCollection.ALLOWDUPLICATES);
	}

	@Override
	public Map<String, ISubmodelElement> getSubmodelElements() {
		// FIXME: This is a hack, fix this when API is clear
		String elementPath = VABPathTools.concatenatePaths(Property.VALUE, SubmodelElementMapProvider.ELEMENTS);
		VABElementProxy proxy = getProxy().getDeepProxy(elementPath);
		return ConnectedSubmodelElementFactory.getConnectedSubmodelElements(proxy);
	}

	@Override
	public Map<String, IDataElement> getDataElements() {
		// FIXME: This is a hack, fix this when API is clear
		String elementPath = VABPathTools.concatenatePaths(Property.VALUE, SubmodelElementMapProvider.DATAELEMENTS);
		VABElementProxy proxy = getProxy().getDeepProxy(elementPath);
		return ConnectedSubmodelElementFactory.getDataElements(proxy);
	}

	@Override
	public Map<String, IOperation> getOperations() {
		// FIXME: This is a hack, fix this when API is clear
		String elementPath = VABPathTools.concatenatePaths(Property.VALUE, SubmodelElementMapProvider.OPERATIONS);
		VABElementProxy proxy = getProxy().getDeepProxy(elementPath);
		return ConnectedSubmodelElementFactory.getOperations(proxy);
	}
}
