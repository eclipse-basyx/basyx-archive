package org.eclipse.basyx.submodel.metamodel.facade;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.IProperty;
import org.eclipse.basyx.submodel.metamodel.facade.identifier.IdentifierFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.AdministrativeInformationFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.ReferableFacade;
import org.eclipse.basyx.submodel.metamodel.facade.reference.ReferenceFacade;
import org.eclipse.basyx.submodel.metamodel.facade.reference.ReferenceHelper;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.AdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Description;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifier;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * Base class for sub model facades
 * 
 * @author kuhn
 *
 */
public class SubmodelFacade implements ISubModel {
	private VABModelMap<Object> map;
	private VABElementContainerFacade containerFacade;

	public SubmodelFacade(VABModelMap<Object> map) {
		this.map = map;
		containerFacade = new VABElementContainerFacade(map);
	}

	/**
	 * Constructor
	 */
	public SubmodelFacade() {
		// Instantiate VAB HashMap provider with sub model instance
		this(new SubModel());
	}

	/**
	 * Constructor with an initial submodel
	 */
	public SubmodelFacade(SubModel submodel) {
		this((VABModelMap<Object>) submodel);
	}

	/**
	 * Return reference to sub model structure FIXME: Why does this method exist?
	 */
	public SubModel getSubModel() {
		// Assume that VAB HashMap provider carries a sub model
		return (SubModel) getElements();
	}

	/**
	 * Get value of 'idShort' property
	 */
	@Override
	public String getIdShort() {
		return (String) getElements().get(Referable.IDSHORT);
	}

	/**
	 * Update value of 'idShort' property
	 */
	@Override
	public void setIdShort(String newValue) {
		getElements().put(Referable.IDSHORT, newValue);
	}

	/**
	 * Get value of 'category' property
	 */
	@Override
	public String getCategory() {
		return (String) getElements().get(Referable.CATEGORY);
	}

	/**
	 * Update value of 'category' property
	 */
	public void setCategory(String newValue) {
		getElements().put(Referable.CATEGORY, newValue);
	}

	/**
	 * Get value of 'description' property
	 */
	@Override
	public Description getDescription() {
		return new ReferableFacade(map).getDescription();
	}

	/**
	 * Update value of 'description' property
	 */
	public void setDescription(String newValue) {
		getElements().put(Referable.DESCRIPTION, newValue);
	}

	/**
	 * Get value of 'parent' property
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IReference getParent() {
		return new ReferenceFacade((Map<String, Object>) getElements().get(Referable.PARENT));
	}

	/**
	 * Update value of 'parent' property
	 */
	public void setParent(String newValue) {
		getElements().put(Referable.PARENT, newValue);
	}

	/**
	 * Get value of 'administration' property
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IAdministrativeInformation getAdministration() {
		return new AdministrativeInformationFacade((Map<String, Object>) getElements().get(Identifiable.ADMINISTRATION));
	}

	/**
	 * Update value of 'administration' property
	 */
	public void setAdministration(AdministrativeInformation newValue) {
		getElements().put(Identifiable.ADMINISTRATION, newValue);
	}

	/**
	 * Get value of 'identification' property
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IIdentifier getIdentification() {
		return new IdentifierFacade((Map<String, Object>) getElements().get(Identifiable.IDENTIFICATION));
	}

	/**
	 * Update value of 'identification' property
	 */
	public void setIdentification(Identifier newValue) {
		getElements().put(Identifiable.IDENTIFICATION, newValue);
	}

	/**
	 * Get value of 'id_semantics' property
	 */
	@SuppressWarnings("unchecked")
	public IIdentifier getIdSemantics() {
		return new IdentifierFacade((Map<String, Object>) getElements().get(AssetAdministrationShell.IDSEMANTICS));
	}

	/**
	 * Update value of 'id_semantics' property
	 */
	public void setIdSemantics(Identifier newValue) {
		getElements().put(AssetAdministrationShell.IDSEMANTICS, newValue);
	}

	/**
	 * Get value of 'qualifier' property
	 */
	@SuppressWarnings("unchecked")
	public Collection<String> getQualifier() {
		return (Collection<String>) getElements().get(Qualifier.QUALIFIER);
	}

	/**
	 * Update value of 'qualifier' property
	 */
	public void setQualifier(Collection<String> newValue) {
		getElements().put(Qualifier.QUALIFIER, newValue);
	}

	/**
	 * Get value of 'kind' property
	 */
	public String getKind() {
		return (String) getElements().get(HasKind.KIND);
	}

	/**
	 * Update value of 'kind' property
	 */
	public void setKind(int newValue) {
		getElements().put(HasKind.KIND, newValue);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getSemanticId() {
		return new ReferenceFacade((Map<String, Object>) map.get(HasSemantics.SEMANTICID));
	}

	public void setSemanticID(IReference ref) {
		map.put(HasSemantics.SEMANTICID, ref);
	}

	public void setAdministration(String version, String revision) {
		map.put(Identifiable.ADMINISTRATION, new AdministrativeInformation(version, revision));
	}

	public void setIdentification(String idType, String id) {
		map.put(Identifiable.IDENTIFICATION, new Identifier(idType, id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IReference> getDataSpecificationReferences() {
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) map.get(HasDataSpecification.HASDATASPECIFICATION);
		return ReferenceHelper.transform(set);
	}

	public void setDataSpecificationReferences(Set<IReference> ref) {
		map.put(HasDataSpecification.HASDATASPECIFICATION, ref);
	}

	@Override
	public String getHasKindReference() {
		return (String) map.get(HasKind.KIND);
	}

	public void setHasKindReference(String kind) {
		map.put(HasKind.KIND, kind);
	}

	@Override
	public void setProperties(Map<String, IProperty> properties) {
		map.put(SubModel.PROPERTIES, properties);

	}

	@Override
	public void setOperations(Map<String, IOperation> operations) {
		map.put(SubModel.OPERATIONS, operations);

	}

	private Map<String, Object> getElements() {
		return map;
	}

	public void setParent(IReference obj) {
		map.put(Referable.PARENT, obj);

	}

	@Override
	public void addSubModelElement(ISubmodelElement element) {
		containerFacade.addSubModelElement(element);
	}

	@Override
	public Map<String, IDataElement> getDataElements() {
		return containerFacade.getDataElements();
	}

	@Override
	public Map<String, IOperation> getOperations() {
		return containerFacade.getOperations();
	}

}
