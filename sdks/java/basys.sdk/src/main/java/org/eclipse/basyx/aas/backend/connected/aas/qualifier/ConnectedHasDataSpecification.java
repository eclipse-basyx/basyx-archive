package org.eclipse.basyx.aas.backend.connected.aas.qualifier;

import java.util.HashSet;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasDataSpecification;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.HasDataSpecification;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IHasDataSpecification
 * @author rajashek
 *
 */
public class ConnectedHasDataSpecification extends ConnectedElement implements IHasDataSpecification {
	public ConnectedHasDataSpecification(VABElementProxy proxy) {
		super(proxy);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return (HashSet<IReference>) getProxy().getModelPropertyValue(HasDataSpecification.HASDATASPECIFICATION);
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		getProxy().setModelPropertyValue(HasDataSpecification.HASDATASPECIFICATION, ref);
		
	}

}
