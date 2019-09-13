package org.eclipse.basyx.aas.impl.metamodel.hashmap.aas;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.metamodel.aas.ISubModel;
import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.api.metamodel.aas.parts.IConceptDictionary;
import org.eclipse.basyx.aas.api.metamodel.aas.parts.IView;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.security.ISecurity;
import org.eclipse.basyx.aas.impl.metamodel.facades.AssetAdministrationShellFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.HasDataSpecificationFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.IdentifiableFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.ReferableFacade;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.VABModelMap;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.parts.ConceptDictionary;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.parts.View;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.HasDataSpecification;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.reference.Key;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.reference.Reference;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.reference.enums.KeyElements;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.reference.enums.KeyType;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.security.Security;

/**
 * AssetAdministrationShell class <br/>
 * Does not implement IAssetAdministrationShell since there are only references
 * stored in this map
 * 
 * @author kuhn, schnicke
 *
 */

public class AssetAdministrationShell extends VABModelMap<Object> implements IAssetAdministrationShell {

	public static final String SECURITY = "security";
	public static final String DERIVEDFROM = "derivedFrom";
	public static final String ASSET = "asset";
	public static final String SUBMODEL = "submodel";
	public static final String SUBMODELS = "submodels";
	public static final String VIEWS = "views";
	public static final String CONCEPTDICTIONARY = "conceptDictionary";
	public static final String TYPE = "type";
	public static final String ADDRESS = "address";
	public static final String ENDPOINTS = "endpoints";
	public static final String IDSEMANTICS = "id_semantics";

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
		put(SECURITY, null);
		put(DERIVEDFROM, null);
		put(ASSET, null);
		put(SUBMODEL, new HashSet<Reference>());
		put(SUBMODELS, new HashSet<SubmodelDescriptor>());
		put(VIEWS, new HashSet<View>());
		put(CONCEPTDICTIONARY, new HashSet<ConceptDictionary>());
	}

	public AssetAdministrationShell(Reference derivedFrom, Security security, Reference asset, Set<Reference> submodels, Set<ConceptDictionary> dictionaries, Set<View> views) {
		// Add qualifiers
		putAll(new Identifiable());
		putAll(new HasDataSpecification());

		// Add attributes
		put(SECURITY, security);
		put(DERIVEDFROM, derivedFrom);
		put(ASSET, asset);
		put(SUBMODEL, submodels);
		put(VIEWS, views);
		put(CONCEPTDICTIONARY, dictionaries);
	}

	public void setEndpoint(String endpoint, String endpointType) {
		HashMap<String, String> endpointWrapper = new HashMap<String, String>();
		endpointWrapper.put(TYPE, endpointType);
		endpointWrapper.put(ADDRESS, endpoint + "/aas");

		put(ENDPOINTS, Arrays.asList(endpointWrapper));
	}

	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> getEndpoints() {
		return (List<HashMap<String, String>>) get(ENDPOINTS);
	}

	/**
	 * Add a submodel as reference
	 */
	@Override
	public void addSubModel(ISubModel subModel) {
		System.out.println("adding Submodel " + subModel.getId());
		addSubModel(subModel.getId());
	}

	@SuppressWarnings("unchecked")
	public void addSubModelHack(SubModel subModel, String endpoint, String endpointType) {
		System.out.println("adding Submodel " + subModel.getId());
		SubmodelDescriptor desc = new SubmodelDescriptor(subModel, endpoint, endpointType);
		((Set<SubmodelDescriptor>) get(SUBMODELS)).add(desc);

	}

	@SuppressWarnings("unchecked")
	public Set<SubmodelDescriptor> getSubmodelDescriptors() {
		return ((Set<SubmodelDescriptor>) get(SUBMODELS));
	}

	@SuppressWarnings("unchecked")
	public void addSubModel(String id) {
		((Set<Reference>) get(SUBMODEL)).add(new Reference(Collections.singletonList(new Key(KeyElements.Submodel, false, id, KeyType.Custom))));
	}

	@Override
	public IAdministrativeInformation getAdministration() {
		return new IdentifiableFacade(this).getAdministration();
	}

	@Override
	public IIdentifier getIdentification() {
		return new IdentifiableFacade(this).getIdentification();
	}

	public void setAdministration(String version, String revision) {
		new IdentifiableFacade(this).setAdministration(version, revision);
	}

	public void setIdentification(String idType, String id) {
		new IdentifiableFacade(this).setIdentification(idType, id);
	}

	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return new HasDataSpecificationFacade(this).getDataSpecificationReferences();
	}

	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		new HasDataSpecificationFacade(this).setDataSpecificationReferences(ref);
	}

	@Override
	public String getId() {
		return new AssetAdministrationShellFacade(this).getId();
	}

	@Override
	public void setId(String id) {
		new AssetAdministrationShellFacade(this).setId(id);
	}

	public void setSecurity(ISecurity security) {
		new AssetAdministrationShellFacade(this).setSecurity(security);
	}

	@Override
	public ISecurity getSecurity() {
		return new AssetAdministrationShellFacade(this).getSecurity();
	}

	public void setDerivedFrom(IReference derivedFrom) {
		new AssetAdministrationShellFacade(this).setDerivedFrom(derivedFrom);
	}

	@Override
	public IReference getDerivedFrom() {
		return new AssetAdministrationShellFacade(this).getDerivedFrom();
	}

	public void setAsset(IReference asset) {
		new AssetAdministrationShellFacade(this).setAsset(asset);
	}

	@Override
	public IReference getAsset() {
		return new AssetAdministrationShellFacade(this).getAsset();
	}

	@Override
	public void setSubModel(Set<IReference> submodels) {
		new AssetAdministrationShellFacade(this).setSubModel(submodels);
	}

	@Override
	public Set<IReference> getSubModel() {
		return new AssetAdministrationShellFacade(this).getSubModel();
	}

	public void setViews(Set<IView> views) {
		new AssetAdministrationShellFacade(this).setViews(views);
	}

	@Override
	public Set<IView> getViews() {
		return new AssetAdministrationShellFacade(this).getViews();
	}

	public void setConceptDictionary(Set<IConceptDictionary> dictionaries) {
		new AssetAdministrationShellFacade(this).setConceptDictionary(dictionaries);
	}

	@Override
	public Set<IConceptDictionary> getConceptDictionary() {
		return new AssetAdministrationShellFacade(this).getConceptDictionary();
	}

	@Override
	public Map<String, ISubModel> getSubModels() {
		return new AssetAdministrationShellFacade(this).getSubModels();
	}

	@Override
	public String getIdshort() {
		return new ReferableFacade(this).getIdshort();
	}

	@Override
	public String getCategory() {
		return new ReferableFacade(this).getCategory();
	}

	@Override
	public String getDescription() {
		return new ReferableFacade(this).getDescription();
	}

	@Override
	public IReference getParent() {
		return new ReferableFacade(this).getParent();
	}

	public void setIdshort(String idShort) {
		new ReferableFacade(this).setIdshort(idShort);
	}

	public void setCategory(String category) {
		new ReferableFacade(this).setCategory(category);

	}

	public void setDescription(String description) {
		new ReferableFacade(this).setDescription(description);
	}

	public void setParent(IReference obj) {
		new ReferableFacade(this).setParent(obj);
	}
}
