package org.eclipse.basyx.submodel.metamodel.connected;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.connected.facades.ConnectedVABElementContainerFacade;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.property.ConnectedPropertyFactory;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.AdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
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
public class ConnectedSubModel extends ConnectedVABElementContainerFacade implements ISubModel {

	ConnectedPropertyFactory factory = new ConnectedPropertyFactory();

	public ConnectedSubModel(VABElementProxy proxy) {
		super(proxy);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getSemanticId() {
		return Reference.createAsFacade((Map<String, Object>) getElem().get(HasSemantics.SEMANTICID));
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
	public Collection<IReference> getDataSpecificationReferences() {
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
	public Collection<IConstraint> getQualifier() {
		return Qualifiable.createAsFacade(getElem()).getQualifier();
	}
}
