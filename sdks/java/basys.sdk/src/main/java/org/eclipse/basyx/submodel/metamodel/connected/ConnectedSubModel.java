package org.eclipse.basyx.submodel.metamodel.connected;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.dataspecification.IEmbeddedDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElementFactory;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.AdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifiable;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.restapi.SubmodelElementProvider;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;


/**
 * "Connected" implementation of SubModel
 * 
 * @author rajashek
 *
 */
public class ConnectedSubModel extends ConnectedElement implements ISubModel {

	public ConnectedSubModel(VABElementProxy proxy) {
		super(proxy);
	}

	protected KeyElements getKeyElement() {
		return KeyElements.SUBMODEL;
	}

	@Override
	public IReference getSemanticId() {
		return HasSemantics.createAsFacade(getElem()).getSemanticId();
	}

	@Override
	public IAdministrativeInformation getAdministration() {
		return Identifiable.createAsFacade(getElem(), getKeyElement()).getAdministration();
	}

	@Override
	public IIdentifier getIdentification() {
		return Identifiable.createAsFacade(getElem(), getKeyElement()).getIdentification();
	}

	@Override
	public Collection<IReference> getDataSpecificationReferences() {
		return HasDataSpecification.createAsFacade(getElem()).getDataSpecificationReferences();
	}

	@Override
	public Collection<IEmbeddedDataSpecification> getEmbeddedDataSpecifications() {
		return HasDataSpecification.createAsFacade(getElem()).getEmbeddedDataSpecifications();
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
		return Referable.createAsFacade(getElem(), getKeyElement()).getDescription();
	}

	@Override
	public IReference getParent() {
		return Referable.createAsFacade(getElem(), getKeyElement()).getParent();
	}
	
	@Override
	public Collection<IConstraint> getQualifiers() {
		return Qualifiable.createAsFacade(getElem()).getQualifiers();
	}

	@Override
	public void addSubModelElement(ISubmodelElement element) {
		if (element instanceof SubmodelElement) {
			((SubmodelElement) element).setParent(getReference());
		}
		
		if (element instanceof IDataElement) {
			getProxy().createValue(SubmodelElementProvider.PROPERTIES, element);
		} else if (element instanceof IOperation) {
			getProxy().createValue(SubmodelElementProvider.OPERATIONS, element);
		} else if (element instanceof ISubmodelElement) {
			getProxy().createValue(SubmodelElementProvider.ELEMENTS, element);
		}
	}

	@Override
	public Map<String, IProperty> getProperties() {
		return ConnectedSubmodelElementFactory.getProperties(getProxy(), SubmodelElementProvider.PROPERTIES,
						SubmodelElementProvider.PROPERTIES);
	}

	@Override
	public Map<String, IOperation> getOperations() {
		return ConnectedSubmodelElementFactory.getOperations(getProxy(), SubmodelElementProvider.OPERATIONS,
				SubmodelElementProvider.OPERATIONS);
	}

	@Override
	public Map<String, ISubmodelElement> getSubmodelElements() {
		return ConnectedSubmodelElementFactory.getConnectedSubmodelElements(getProxy(),
				SubmodelElementProvider.ELEMENTS, SubmodelElementProvider.ELEMENTS);
	}

	@Override
	public IReference getReference() {
		return Identifiable.createAsFacade(getElem(), getKeyElement()).getReference();
	}

	/**
	 * Returns a local copy of the submodel, i.e. a snapshot of the current state.
	 * <br>
	 * No changes of this copy are reflected in the remote Submodel
	 * 
	 * @return the local copy
	 */
	public SubModel getLocalCopy() {
		return SubModel.createAsFacade(getElem());
	}
}
