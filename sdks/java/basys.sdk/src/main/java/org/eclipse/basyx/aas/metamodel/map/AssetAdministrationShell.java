package org.eclipse.basyx.aas.metamodel.map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.api.parts.IConceptDictionary;
import org.eclipse.basyx.aas.metamodel.api.parts.IView;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.IAsset;
import org.eclipse.basyx.aas.metamodel.api.security.ISecurity;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.aas.metamodel.map.parts.ConceptDictionary;
import org.eclipse.basyx.aas.metamodel.map.parts.View;
import org.eclipse.basyx.aas.metamodel.map.security.Security;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.parts.IConceptDescription;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.AdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.reference.ReferenceHelper;
import org.eclipse.basyx.vab.model.VABModelMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AssetAdministrationShell class <br/>
 * Does not implement IAssetAdministrationShell since there are only references
 * stored in this map
 * 
 * @author kuhn, schnicke
 *
 */

public class AssetAdministrationShell extends VABModelMap<Object> implements IAssetAdministrationShell {
	private static Logger logger = LoggerFactory.getLogger(AssetAdministrationShell.class);

	public static final String SECURITY = "security";
	public static final String DERIVEDFROM = "derivedFrom";
	public static final String ASSET = "asset";
	public static final String ASSETREF = "assetRef"; // Currently not standard conforming
	public static final String SUBMODELS = "submodels"; // Used for storing keys to conform to the standard
	public static final String SUBMODELDESCRIPTORS = "submodelDescriptors";
	public static final String VIEWS = "views";
	public static final String CONCEPTDICTIONARY = "conceptDictionary";
	public static final String TYPE = "type";
	public static final String ADDRESS = "address";
	public static final String ENDPOINTS = "endpoints";
	public static final String MODELTYPE = "AssetAdministationShell";

	/**
	 * Constructor
	 */
	public AssetAdministrationShell() {
		this(null, null, new Asset(), new HashSet<SubmodelDescriptor>(), new HashSet<IConceptDictionary>(), new HashSet<IView>());
	}

	public AssetAdministrationShell(Reference derivedFrom, Security security, Asset asset, Set<SubmodelDescriptor> submodels, Set<IConceptDictionary> dictionaries, Set<IView> views) {
		// Add model type
		putAll(new ModelType(MODELTYPE));
		
		// Add qualifiers
		putAll(new Identifiable());
		putAll(new HasDataSpecification());

		setSubmodelReferences(new HashSet<IReference>());

		// Add attributes
		setSecurity(security);
		setDerivedFrom(derivedFrom);
		setAsset(asset);
		setSubModels(submodels);

		setViews(views);
		setConceptDictionary(dictionaries);
	}

