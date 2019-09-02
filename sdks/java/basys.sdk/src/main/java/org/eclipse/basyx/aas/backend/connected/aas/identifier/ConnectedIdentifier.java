package org.eclipse.basyx.aas.backend.connected.aas.identifier;

import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.Identifier;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IIdentifier
 * @author rajashek
 *
 */
public class ConnectedIdentifier extends ConnectedElement implements IIdentifier{

	public ConnectedIdentifier(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public String getIdType() {
		return (String) getProxy().getModelPropertyValue(Identifier.IDTYPE);
	}

	@Override
	public void setIdType(String newValue) {
		getProxy().setModelPropertyValue(Identifier.IDTYPE, newValue);
	
	}

	@Override
	public String getId() {
		return (String) getProxy().getModelPropertyValue(Identifier.ID);
	}

	@Override
	public void setId(String newValue) {
		getProxy().setModelPropertyValue(Identifier.ID, newValue);
		
	}
}
