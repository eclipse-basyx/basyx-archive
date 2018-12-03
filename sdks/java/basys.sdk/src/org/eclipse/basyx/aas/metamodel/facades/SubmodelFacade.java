package org.eclipse.basyx.aas.metamodel.facades;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel_;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.AdministrativeInformation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identification;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.SemanticDescription;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;



/**
 * Base class for sub model facades
 * 
 * @author kuhn
 *
 */
public class SubmodelFacade extends VABHashmapProvider {
	
	
	/**
	 * Constructor
	 */
	public SubmodelFacade() {
		// Instantiate VAB HashMap provider with sub model instance
		super(new SubModel_());
	}
	
	
	/**
	 * Return reference to sub model structure
	 */
	public SubModel_ getSubModel() {
		// Assume that VAB HashMap provider carries a sub model
		return (SubModel_) this.getElements();
	}

	
	/**
	 * Get value of 'idShort' property
	 */
	public String getIDShort() {
		return (String) elements.get("idShort");
	}

	/**
	 * Update value of 'idShort' property
	 */
	public void setIDShort(String newValue) {
		elements.put("idShort", newValue);
	}
	
	

	/**
	 * Get value of 'category' property
	 */
	public String getCategory() {
		return (String) elements.get("category");
	}
	
	/**
	 * Update value of 'category' property
	 */
	public void setCategory(String newValue) {
		elements.put("category", newValue);
	}

	
	
	/**
	 * Get value of 'description' property
	 */
	public String getDescription() {
		return (String) elements.get("description");
	}
	
	/**
	 * Update value of 'description' property
	 */
	public void setDescription(String newValue) {
		elements.put("description", newValue);
	}

	
	
	/**
	 * Get value of 'parent' property
	 */
	public String getParent() {
		return (String) elements.get("parent");
	}
	
	/**
	 * Update value of 'parent' property
	 */
	public void setParent(String newValue) {
		elements.put("parent", newValue);
	}
	
	
	
	/**
	 * Get value of 'administration' property
	 */
	public AdministrativeInformation getAdministration() {
		return (AdministrativeInformation) elements.get("administration");
	}
	
	/**
	 * Update value of 'administration' property
	 */
	public void setAdministration(AdministrativeInformation newValue) {
		elements.put("administration", newValue);
	}
	

	
	/**
	 * Get value of 'identification' property
	 */
	public Identification getIdentification() {
		return (Identification) elements.get("identification");
	}
	
	/**
	 * Update value of 'identification' property
	 */
	public void setIdentification(Identification newValue) {
		elements.put("identification", newValue);
	}
	
	

	/**
	 * Get value of 'id_semantics' property
	 */
	public Identification getIdSemantics() {
		return (Identification) elements.get("id_semantics");
	}
	
	/**
	 * Update value of 'id_semantics' property
	 */
	public void setIdSemantics(Identification newValue) {
		elements.put("id_semantics", newValue);
	}

	
	
	/**
	 * Get value of 'hasFullSemanticDescription' property
	 */
	@SuppressWarnings("unchecked")
	public HashSet<SemanticDescription> getHasFullSemanticDescription() {
		return (HashSet<SemanticDescription>) elements.get("hasFullSemanticDescription");
	}
	
	/**
	 * Update value of 'hasFullSemanticDescription' property
	 */
	public void setHasFullSemanticDescription(HashSet<SemanticDescription> newValue) {
		elements.put("hasFullSemanticDescription", newValue);
	}



	/**
	 * Get value of 'qualifier' property
	 */
	@SuppressWarnings("unchecked")
	public Collection<String> getQualifier() {
		return (Collection<String>) elements.get("qualifier");
	}
	
	/**
	 * Update value of 'qualifier' property
	 */
	public void setQualifier(Collection<String> newValue) {
		elements.put("qualifier", newValue);
	}



	/**
	 * Get value of 'kind' property
	 */
	public int getKind() {
		return (Integer) elements.get("kind");
	}
	
	/**
	 * Update value of 'kind' property
	 */
	public void setKind(int newValue) {
		elements.put("kind", newValue);
	}
}

