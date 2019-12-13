package org.eclipse.basyx.components.processengine.connector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.manager.api.IAssetAdministrationShellManager;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.identifier.IdentifierType;


/**
 * A service executor that invokes services defined in the administration shells.
 * The service executor is called by the Java-delegate class of the process-engine
 * All necessary parameters are delivered through field injections
 * 
 * @author Zhang, Zai
 * */
public class DeviceServiceExecutor implements IDeviceServiceExecutor {
	
	protected IAssetAdministrationShellManager manager;
	protected String  serviceName;
	protected String serviceProvider;
	protected String serviceSubmodelId;
	protected List<Object> parameters = new ArrayList<>();
	
	
	public DeviceServiceExecutor(IAssetAdministrationShellManager manager) {
		// set-up the administration shell manager to create connected aas
		this.manager = manager;
	};

	/**
	 * Synchronous invocation the expected service specified by the BPMN-model
	 * */
	@Override
	public Object executeService( String servicename, String serviceProvider, String submodelid, List<Object> params) {
		try {
			// create ids
			IIdentifier aasId = new Identifier(IdentifierType.Custom, serviceProvider);
			IIdentifier smId = new Identifier(IdentifierType.Custom, submodelid);

			// create the submodel of the corresponding aas
			ISubModel serviceSubmodel = manager.retrieveSubModel(aasId, smId);

			// navigate to the expected service 
			Map<String, IOperation> operations = serviceSubmodel.getOperations();
			IOperation op = operations.get(servicename);
			
			// invoke the service
			System.out.printf("#Service Executor#--Call service: %s with parameter: %s \n", servicename,  params);
			Object position = op.invoke(params.toArray());
			
			return position;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	public String getServiceSubmodelId() {
		return serviceSubmodelId;
	}
	
}
