package org.eclipse.basyx.aas.backend.connected.aas;

import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.ISubModel;
import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IDataElement;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.ISubmodelElement;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.operation.IOperation;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.IProperty;
import org.eclipse.basyx.aas.backend.connected.ConnectedVABModelMap;
import org.eclipse.basyx.aas.backend.connected.aas.submodelelement.property.ConnectedPropertyFactory;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedVABElementContainerFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.AdministrativeInformationFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.HasDataSpecificationFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.HasKindFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.IdentifierFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.ReferenceFacade;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.IVABElementContainer;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * "Connected" implementation of SubModel
 * 
 * @author rajashek
 *
 */
public class ConnectedSubModel extends ConnectedVABModelMap<Object> implements IVABElementContainer, ISubModel {

	ConnectedPropertyFactory factory = new ConnectedPropertyFactory();
	ConnectedVABElementContainerFacade facade;

	public ConnectedSubModel(VABElementProxy proxy) {
		super(proxy);
		facade = new ConnectedVABElementContainerFacade(proxy);
	}

	@Override
	public IReference getSemanticId() {
		return new ReferenceFacade(getElem());
	}

	@Override
	public IAdministrativeInformation getAdministration() {
		return new AdministrativeInformationFacade(getElem());
	}

	@Override
	public IIdentifier getIdentification() {
		return new IdentifierFacade(getElem());
	}

	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return new HasDataSpecificationFacade(getElem()).getDataSpecificationReferences();
	}

	@Override
	public String getHasKindReference() {
		return new HasKindFacade(getElem()).getHasKindReference();
	}

	@Override
	public void setProperties(Map<String, IProperty> properties) {
		throwNotSupportedException();
	}

	@Override
	public void setOperations(Map<String, IOperation> operations) {
		throwNotSupportedException();
	}

	@Override
	public String getIdshort() {
		return (String) getElem().get(Referable.IDSHORT);
	}

	@Override
	public String getCategory() {
		return (String) getElem().get(Referable.CATEGORY);
	}

	@Override
	public String getDescription() {
		return (String) getElem().get(Referable.DESCRIPTION);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getParent() {
		return new ReferenceFacade((Map<String, Object>) getElem().getPath(Referable.PARENT));
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
}
