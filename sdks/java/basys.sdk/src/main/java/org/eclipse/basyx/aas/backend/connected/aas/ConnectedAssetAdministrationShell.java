package org.eclipse.basyx.aas.backend.connected.aas;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.api.metamodel.aas.parts.IConceptDictionary;
import org.eclipse.basyx.aas.api.metamodel.aas.parts.IView;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.security.ISecurity;
import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connected.ConnectedVABModelMap;
import org.eclipse.basyx.aas.backend.connected.aas.reference.ConnectedReference;
import org.eclipse.basyx.aas.backend.connected.aas.security.ConnectedSecurity;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasDataSpecificationFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedIdentifiableFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;
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
	public ConnectedAssetAdministrationShell(VABElementProxy proxy,
			ConnectedAssetAdministrationShellManager manager) {
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
		return new ConnectedIdentifiableFacade(getProxy()).getAdministration();
	}

	@Override
	public IIdentifier getIdentification() {
		return new ConnectedIdentifiableFacade(getProxy()).getIdentification();
	}

	@Override
	public void setAdministration(String version, String revision) {
		new ConnectedIdentifiableFacade(getProxy()).setAdministration(version, revision);
		
	}

	@Override
	public void setIdentification(String idType, String id) {
		new ConnectedIdentifiableFacade(getProxy()).setIdentification(idType, id);
		
	}
	
	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return new ConnectedHasDataSpecificationFacade(getProxy()).getDataSpecificationReferences();
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		new ConnectedHasDataSpecificationFacade(getProxy()).setDataSpecificationReferences(ref);
		
	}
	
	@Override
	public void setSecurity(ISecurity security) {
		getProxy().setModelPropertyValue(AssetAdministrationShell.SECURITY, security);
		
	}

	@Override
	public ISecurity getSecurity() {
		return new ConnectedSecurity(getProxy().getDeepProxy(AssetAdministrationShell.SECURITY));
	}

	@Override
	public void setDerivedFrom(IReference derivedFrom) {
		getProxy().setModelPropertyValue(AssetAdministrationShell.DERIVEDFROM, derivedFrom);
		
	}

	@Override
	public IReference getDerivedFrom() {
		return new ConnectedReference(getProxy().getDeepProxy(AssetAdministrationShell.DERIVEDFROM));
	}

	@Override
	public void setAsset(IReference asset) {
		getProxy().setModelPropertyValue(AssetAdministrationShell.ASSET, asset);
		
	}

	@Override
	public IReference getAsset() {
		return new ConnectedReference(getProxy().getDeepProxy(AssetAdministrationShell.ASSET));
	}

	@Override
	public void setSubModel(Set<IReference> submodels) {
		getProxy().setModelPropertyValue(AssetAdministrationShell.SUBMODEL, submodels);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IReference> getSubModel() {
		return (Set<IReference>) getProxy().getModelPropertyValue(AssetAdministrationShell.SUBMODEL);
	}

	@Override
	public void setViews(Set<IView> views) {
		getProxy().setModelPropertyValue(AssetAdministrationShell.VIEWS, views);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IView> getViews() {
		return (Set<IView>) getProxy().getModelPropertyValue(AssetAdministrationShell.VIEWS);
	}

	@Override
	public void setConceptDictionary(Set<IConceptDictionary> dictionaries) {
		getProxy().setModelPropertyValue(AssetAdministrationShell.CONCEPTDICTIONARY, dictionaries);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IConceptDictionary> getConceptDictionary() {
		return (Set<IConceptDictionary>) getProxy().getModelPropertyValue(AssetAdministrationShell.CONCEPTDICTIONARY);
	}
	
	@Override
	public String getId() {
		return (String) getProxy().getModelPropertyValue(Referable.IDSHORT);
	}

	@Override
	public void setId(String id) {
		getProxy().setModelPropertyValue(Referable.IDSHORT, id);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ISubModel> getSubModels() {
		
		Set<Map<?, ?>> refs = null;
		Map<String, ISubModel> ret = new HashMap<>();

		try {
			// Java getSubmodels
			refs = (Set<Map<?, ?>>) getProxy().getModelPropertyValue(AssetAdministrationShell.SUBMODEL);
			for (Map<?, ?> key : refs) {
				String id = (String) ((Map<?, ?>) ((List<?>) key.get("keys")).get(0)).get("value");
				ISubModel sm = manager.retrieveSubModel(new ModelUrn(getId()), id);
				ret.put(id, sm);
			}
		} catch (ClassCastException e) {
			System.out.println("Cast failed... trying c# get submodels");
			// c# getSubmodels
			refs = (Set<Map<?, ?>>) getProxy().getModelPropertyValue(AssetAdministrationShell.SUBMODELS);
			for (Map<?, ?> key : refs) {
				String id = (String) key.get("idShort");
				ISubModel sm = manager.retrieveSubModel(new ModelUrn(getId()), id);
				ret.put(id, sm);
			}
		}
		

		return ret;
	}


	@Override
	public void addSubModel(ISubModel subModel) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getIdshort() {
		return (String) getProxy().getModelPropertyValue(Referable.IDSHORT);
	}

	@Override
	public String getCategory() {
		return (String) getProxy().getModelPropertyValue(Referable.CATEGORY);
	}

	@Override
	public String getDescription() {
		return (String) getProxy().getModelPropertyValue(Referable.DESCRIPTION);
	}

	@Override
	public IReference  getParent() {
		return new ConnectedReference(getProxy().getDeepProxy(Referable.PARENT));
	}

	@Override
	public void setIdshort(String idShort) {
		getProxy().setModelPropertyValue(Referable.IDSHORT, idShort);
		
	}

	@Override
	public void setCategory(String category) {
		getProxy().setModelPropertyValue(Referable.CATEGORY, category);
		
	}

	@Override
	public void setDescription(String description) {
		getProxy().setModelPropertyValue(Referable.DESCRIPTION, description);
		
	}

	@Override
	public void setParent(IReference  obj) {
		getProxy().setModelPropertyValue(Referable.PARENT, obj);
		
	}
}
