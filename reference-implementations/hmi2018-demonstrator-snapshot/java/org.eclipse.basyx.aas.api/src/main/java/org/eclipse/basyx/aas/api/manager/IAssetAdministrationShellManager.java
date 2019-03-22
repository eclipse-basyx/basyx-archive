package org.eclipse.basyx.aas.api.manager;

import java.util.Collection;

import org.eclipse.basyx.aas.api.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedOperation;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedProperty;

public interface IAssetAdministrationShellManager {
	public ConnectedAssetAdministrationShell createAAS(AssetAdministrationShell aas) throws Exception;
	public ConnectedAssetAdministrationShell retrieveAAS(String id) throws Exception;
	public boolean existsAAS(String id) throws Exception;
	public Collection<ConnectedAssetAdministrationShell> retrieveAASAll();
	void deleteAAS(String id) throws Exception;
	public ConnectedOperation retrieveOperation(String aasId, String submodelName, String operationName) throws Exception;
	public ConnectedProperty retrieveProperty(String aasId, String subName, String propertyName) throws Exception;

	Object callOperation(String aasId, String subName, String operationName, Object parameters[], int timeout) throws Exception;
	Object getSingleProperty(String aasId, String subName, String propertyName) throws Exception;
	void setSingleProperty(String aasId, String subName, String propertyName, Object value) throws Exception;
	Object getCollectionProperty(String aasId, String subName, String propertyName, String key) throws Exception;
	void setCollectionProperty(String aasId, String subName, String propertyName, String key, Object value) throws Exception;


}
