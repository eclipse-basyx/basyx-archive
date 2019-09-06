package org.eclipse.basyx.aas.backend.connected.aas.parts;

import java.util.HashSet;

import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.api.metamodel.aas.parts.IAsset;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasDataSpecificationFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasKindFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedIdentifiableFacade;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.parts.Asset;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IAsset
 * @author rajashek
 *
 */
public class ConnectedAsset extends ConnectedElement implements IAsset {
	
	public ConnectedAsset(VABElementProxy proxy) {
		super(proxy);
		
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
	public String getHasKindReference() {
		return new ConnectedHasKindFacade(getProxy()).getHasKindReference();
	}

	@Override
	public void setHasKindReference(String kind) {
		new ConnectedHasKindFacade(getProxy()).setHasKindReference(kind);
		
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
		;
		
	}

	@Override
	public void setIdentification(String idType, String id) {
		new ConnectedIdentifiableFacade(getProxy()).setIdentification(idType, id);
		
	}

	@Override
	public IReference getAssetIdentificationModel() {
		return (IReference) getProxy().getModelPropertyValue(Asset.ASSETIDENTIFICATIONMODEL);
	}

	@Override
	public void setAssetIdentificationModel(IReference submodel) {
		getProxy().setModelPropertyValue(Asset.ASSETIDENTIFICATIONMODEL, submodel);
		
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
		return (IReference) getProxy().getModelPropertyValue(Referable.PARENT);
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
