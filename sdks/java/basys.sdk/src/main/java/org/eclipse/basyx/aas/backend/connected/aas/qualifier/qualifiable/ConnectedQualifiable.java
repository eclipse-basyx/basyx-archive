package org.eclipse.basyx.aas.backend.connected.aas.qualifier.qualifiable;

import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IQualifiable;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.qualifiable.Qualifiable;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IQualifiable
 * @author rajashek
 *
 */
public class ConnectedQualifiable extends ConnectedElement implements IQualifiable {

	
	public ConnectedQualifiable(String path, VABElementProxy proxy) {
		super(path, proxy);		
	}
	
	@Override
	public void setQualifier(Set<IConstraint> qualifiers) {
		getProxy().updateElementValue(constructPath(Qualifiable.CONSTRAINTS), qualifiers);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IConstraint> getQualifier() {
		return (Set<IConstraint>)getProxy().readElementValue(constructPath(Qualifiable.CONSTRAINTS));
	}
}
