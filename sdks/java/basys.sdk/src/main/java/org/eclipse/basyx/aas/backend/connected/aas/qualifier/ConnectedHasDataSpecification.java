package org.eclipse.basyx.aas.backend.connected.aas.qualifier;

import java.util.HashSet;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasDataSpecification;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasDataSpecification;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IHasDataSpecification
 * @author rajashek
 *
 */
public class ConnectedHasDataSpecification extends ConnectedElement implements IHasDataSpecification {
	public ConnectedHasDataSpecification(String path, VABElementProxy proxy) {
		super(path, proxy);		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return (HashSet<IReference>) getProxy().getModelPropertyValue(constructPath(HasDataSpecification.HASDATASPECIFICATION));
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		getProxy().setModelPropertyValue(constructPath(HasDataSpecification.HASDATASPECIFICATION), ref);
		
	}

}
