package org.eclipse.basyx.aas.api.resources.connected;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.resources.basic.Event;
import org.eclipse.basyx.aas.api.resources.basic.Operation;
import org.eclipse.basyx.aas.api.resources.basic.Property;
import org.eclipse.basyx.aas.api.resources.basic.SubModel;

public abstract class ConnectedSubModel extends SubModel {
		
	public Map<String, ConnectedOperation> getConnectedOperations() {
		Map<String, ConnectedOperation> cOperations = new HashMap<>();
		for (String key : super.getOperations().keySet()) {
			Operation op = super.getOperations().get(key);
			if (op instanceof ConnectedOperation) {
				cOperations.put(key, (ConnectedOperation) op);
			} else {
				throw new IllegalStateException("Should contain only connected operations!");
			}
		}
		return cOperations;
	}


	public Map<String, ConnectedProperty> getConnectedProperties() {
		Map<String, ConnectedProperty> cProperties = new HashMap<>();
		for (String key : super.getProperties().keySet()) {
			Property prop = super.getProperties().get(key);
			if (prop instanceof ConnectedProperty) {
				cProperties.put(key, (ConnectedProperty) prop);
			} else {
				throw new IllegalStateException("Should contain only connected properties!");
			}
		}
		return cProperties;
	}

	
	public Map<String, ConnectedEvent> getConnectedEvents() {
		Map<String, ConnectedEvent> cEvents = new HashMap<>();
		for (String key : super.getEvents().keySet()) {
			Event event = super.getEvents().get(key);
			if (event instanceof ConnectedEvent) {
				cEvents.put(key, (ConnectedEvent) event);
			} else {
				throw new IllegalStateException("Should contain only connected properties!");
			}
		}
		return cEvents;
	}
	
	
}