	/**
	 * Creates a AssetAdministrationShell object from a map
	 * 
	 * @param obj
	 *            a AssetAdministrationShell object as raw map
	 * @return a AssetAdministrationShell object, that behaves like a facade for the
	 *         given map
	 */
	public static AssetAdministrationShell createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		AssetAdministrationShell ret = new AssetAdministrationShell();
		ret.setMap(map);
		return ret;
	}

	/**
	 * Sets the endpoint of the AAS
	 * 
	 * @param endpoint
	 *            is expected to end with "/aas"
	 * @param endpointType
	 */
	public void setEndpoint(String endpoint, String endpointType) {
		HashMap<String, String> endpointWrapper = new HashMap<String, String>();
		endpointWrapper.put(TYPE, endpointType);
		endpointWrapper.put(ADDRESS, endpoint);

		put(ENDPOINTS, Arrays.asList(endpointWrapper));
	}

	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> getEndpoints() {
		return (List<HashMap<String, String>>) get(ENDPOINTS);
	}

	@Override
	public IAdministrativeInformation getAdministration() {
		return Identifiable.createAsFacade(this).getAdministration();
	}

	@Override
	public IIdentifier getIdentification() {
		return Identifiable.createAsFacade(this).getIdentification();
	}

	public void setAdministration(AdministrativeInformation information) {
		Identifiable.createAsFacade(this).setAdministration(information);
	}

	public void setIdentification(IIdentifier id) {
		setIdentification(id.getIdType(), id.getId());
	}

	public void setIdentification(IdentifierType idType, String id) {
		Identifiable.createAsFacade(this).setIdentification(idType, id);
	}

	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return HasDataSpecification.createAsFacade(this).getDataSpecificationReferences();
	}

	public void setDataSpecificationReferences(Set<IReference> ref) {
		HasDataSpecification.createAsFacade(this).setDataSpecificationReferences(ref);
	}

	public void setIdShort(String id) {
		Referable.createAsFacade(this).setIdShort(id);
	}

	public void setSecurity(ISecurity security) {
		put(AssetAdministrationShell.SECURITY, security);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ISecurity getSecurity() {
		return Security.createAsFacade((Map<String, Object>) get(AssetAdministrationShell.SECURITY));
	}

	public void setDerivedFrom(IReference derivedFrom) {
		put(AssetAdministrationShell.DERIVEDFROM, derivedFrom);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getDerivedFrom() {
		return Reference.createAsFacade((Map<String, Object>) get(AssetAdministrationShell.DERIVEDFROM));
	}

	public void setAsset(Asset asset) {
		put(AssetAdministrationShell.ASSET, asset);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IAsset getAsset() {
		return Asset.createAsFacade((Map<String, Object>) get(AssetAdministrationShell.ASSET));
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getAssetReference() {
		return Reference.createAsFacade((Map<String, Object>) get(ASSETREF));
	}

	public void setAssetReference(Reference ref) {
		put(ASSETREF, ref);
	}

	@SuppressWarnings("unchecked")
	public void setSubModels(Set<SubmodelDescriptor> submodels) {
		put(AssetAdministrationShell.SUBMODELDESCRIPTORS, submodels);

		// Clear submodel references and add new keys
		((Set<Reference>) get(SUBMODELS)).clear();
		submodels.stream().forEach(s -> addSubmodelReferences(s));
	}

	public void setViews(Set<IView> views) {
		put(AssetAdministrationShell.VIEWS, views);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<IView> getViews() {
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) get(AssetAdministrationShell.VIEWS);
		return set.stream().map(m -> View.createAsFacade(m)).collect(Collectors.toSet());
	}

	public void setConceptDictionary(Set<IConceptDictionary> dictionaries) {
		put(AssetAdministrationShell.CONCEPTDICTIONARY, dictionaries);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<IConceptDictionary> getConceptDictionary() {
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) get(AssetAdministrationShell.CONCEPTDICTIONARY);

		return set.stream().map(m -> ConceptDictionary.createAsFacade(m)).collect(Collectors.toSet());
	}

	@Override
	public Map<String, ISubModel> getSubModels() {
		throw new RuntimeException("getSubModels on local copy is not supported");
	}

	@Override
	public String getIdShort() {
		return Referable.createAsFacade(this).getIdShort();
	}

	@Override
	public String getCategory() {
		return Referable.createAsFacade(this).getCategory();
	}

	@Override
	public LangStrings getDescription() {
		return Referable.createAsFacade(this).getDescription();
	}

	@Override
	public IReference getParent() {
		return Referable.createAsFacade(this).getParent();
	}

	public void setCategory(String category) {
		Referable.createAsFacade(this).setCategory(category);
	}

	public void setDescription(LangStrings description) {
		Referable.createAsFacade(this).setDescription(description);
	}

	public void setParent(IReference obj) {
		Referable.createAsFacade(this).setParent(obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addSubModel(SubmodelDescriptor descriptor) {
		logger.trace("adding Submodel", descriptor.getIdentifier().getId());
		((Set<SubmodelDescriptor>) get(AssetAdministrationShell.SUBMODELDESCRIPTORS)).add(descriptor);
		addSubmodelReferences(descriptor);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<SubmodelDescriptor> getSubModelDescriptors() {
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) get(AssetAdministrationShell.SUBMODELDESCRIPTORS);
		return set.stream().map(m -> new SubmodelDescriptor(m)).collect(Collectors.toSet());
	}

	/**
	 * Allows addition of a concept description to the concept dictionary
	 * 
	 * @param description
	 */
	public void addConceptDescription(IConceptDescription description) {
		Set<IConceptDictionary> dictionaries = getConceptDictionary();
		if (dictionaries.isEmpty()) {
			dictionaries.add(new ConceptDictionary());
		}
		ConceptDictionary dictionary = (ConceptDictionary) dictionaries.iterator().next();
		dictionary.addConceptDescription(description);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<IReference> getSubmodelReferences() {
		return ReferenceHelper.transform((Set<Map<String, Object>>) get(SUBMODELS));
	}

	public void setSubmodelReferences(Set<IReference> references) {
		put(SUBMODELS, references);
	}

	public void addSubmodelReference(IReference reference) {
		getSubmodelReferencesAsRawSet().add(reference);
	}

	private void addSubmodelReferences(SubmodelDescriptor descriptor) {
		IIdentifier identifier = descriptor.getIdentifier();
		Reference ref = new Reference(identifier, KeyElements.SUBMODEL, true);
		addSubmodelReference(ref);
	}

	@SuppressWarnings("unchecked")
	private Set<Object> getSubmodelReferencesAsRawSet() {
		return ((Set<Object>) get(SUBMODELS));
	}
}
