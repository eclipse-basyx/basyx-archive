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
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElementFactory;
import org.eclipse.basyx.submodel.metamodel.facade.SubmodelElementMapCollectionConverter;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifiable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.restapi.MultiSubmodelElementProvider;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;


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

	/**
	 * Creates a ConnectedSubmodel based on a proxy and an already cached local copy
	 * 
	 * @param proxy
	 * @param localCopy
	 */
	public ConnectedSubModel(VABElementProxy proxy, SubModel localCopy) {
		super(proxy);
		cached = localCopy;
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

	@SuppressWarnings("unchecked")
	@Override
	public void addSubModelElement(ISubmodelElement element) {
		String path = VABPathTools.concatenatePaths(MultiSubmodelElementProvider.ELEMENTS, element.getIdShort());

		if (element instanceof SubmodelElement) {
			((SubmodelElement) element).setParent(getReference());
			
			// Convert "value" in SubmodelElementCollection from Map to Collection
			if (element instanceof SubmodelElementCollection) {
				Map<String, Object> converted = SubmodelElementMapCollectionConverter.smElementToMap((Map<String, Object>) element);
				
				getProxy().setModelPropertyValue(path, converted);
				return;
			}
		}
		getProxy().setModelPropertyValue(path, element);
	}

	@Override
	public Map<String, IProperty> getProperties() {
		return ConnectedSubmodelElementFactory.getProperties(getProxy(), MultiSubmodelElementProvider.ELEMENTS,
						MultiSubmodelElementProvider.ELEMENTS);
	}

	@Override
	public Map<String, IOperation> getOperations() {
		return ConnectedSubmodelElementFactory.getOperations(getProxy(), MultiSubmodelElementProvider.ELEMENTS,
				MultiSubmodelElementProvider.ELEMENTS);
	}

	@Override
	public Map<String, ISubmodelElement> getSubmodelElements() {
		return ConnectedSubmodelElementFactory.getConnectedSubmodelElements(getProxy(),
				MultiSubmodelElementProvider.ELEMENTS, MultiSubmodelElementProvider.ELEMENTS);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getValues() {
		return (Map<String, Object>) getProxy().getModelPropertyValue(SubModelProvider.VALUES);
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

	/**
	 * Get submodel element by given id
	 * @param id
	 * @return specific submodel element
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ISubmodelElement getSubmodelElement(String id) {
		Map<String, Object> node =(Map<String, Object>) getProxy().getModelPropertyValue(VABPathTools.concatenatePaths(MultiSubmodelElementProvider.ELEMENTS, id));
		ISubmodelElement element = ConnectedSubmodelElementFactory.getConnectedSubmodelElement(getProxy(), MultiSubmodelElementProvider.ELEMENTS, id, node);
		return element;		
	}

	/**
	 * Delete a submodel element by given id
	 * @param id
	 */
	@Override
	public void deleteSubmodelElement(String id) {
		getProxy().deleteValue(VABPathTools.concatenatePaths(MultiSubmodelElementProvider.ELEMENTS, id));
	}
}
