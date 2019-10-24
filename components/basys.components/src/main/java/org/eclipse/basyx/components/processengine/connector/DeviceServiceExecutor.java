package org.eclipse.basyx.components.processengine.connector;

import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.manager.api.IAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.ISingleProperty;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubModel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.identifier.IdentifierType;


/**
 * A service executor that invokes services defined in the administration shells.
 * The service executor is called by the Java-delegate class of the process-engine
 * All necessary parameters are delivered through field injections
 * 
 * @author Zhang, Zai
 * */
public abstract class DeviceServiceExecutor implements IDeviceServiceExecutor {
	
	protected IAssetAdministrationShellManager manager;
	
	
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
	


	/**
	 * Get value of a property from a submodel
	 * */
	protected Object getProperty(String rawUrn, String submodelid, String propertyName) throws Exception {
		// create Model urn
		IIdentifier aasUrn = new ModelUrn(rawUrn);
		IIdentifier smId = new Identifier(IdentifierType.Custom, submodelid);

		// retrieve submodel with id
		ISubModel statusSubmodel = manager.retrieveSubModel(aasUrn, smId);
		
		// get properties of the submodel
		Map<String, IDataElement> properties = ((ConnectedSubModel) statusSubmodel).getDataElements();
		
		// get specific property
		ISingleProperty pro_EXST = (ISingleProperty)properties.get(propertyName);
		
		//get value-information of property in map
		Object obj = pro_EXST.get();
		
		//type-check
		if(obj instanceof Map) {
			//convert to map
			@SuppressWarnings("unchecked")
			Map<String, Object> valuemap = (Map<String, Object>)pro_EXST.get();
			
			// get value of the property
			Object val = valuemap.get("value");
			System.out.println("value of " + propertyName+ " is "+val);
			return val;
		}else if(obj instanceof String) {
			String value = (String) obj;
			System.out.println("value of " + propertyName+ " is "+value);
			return value;
		}
		return null;
	}
	
	
	@Override
	public String getServiceName() {
		return null;
	}

	@Override
	public String getServiceProvider() {
		return null;
	}

	@Override
	public List<Object> getParams() {
		return null;
	}

	/**
	 * For the case that the executor knows which submodel to call
	 * */
	@Override
	public abstract Object executeService(String serviceName, String deviceid, List<Object> params) throws Exception ;
	
}
