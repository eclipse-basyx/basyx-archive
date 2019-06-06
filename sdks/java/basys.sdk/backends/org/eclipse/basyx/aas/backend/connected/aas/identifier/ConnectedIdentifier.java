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

	public ConnectedIdentifier(String path, VABElementProxy proxy) {
		super(path, proxy);
	}

	@Override
	public String getIdType() {
		return (String) getProxy().readElementValue(constructPath(Identifier.IDTYPE));
	}

	@Override
	public void setIdType(String newValue) {
		getProxy().updateElementValue(constructPath(Identifier.IDTYPE), newValue);
	
	}

	@Override
	public String getId() {
		return (String) getProxy().readElementValue(constructPath(Identifier.ID));
	}

	@Override
	public void setId(String newValue) {
		getProxy().updateElementValue(constructPath(Identifier.ID), newValue);
		
	}
}
