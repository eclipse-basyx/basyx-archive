package org.eclipse.basyx.aas.backend.connected.aas.submodelelement.property;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.resources.IContainerProperty;
import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.PropertyType;
import org.eclipse.basyx.aas.backend.connected.aas.submodelelement.operation.ConnectedOperation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Connects to a ComplexDataProperty as specified by meta model. <br />
 * Not contained in DAAS meta model
 * 
 * @author schnicke
 *
 */
public class ConnectedContainerProperty extends ConnectedProperty implements IContainerProperty {

	ConnectedPropertyFactory factory = new ConnectedPropertyFactory();

	public ConnectedContainerProperty(String path, VABElementProxy proxy) {
		super(PropertyType.Container, path, proxy);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IProperty> getProperties() {
		Map<String, Object> props = (Map<String, Object>) getProxy().readElementValue(constructPath(SubModel.PROPERTIES));
		Map<String, IProperty> ret = new HashMap<>();

		for (String s : props.keySet()) {
			ret.put(s, factory.createProperty(constructPath(SubModel.PROPERTIES + "/" + s), getProxy()));
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IOperation> getOperations() {
		Map<String, Object> ops = (Map<String, Object>) getProxy().readElementValue(constructPath(SubModel.OPERATIONS));

		Map<String, IOperation> ret = new HashMap<>();
		for (String s : ops.keySet()) {
			ret.put(s, new ConnectedOperation(constructPath(SubModel.OPERATIONS + "/" + s), getProxy()));
		}
		return ret;
	}

	@Override
	public void setValue(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValueId(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getValueId() {
		// TODO Auto-generated method stub
		return null;
	}



}
