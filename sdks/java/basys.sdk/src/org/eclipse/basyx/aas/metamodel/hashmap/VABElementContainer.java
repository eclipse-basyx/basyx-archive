package org.eclipse.basyx.aas.metamodel.hashmap;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.Property;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.operation.Operation;

public interface VABElementContainer {

	public void addProperty(String path, Property property);

	public void addOperation(String path, Operation operation);

	public void addEvent(String id, Object event);

	public Map<String, Object> getAsMap();

	// public Map<String, Property> getProperties();
	//
	// public Map<String, Operation> getOperations();
}
