package org.eclipse.basyx.aas.metamodel.map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.api.parts.IAsset;
import org.eclipse.basyx.aas.metamodel.api.parts.IConceptDictionary;
import org.eclipse.basyx.aas.metamodel.api.parts.IView;
import org.eclipse.basyx.aas.metamodel.api.security.ISecurity;
import org.eclipse.basyx.aas.metamodel.facade.AssetAdministrationShellFacade;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.aas.metamodel.map.parts.ConceptDictionary;
import org.eclipse.basyx.aas.metamodel.map.parts.View;
import org.eclipse.basyx.aas.metamodel.map.security.Security;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasDataSpecificationFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.IdentifiableFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.ReferableFacade;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Description;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.vab.model.VABModelMap;

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
	public static final String SUBMODELS = "submodels";
	public static final String VIEWS = "views";
	public static final String CONCEPTDICTIONARY = "conceptDictionary";
	public static final String TYPE = "type";
	public static final String ADDRESS = "address";
	public static final String ENDPOINTS = "endpoints";
	public static final String IDSEMANTICS = "id_semantics";
	public static final String MODELTYPE = "AssetAdministationShell";

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public AssetAdministrationShell() {
		// Add model type
		putAll(new ModelType(MODELTYPE));

		// Add qualifiers
		putAll(new Identifiable());
		putAll(new HasDataSpecification());

		// Add attributes
		put(SECURITY, null);
		put(DERIVEDFROM, null);
		put(ASSET, new Asset());
		put(SUBMODELS, new HashSet<SubmodelDescriptor>());
		put(VIEWS, new HashSet<View>());
		put(CONCEPTDICTIONARY, new HashSet<ConceptDictionary>());
	}

	public AssetAdministrationShell(Reference derivedFrom, Security security, Asset asset, Set<SubmodelDescriptor> submodels, Set<ConceptDictionary> dictionaries, Set<View> views) {
		// Add qualifiers
		putAll(new Identifiable());
		putAll(new HasDataSpecification());

		// Add attributes
		put(SECURITY, security);
		put(DERIVEDFROM, derivedFrom);
		put(ASSET, asset);
		put(SUBMODELS, submodels);
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

	@SuppressWarnings("unchecked")
	public Set<SubmodelDescriptor> getSubmodelDescriptors() {
		return ((Set<SubmodelDescriptor>) get(SUBMODELS));
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

	public void setIdentification(IIdentifier id) {
		setIdentification(id.getIdType(), id.getId());
	}

	public void setIdentification(String idType, String id) {
		new IdentifiableFacade(this).setIdentification(idType, id);
	}

	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return new HasDataSpecificationFacade(this).getDataSpecificationReferences();
	}

	public void setDataSpecificationReferences(Set<IReference> ref) {
		new HasDataSpecificationFacade(this).setDataSpecificationReferences(ref);
	}

	@Override
	public void setIdShort(String id) {
		new AssetAdministrationShellFacade(this).setIdShort(id);
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
	public IAsset getAsset() {
		return new AssetAdministrationShellFacade(this).getAsset();
	}

	@Override
	public void setSubModels(Set<SubmodelDescriptor> submodels) {
		new AssetAdministrationShellFacade(this).setSubModels(submodels);
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
	public String getIdShort() {
		return new ReferableFacade(this).getIdShort();
	}

	@Override
	public String getCategory() {
		return new ReferableFacade(this).getCategory();
	}

	@Override
	public Description getDescription() {
		return new ReferableFacade(this).getDescription();
	}

	@Override
	public IReference getParent() {
		return new ReferableFacade(this).getParent();
	}

	public void setCategory(String category) {
		new ReferableFacade(this).setCategory(category);
	}

	public void setDescription(Description description) {
		new ReferableFacade(this).setDescription(description);
	}

	public void setParent(IReference obj) {
		new ReferableFacade(this).setParent(obj);
	}

	@Override
	public void addSubModel(SubmodelDescriptor descriptor) {
		new AssetAdministrationShellFacade(this).addSubModel(descriptor);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<SubmodelDescriptor> getSubModelDescriptors() {
		return (Set<SubmodelDescriptor>) get(SUBMODELS);
	}
}
