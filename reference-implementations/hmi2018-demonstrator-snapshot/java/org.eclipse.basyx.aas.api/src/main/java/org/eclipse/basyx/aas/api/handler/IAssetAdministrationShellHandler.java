package org.eclipse.basyx.aas.api.handler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.eclipse.basyx.aas.api.resources.connected.ConnectedOperation;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedProperty;


public interface IAssetAdministrationShellHandler {
	public void handleOperation(ConnectedOperation cop, Object obj, Method m) throws Exception;
	public void handleProperty(ConnectedProperty cop, Object obj, Method m) throws Exception;
	public void handleProperty(ConnectedProperty cop, Object obj, Field f) throws Exception;
}
