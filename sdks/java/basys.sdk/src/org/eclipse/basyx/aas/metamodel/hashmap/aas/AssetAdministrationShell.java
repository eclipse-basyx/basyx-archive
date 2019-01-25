package org.eclipse.basyx.aas.metamodel.hashmap.aas;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.basyx.aas.api.resources.IElement;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.VABModelMap;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.parts.ConceptDictionary;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.parts.View;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasDataSpecification;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Key;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.enums.KeyElements;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.enums.KeyType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.security.Security;
import org.eclipse.basyx.vab.core.ref.VABElementRef;

/**
 * AssetAdministrationShell class <br/>
 * Does not implement IAssetAdministrationShell since there are only references
 * stored in this map
 * 
 * @author kuhn, schnicke
 *
 */

public class AssetAdministrationShell extends VABModelMap<Object> implements IElement {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public AssetAdministrationShell() {
		// Add qualifiers
		putAll(new Identifiable());
		putAll(new HasDataSpecification());

		// Add attributes
		put("security", null);
		put("derivedFrom", null);
		put("asset", null);
		put("submodel", new HashSet<Reference>());
		put("views", new HashSet<View>());
		put("conceptDictionary", new HashSet<ConceptDictionary>());
	}

	public AssetAdministrationShell(Reference derivedFrom, Security security, Reference asset, Set<Reference> submodels, Set<ConceptDictionary> dictionaries, Set<View> views) {
		// Add qualifiers
		putAll(new Identifiable());
		putAll(new HasDataSpecification());

		// Add attributes
		put("security", security);
		put("derivedFrom", derivedFrom);
		put("asset", asset);
		put("submodel", submodels);
		put("views", views);
		put("conceptDictionary", dictionaries);
	}

	/**
	 * Get Submodels
	 */
	@SuppressWarnings("unchecked")
	public Set<VABElementRef> getSubModels() {
		return ((Set<Reference>) get("submodel")).stream().map(s -> {
			String id = s.getKeys().get(0).getValue();
			return new VABElementRef(id);
		}).collect(Collectors.toSet());
	}

	/**
	 * Add a submodel as reference
	 */
	public void addSubModel(ISubModel subModel) {
		System.out.println("adding Submodel " + subModel.getId());
		addSubModel(subModel.getId());
		System.out.println("added Submodel " + getSubModels());
	}

	@SuppressWarnings("unchecked")
	public void addSubModel(String id) {
		((Set<Reference>) get("submodel")).add(new Reference(Collections.singletonList(new Key(KeyElements.Submodel, false, id, KeyType.Custom))));
	}

	/**
	 * Get AAS Id
	 */
	@Override
	public String getId() {
		return (String) get("idShort");
	}

	/**
	 * Set AAS Id
	 */
	@Override
	public void setId(String id) {
		put("idShort", id);
	}

}
