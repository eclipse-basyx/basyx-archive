package org.eclipse.basyx.submodel.metamodel.connected;

import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.IProperty;
import org.eclipse.basyx.submodel.metamodel.connected.facades.ConnectedVABElementContainerFacade;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.property.ConnectedPropertyFactory;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.AdministrativeInformationFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasDataSpecificationFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.IdentifiableFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.ReferableFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.haskind.HasKindFacade;
import org.eclipse.basyx.submodel.metamodel.facade.reference.ReferenceFacade;
import org.eclipse.basyx.submodel.metamodel.map.IVABElementContainer;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Description;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

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
		return new IdentifiableFacade(getElem()).getIdentification();
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
	public String getIdShort() {
		return (String) getElem().get(Referable.IDSHORT);
	}

	@Override
	public String getCategory() {
		return (String) getElem().get(Referable.CATEGORY);
	}

	@Override
	public Description getDescription() {
		return new ReferableFacade(getElem()).getDescription();
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
