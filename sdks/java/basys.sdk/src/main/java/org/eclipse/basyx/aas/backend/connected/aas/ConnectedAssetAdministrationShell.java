package org.eclipse.basyx.aas.backend.connected.aas;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.basyx.aas.api.metamodel.aas.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.metamodel.aas.ISubModel;
import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.api.metamodel.aas.parts.IConceptDictionary;
import org.eclipse.basyx.aas.api.metamodel.aas.parts.IView;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.security.ISecurity;
import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connected.ConnectedVABModelMap;
import org.eclipse.basyx.aas.impl.metamodel.facades.ConceptDictionaryFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.HasDataSpecificationFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.IdentifiableFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.ReferenceFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.SecurityFacade;
import org.eclipse.basyx.aas.impl.metamodel.facades.ViewFacade;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * "Connected" implementation of IAssetAdministrationShell
 * 
 * @author rajashek, Zai Zhang
 *
 */
public class ConnectedAssetAdministrationShell extends ConnectedVABModelMap<Object> implements IAssetAdministrationShell {

	ConnectedAssetAdministrationShellManager manager;

	/**
	 * Constructor creating a ConnectedAAS pointing to the AAS represented by proxy
	 * and path
	 * 
	 * @param path
	 * @param proxy
	 * @param manager
	 */
	public ConnectedAssetAdministrationShell(VABElementProxy proxy, ConnectedAssetAdministrationShellManager manager) {
		super(proxy);
		this.manager = manager;
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
		return new IdentifiableFacade(getElem()).getAdministration();
	}

	@Override
	public IIdentifier getIdentification() {
		return new IdentifiableFacade(getElem()).getIdentification();
	}

	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return new HasDataSpecificationFacade(getElem()).getDataSpecificationReferences();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ISecurity getSecurity() {
		return new SecurityFacade((Map<String, Object>) getElem().getPath(AssetAdministrationShell.SECURITY));
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getDerivedFrom() {
		return new ReferenceFacade((Map<String, Object>) getElem().getPath(AssetAdministrationShell.DERIVEDFROM));
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getAsset() {
		return new ReferenceFacade((Map<String, Object>) getElem().getPath(AssetAdministrationShell.ASSET));
	}

	@Override
	public void setSubModel(Set<IReference> submodels) {
		throwNotSupportedException();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IReference> getSubModel() {
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) getElem().getPath(AssetAdministrationShell.SUBMODEL);
		return set.stream().map(x -> new ReferenceFacade(x)).collect(Collectors.toSet());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IView> getViews() {
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) getElem().getPath(AssetAdministrationShell.VIEWS);
		return set.stream().map(x -> new ViewFacade(x)).collect(Collectors.toSet());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IConceptDictionary> getConceptDictionary() {
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) getElem().getPath(AssetAdministrationShell.CONCEPTDICTIONARY);
		return set.stream().map(x -> new ConceptDictionaryFacade(x)).collect(Collectors.toSet());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ISubModel> getSubModels() {

		Set<Map<?, ?>> submodels = null;
		Map<String, ISubModel> ret = new HashMap<>();

		submodels = (Set<Map<?, ?>>) getProxy().getModelPropertyValue(AssetAdministrationShell.SUBMODELS);
		for (Map<?, ?> submodelMap : submodels) {
			String id = (String) submodelMap.get("idShort");
			ret.put(id, new ConnectedSubModel(getProxy().getDeepProxy(AssetAdministrationShell.SUBMODELS + "/" + id)));
		}
		return ret;
	}

	@Override
	public void addSubModel(ISubModel subModel) {
		throwNotSupportedException();
	}

	@Override
	public String getIdshort() {
		return (String) getElem().get(Referable.IDSHORT);
	}

	@Override
	public String getCategory() {
		return (String) getElem().get(Referable.CATEGORY);
	}

	@Override
	public String getDescription() {
		return (String) getElem().get(Referable.DESCRIPTION);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getParent() {
		return new ReferenceFacade((Map<String, Object>) getElem().getPath(Referable.PARENT));
	}
}
