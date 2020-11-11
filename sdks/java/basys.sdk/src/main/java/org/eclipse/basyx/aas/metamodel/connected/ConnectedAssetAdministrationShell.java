package org.eclipse.basyx.aas.metamodel.connected;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.api.parts.IConceptDictionary;
import org.eclipse.basyx.aas.metamodel.api.parts.IView;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.IAsset;
import org.eclipse.basyx.aas.metamodel.api.security.ISecurity;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.aas.metamodel.map.parts.ConceptDictionary;
import org.eclipse.basyx.aas.metamodel.map.parts.View;
import org.eclipse.basyx.aas.metamodel.map.security.Security;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.dataspecification.IEmbeddedDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedElement;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubModel;
import org.eclipse.basyx.submodel.metamodel.facade.SubmodelElementMapCollectionConverter;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.reference.ReferenceHelper;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;

/**
 * "Connected" implementation of IAssetAdministrationShell
 * 
 * @author rajashek, Zai Zhang, schnicke
 *
 */
public class ConnectedAssetAdministrationShell extends ConnectedElement implements IAssetAdministrationShell {
	/**
	 * Constructor creating a ConnectedAAS pointing to the AAS represented by proxy
	 * 
	 * @param proxy
	 * @param manager
	 */
	@Deprecated
	public ConnectedAssetAdministrationShell(VABElementProxy proxy, ConnectedAssetAdministrationShellManager manager) {
		super(proxy);
	}

	/**
	 * Constructor creating a ConnectedAAS pointing to the AAS represented by proxy
	 * 
	 * @param proxy
	 */
	public ConnectedAssetAdministrationShell(VABElementProxy proxy) {
		super(proxy);
	}

	/**
	 * Constructor creating a ConnectedAAS pointing to the AAS represented by proxy
	 * and an already cached local copy
	 * 
	 * @param proxy
	 * @param localCopy
	 */
	public ConnectedAssetAdministrationShell(VABElementProxy proxy, AssetAdministrationShell localCopy) {
		super(proxy);
	}

	/**
	 * Copy constructor, allowing to create a ConnectedAAS pointing to the same AAS
	 * as <i>shell</i>
	 * 
	 * @param shell
	 */
	public ConnectedAssetAdministrationShell(ConnectedAssetAdministrationShell shell) {
		super(shell.getProxy());
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

	@SuppressWarnings("unchecked")
	@Override
	public ISecurity getSecurity() {
		return Security.createAsFacade((Map<String, Object>) getElem().getPath(AssetAdministrationShell.SECURITY));
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getDerivedFrom() {
		return Reference.createAsFacade((Map<String, Object>) getElem().getPath(AssetAdministrationShell.DERIVEDFROM));
	}

	@SuppressWarnings("unchecked")
	@Override
	public IAsset getAsset() {
		return Asset.createAsFacade((Map<String, Object>) getElem().getPath(AssetAdministrationShell.ASSET));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<IView> getViews() {
		Collection<Map<String, Object>> coll = (Collection<Map<String, Object>>) getElem()
				.getPath(AssetAdministrationShell.VIEWS);
		return coll.stream().map(View::createAsFacade).collect(Collectors.toSet());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<IConceptDictionary> getConceptDictionary() {
		Collection<Map<String, Object>> set = (Collection<Map<String, Object>>) getElem()
				.getPath(AssetAdministrationShell.CONCEPTDICTIONARY);
		return set.stream().map(ConceptDictionary::createAsFacade).collect(Collectors.toSet());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ISubModel> getSubModels() {
		Collection<Map<String, Object>> submodelCollection = (Collection<Map<String, Object>>) getProxy().getModelPropertyValue(AssetAdministrationShell.SUBMODELS);

		Map<String, ISubModel> ret = new HashMap<>();

		for (Map<String, Object> m : submodelCollection) {
			SubModel sm = SubModel.createAsFacade(m);
			String path = VABPathTools.concatenatePaths(AssetAdministrationShell.SUBMODELS, sm.getIdShort(), SubModelProvider.SUBMODEL);
			ret.put(sm.getIdShort(), new ConnectedSubModel(getProxy().getDeepProxy(path), sm));
		}

		return ret;
	}

	@Override
	public void addSubModel(SubModel subModel) {
		subModel.setParent(getReference());
		Map<String, Object> convertedMap = SubmodelElementMapCollectionConverter.smToMap(subModel);
		String accessPath = VABPathTools.concatenatePaths(AssetAdministrationShell.SUBMODELS, subModel.getIdShort());
		getProxy().setModelPropertyValue(accessPath, convertedMap);
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

	@SuppressWarnings("unchecked")
	@Override
	public IReference getParent() {
		return Reference.createAsFacade((Map<String, Object>) getElem().getPath(Referable.PARENT));
	}

	@Override
	public Collection<IReference> getSubmodelReferences() {
		return ReferenceHelper.transform(getElemLive().getPath(AssetAdministrationShell.SUBMODELS));
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getAssetReference() {
		return Reference.createAsFacade((Map<String, Object>) getElem().getPath(AssetAdministrationShell.ASSETREF));
	}
	
	private KeyElements getKeyElement() {
		return KeyElements.ASSETADMINISTRATIONSHELL;
	} 

	@Override
	public IReference getReference() {
		return Identifiable.createAsFacade(getElem(), getKeyElement()).getReference();
	}

	/**
	 * Returns a local copy of the AAS, i.e. a snapshot of the current state. <br>
	 * No changes of this copy are reflected in the remote AAS
	 * 
	 * @return the local copy
	 */
	public AssetAdministrationShell getLocalCopy() {
		return AssetAdministrationShell.createAsFacade(getElem());
	}

	@Override
	public void removeSubmodel(IIdentifier id) {
		ISubModel sm = getSubmodel(id);

		String path = VABPathTools.concatenatePaths(AssetAdministrationShell.SUBMODELS, sm.getIdShort());
		getProxy().deleteValue(path);
	}

	@Override
	public ISubModel getSubmodel(IIdentifier id) {
		// FIXME: Change this when AAS API supports Submodel retrieval by Identifier
		Optional<ISubModel> op = getSubModels().values().stream().filter(sm -> sm.getIdentification().getId().equals(id.getId())).findFirst();
		if (!op.isPresent()) {
			throw new ResourceNotFoundException("AAS " + getIdentification() + " does not have a submodel with id " + id);
		}
		return op.get();
	}
}
