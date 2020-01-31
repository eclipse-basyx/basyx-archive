package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.property;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.property.IContainerProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.property.PropertyType;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.connected.facades.ConnectedVABElementContainerFacade;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Connects to a ComplexDataProperty as specified by meta model. <br />
 * Not contained in DAAS meta model
 * 
 * @author schnicke
 *
 */
public class ConnectedContainerProperty extends ConnectedProperty implements IContainerProperty {

	ConnectedPropertyFactory factory = new ConnectedPropertyFactory();
	ConnectedVABElementContainerFacade facade;

	public ConnectedContainerProperty(VABElementProxy proxy) {
		super(PropertyType.Container, proxy);
		facade = new ConnectedVABElementContainerFacade(proxy);
	}

	@Override
	public void addSubModelElement(ISubmodelElement element) {
		facade.addSubModelElement(element);
	}

	@Override
	public Map<String, IDataElement> getDataElements() {
		return facade.getDataElements();
	}

	@Override
	public Map<String, IOperation> getOperations() {
		return facade.getOperations();
	}
	
	@Override
	public Map<String, ISubmodelElement> getSubmodelElements() {
		return facade.getSubmodelElements();
	}
}
