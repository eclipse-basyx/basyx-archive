package org.eclipse.basyx.aas.backend.connected;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.backend.connected.property.ConnectedPropertyFactory;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

public class ConnectedSubModel extends ConnectedElement implements ISubModel {

	ConnectedPropertyFactory factory = new ConnectedPropertyFactory();

	public ConnectedSubModel(String path, VABElementProxy proxy) {
		super(path, proxy);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IProperty> getProperties() {
		Map<String, Object> props = (Map<String, Object>) getProxy().readElementValue(constructPath("properties"));

		Map<String, IProperty> ret = new HashMap<>();
		for (String s : props.keySet()) {
			ret.put(s, factory.createProperty(constructPath("properties/" + s), getProxy()));
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IOperation> getOperations() {
		Map<String, Object> ops = (Map<String, Object>) getProxy().readElementValue(constructPath("operations"));

		Map<String, IOperation> ret = new HashMap<>();
		for (String s : ops.keySet()) {
			ret.put(s, new ConnectedOperation(constructPath("operations/" + s), getProxy()));
		}
		return ret;
	}
}
