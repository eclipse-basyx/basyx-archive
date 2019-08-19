package org.eclipse.basyx.aas.metamodel.facades;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.VABModelMap;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.Identifier;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.AdministrativeInformation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasDataSpecification;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.haskind.HasKind;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.qualifiable.Qualifier;



/**
 * Base class for sub model facades
 * 
 * @author kuhn
 *
 */
public class SubmodelFacade implements ISubModel {
	private VABModelMap<Object> map;

	public SubmodelFacade(VABModelMap<Object> map) {
		this.map = map;
	}

	/**
	 * Constructor
	 */
	public SubmodelFacade() {
		// Instantiate VAB HashMap provider with sub model instance
		map = new SubModel();
	}

	/**
	 * Constructor with an initial submodel
	 */
	public SubmodelFacade(SubModel submodel) {
		map = submodel;
	}

	/**
	 * Return reference to sub model structure
	 */
	public SubModel getSubModel() {
		// Assume that VAB HashMap provider carries a sub model
		return (SubModel) getElements();
	}

	/**
	 * Get value of 'idShort' property
	 */
	public String getIDShort() {
		return (String) getElements().get(Referable.IDSHORT);
	}

	/**
	 * Update value of 'idShort' property
	 */
	public void setIDShort(String newValue) {
		getElements().put(Referable.IDSHORT, newValue);
	}

	/**
	 * Get value of 'category' property
	 */
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
	public String getDescription() {
		return (String) getElements().get(Referable.DESCRIPTION);
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
	public IReference getParent() {
		return (IReference) getElements().get(Referable.PARENT);
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
	@Override
	public IAdministrativeInformation getAdministration() {
		return (IAdministrativeInformation) getElements().get(Identifiable.ADMINISTRATION);
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
	@Override
	public IIdentifier getIdentification() {
		return (IIdentifier) getElements().get(Identifiable.IDENTIFICATION);
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
	public Identifier getIdSemantics() {
		return (Identifier) getElements().get(AssetAdministrationShell.IDSEMANTICS);
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

	@Override
	public String getId() {
	return (String)map.get(Referable.IDSHORT);
	}

	@Override
	public void setId(String id) {
		map.put(Referable.IDSHORT, id);
		
	}

	@Override
	public IReference getSemanticId() {
		return (IReference)map.get(HasSemantics.SEMANTICID);
	}

	@Override
	public void setSemanticID(IReference ref) {
		map.put(HasSemantics.SEMANTICID, ref);
		
	}
	


	@Override
	public void setAdministration(String version, String revision) {
		map.put(Identifiable.ADMINISTRATION, new AdministrativeInformation(version, revision));
		
	}

	@Override
	public void setIdentification(String idType, String id) {
		map.put(Identifiable.IDENTIFICATION, new Identifier(idType, id));
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return (HashSet<IReference>) map.get(HasDataSpecification.HASDATASPECIFICATION);
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		map.put(HasDataSpecification.HASDATASPECIFICATION, ref);
		
	}

	@Override
	public String getHasKindReference() {
		return (String) map.get(HasKind.KIND);
	}

	@Override
	public void setHasKindReference(String kind) {
		map.put(HasKind.KIND, kind);
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IProperty> getProperties() {
		return (Map<String, IProperty>)map.get(SubModel.PROPERTIES);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IOperation> getOperations() {
		return (Map<String, IOperation>)map.get(SubModel.OPERATIONS);
	}

	@Override
	public void setProperties(Map<String, IProperty> properties) {
		map.put(SubModel.PROPERTIES,properties);
		
	}

	@Override
	public void setOperations(Map<String, IOperation> operations) {
		map.put(SubModel.OPERATIONS,operations);
		
	}

	@Override
	public Map<String, Object> getElements() {
		return map;
	}
	
	@Override
	public String getIdshort() {
		return (String) map.get(Referable.IDSHORT);
	}



	@Override
	public void setIdshort(String idShort) {
		map.put(Referable.IDSHORT, idShort);
		
	}



	@Override
	public void setParent(IReference  obj) {
		map.put(Referable.PARENT, obj);
		
	}
}
