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
 * 
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
	public String getHasKindReference() {
		return new ConnectedHasKindFacade(getProxy()).getHasKindReference();
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
	public IReference getAssetIdentificationModel() {
		return (IReference) getProxy().getModelPropertyValue(Asset.ASSETIDENTIFICATIONMODEL);
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
	public IReference getParent() {
		return (IReference) getProxy().getModelPropertyValue(Referable.PARENT);
	}

}
