package org.eclipse.basyx.submodel.metamodel.connected.submodelelement;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IRelationshipElement;
import org.eclipse.basyx.submodel.metamodel.facade.reference.ReferenceFacade;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.RelationshipElement;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * "Connected" implementation of RelationshipElement
 * 
 * @author rajashek
 *
 */
public class ConnectedRelationshipElement extends ConnectedSubmodelElement implements IRelationshipElement {
	public ConnectedRelationshipElement(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public void setFirst(IReference first) {
		getProxy().setModelPropertyValue(RelationshipElement.FIRST, first);

	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getFirst() {
		return new ReferenceFacade((Map<String, Object>) getElem().getPath(RelationshipElement.FIRST));
	}

	@Override
	public void setSecond(IReference second) {
		getProxy().setModelPropertyValue(RelationshipElement.SECOND, second);

	}

	@Override
	public IReference getSecond() {
		return (IReference) getProxy().getModelPropertyValue(RelationshipElement.FIRST);
	}
}
