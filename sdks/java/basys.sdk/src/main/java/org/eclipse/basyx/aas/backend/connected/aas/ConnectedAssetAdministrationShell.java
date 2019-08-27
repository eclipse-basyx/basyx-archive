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
	public ConnectedAssetAdministrationShell(String path, VABElementProxy proxy,
			ConnectedAssetAdministrationShellManager manager) {
		super(path, proxy);		
		this.manager = manager;
	}
	
	/**
	 * Copy constructor, allowing to create a ConnectedAAS pointing to the same AAS
	 * as <i>shell</i>
	 * 
	 * @param shell
	 */
	public ConnectedAssetAdministrationShell(ConnectedAssetAdministrationShell shell) {
		super(shell.getPath(), shell.getProxy());
	}
	
	@Override
	public IAdministrativeInformation getAdministration() {
		return new ConnectedIdentifiableFacade(getPath(),getProxy()).getAdministration();
	}

	@Override
	public IIdentifier getIdentification() {
		return new ConnectedIdentifiableFacade(getPath(),getProxy()).getIdentification();
	}

	@Override
	public void setAdministration(String version, String revision) {
		 new ConnectedIdentifiableFacade(getPath(),getProxy()).setAdministration(version, revision);
		
	}

	@Override
	public void setIdentification(String idType, String id) {
		 new ConnectedIdentifiableFacade(getPath(),getProxy()).setIdentification(idType, id);
		
	}
	
	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return new ConnectedHasDataSpecificationFacade(getPath(),getProxy()).getDataSpecificationReferences();
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		new ConnectedHasDataSpecificationFacade(getPath(),getProxy()).setDataSpecificationReferences(ref);
		
	}
	
	@Override
	public void setSecurity(ISecurity security) {
		getProxy().updateElementValue(constructPath(AssetAdministrationShell.SECURITY),security );
		
	}

	@Override
	public ISecurity getSecurity() {
		return (ISecurity)getProxy().readElementValue(constructPath(AssetAdministrationShell.SECURITY));
	}

	@Override
	public void setDerivedFrom(IReference derivedFrom) {
		getProxy().updateElementValue(constructPath(AssetAdministrationShell.DERIVEDFROM),derivedFrom) ;
		
	}

	@Override
	public IReference getDerivedFrom() {
		return (IReference)getProxy().readElementValue(constructPath(AssetAdministrationShell.DERIVEDFROM));
	}

	@Override
	public void setAsset(IReference asset) {
		getProxy().updateElementValue(constructPath(AssetAdministrationShell.ASSET),asset );
		
	}

	@Override
	public IReference getAsset() {
		return (IReference)getProxy().readElementValue(constructPath(AssetAdministrationShell.ASSET));
	}

	@Override
	public void setSubModel(Set<IReference> submodels) {
		getProxy().updateElementValue(constructPath(AssetAdministrationShell.SUBMODEL),submodels );
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IReference> getSubModel() {
		return (Set<IReference>)getProxy().readElementValue(constructPath(AssetAdministrationShell.SUBMODEL));
	}

	@Override
	public void setViews(Set<IView> views) {
		getProxy().updateElementValue(constructPath(AssetAdministrationShell.VIEWS),views);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IView> getViews() {
		return (Set<IView>)getProxy().readElementValue(constructPath(AssetAdministrationShell.VIEWS));
	}

	@Override
	public void setConceptDictionary(Set<IConceptDictionary> dictionaries) {
		getProxy().updateElementValue(constructPath(AssetAdministrationShell.CONCEPTDICTIONARY), dictionaries);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IConceptDictionary> getConceptDictionary() {
		return (Set<IConceptDictionary>)getProxy().readElementValue(constructPath(AssetAdministrationShell.CONCEPTDICTIONARY));
	}
	
	@Override
	public String getId() {
		return (String) getProxy().readElementValue(constructPath(Referable.IDSHORT));
	}

	@Override
	public void setId(String id) {
		getProxy().updateElementValue(constructPath(Referable.IDSHORT), id);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ISubModel> getSubModels() {
		
		Set<Map<?, ?>> refs = null;
		Map<String, ISubModel> ret = new HashMap<>();

		try {
			// Java getSubmodels
			refs = (Set<Map<?, ?>>) getProxy().readElementValue(constructPath("submodel"));
			for (Map<?, ?> key : refs) {
				String id = (String) ((Map<?, ?>) ((List<?>) key.get("keys")).get(0)).get("value");
				ISubModel sm = manager.retrieveSM(id, new ModelUrn(getId()));
				ret.put(id, sm);
			}
		} catch (ClassCastException e) {
			System.out.println("Cast failed... trying c# get submodels");
			// c# getSubmodels
			refs = (Set<Map<?, ?>>) getProxy().readElementValue(constructPath("submodels"));
			for (Map<?, ?> key : refs) {
				String id = (String) key.get("idShort");
				ISubModel sm = manager.retrieveSM(id, new ModelUrn(getId()));
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
		return (String) getProxy().readElementValue(constructPath(Referable.IDSHORT));
	}

	@Override
	public String getCategory() {
		return (String) getProxy().readElementValue(constructPath(Referable.CATEGORY));
	}

	@Override
	public String getDescription() {
		return (String) getProxy().readElementValue(constructPath(Referable.DESCRIPTION));
	}

	@Override
	public IReference  getParent() {
		return (IReference)getProxy().readElementValue(constructPath(Referable.PARENT));
	}

	@Override
	public void setIdshort(String idShort) {
		getProxy().updateElementValue(constructPath(Referable.IDSHORT), idShort);
		
	}

	@Override
	public void setCategory(String category) {
		getProxy().updateElementValue(constructPath(Referable.CATEGORY), category);
		
	}

	@Override
	public void setDescription(String description) {
		getProxy().updateElementValue(constructPath(Referable.DESCRIPTION), description);
		
	}

	@Override
	public void setParent(IReference  obj) {
		getProxy().updateElementValue(constructPath(Referable.PARENT), obj);
		
	}
}
