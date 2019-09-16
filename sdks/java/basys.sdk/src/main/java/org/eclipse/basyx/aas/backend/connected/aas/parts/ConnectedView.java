package org.eclipse.basyx.aas.backend.connected.aas.parts;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.parts.IView;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasDataSpecificationFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasSemanticsFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedReferableFacade;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.parts.View;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * "Connected" implementation of IView
 * 
 * @author rajashek
 *
 */
public class ConnectedView extends ConnectedElement implements IView {
	public ConnectedView(VABElementProxy proxy) {
		super(proxy);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IReference> getContainedElement() {
		return (Set<IReference>) getProxy().getModelPropertyValue(View.CONTAINEDELEMENT);
	}

	@Override
	public IReference getSemanticId() {
		return new ConnectedHasSemanticsFacade(getProxy()).getSemanticId();
	}

	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return new ConnectedHasDataSpecificationFacade(getProxy()).getDataSpecificationReferences();
	}

	@Override
	public String getIdshort() {
		return new ConnectedReferableFacade(getProxy()).getIdshort();
	}

	@Override
	public String getCategory() {
		return new ConnectedReferableFacade(getProxy()).getCategory();
	}

	@Override
	public String getDescription() {
		return new ConnectedReferableFacade(getProxy()).getDescription();
	}

	@Override
	public IReference getParent() {
		return new ConnectedReferableFacade(getProxy()).getParent();
	}
}
