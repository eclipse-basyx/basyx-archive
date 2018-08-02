package org.eclipse.basyx.aas.backend.connector.opcua;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.backend.connector.IBasysConnector;
import org.json.JSONObject;

public class OPCUAConnector implements IBasysConnector {

	@Override
	public Object basysGet(String address, String servicePath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject basysGetRaw(String address, String servicePath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void basysSet(String address, String servicePath, Object newValue) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object basysInvoke(String address, String servicePath, Object... parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void basysSet(String address, String servicePath, Object... parameters) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object basysCreate(String address, String servicePath, Object... parameters) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void basysDelete(String address, String servicePath) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void basysDelete(String address, String servicePath, Object obj) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String buildPath(String aasId, String aasSubmodelID, String path, String type) {
		// TODO Auto-generated method stub
		return null;
	}

}
