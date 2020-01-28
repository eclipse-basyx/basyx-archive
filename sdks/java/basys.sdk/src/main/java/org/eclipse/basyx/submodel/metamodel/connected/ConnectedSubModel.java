package org.eclipse.basyx.submodel.metamodel.connected;

import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.IElementContainer;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.connected.facades.ConnectedVABElementContainerFacade;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.property.ConnectedPropertyFactory;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.AdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifiable;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;


/**
 * "Connected" implementation of SubModel
 * 
 * @author rajashek
 *
 */
public class ConnectedSubModel extends ConnectedVABModelMap<Object> implements IElementContainer, ISubModel {

	ConnectedPropertyFactory factory = new ConnectedPropertyFactory();
	ConnectedVABElementContainerFacade facade;

	public ConnectedSubModel(VABElementProxy proxy) {
		super(proxy);
		facade = new ConnectedVABElementContainerFacade(proxy);
	}

	@Override
	public IReference getSemanticId() {
		return Reference.createAsFacade(getElem());
	}

	@Override
	public IAdministrativeInformation getAdministration() {
		return AdministrativeInformation.createAsFacade(getElem());
	}

	@Override
	public IIdentifier getIdentification() {
		return Identifiable.createAsFacade(getElem()).getIdentification();
	}

	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return HasDataSpecification.createAsFacade(getElem()).getDataSpecificationReferences();
	}

	@Override
	public ModelingKind getModelingKind() {
		return HasKind.createAsFacade(getElem()).getModelingKind();
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
	public LangStrings getDescription() {
		return Referable.createAsFacade(getElem()).getDescription();
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getParent() {
		return Reference.createAsFacade((Map<String, Object>) getElem().getPath(Referable.PARENT));
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
	
	@Override
	public Set<IConstraint> getQualifier() {
		return Qualifiable.createAsFacade(getElem()).getQualifier();
	}
}
