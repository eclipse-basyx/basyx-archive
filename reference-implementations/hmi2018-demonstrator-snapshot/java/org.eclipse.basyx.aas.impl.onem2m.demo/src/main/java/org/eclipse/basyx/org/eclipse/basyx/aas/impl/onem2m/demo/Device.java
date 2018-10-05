package org.eclipse.basyx.org.eclipse.basyx.aas.impl.onem2m.demo;

import java.util.ArrayList;

import org.eclipse.basyx.aas.api.manager.IAssetAdministrationShellManager;
import org.eclipse.basyx.aas.api.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.AssetKind;
import org.eclipse.basyx.aas.api.resources.basic.DataType;
import org.eclipse.basyx.aas.api.resources.basic.Operation;
import org.eclipse.basyx.aas.api.resources.basic.ParameterType;
import org.eclipse.basyx.aas.api.resources.basic.SubModel;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedOperation;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedSubModel;
import org.eclipse.basyx.aas.impl.onem2m.connected.OneM2MConnectedOperation;
import org.eclipse.basyx.aas.impl.onem2m.managed.AASHandler;

/**
 * 
 * Example of an implementation that could be deployed on a device in a real world scenario.
 * The device has one submodel which represents a calculator with a single function (sum).
 * The sum function has been integrated into this demo application for simplification reasons. 
 * Normally, devices operations that are more closely related to a specific production use case (e.g. drill operation)
 */
public class Device {

	IAssetAdministrationShellManager manager;
	AASHandler handler;
	
	public static final String ID =  "ExampleDevice";
	public static final String SUBMODEL_NAME = "calcSubModel";
	public static final String OPERATION_NAME = "sum";

	
	/**
	 *	Submodel (represented as internal class) that offers calculation functionality. 
	 * 
	 */
	public class CalculatorSubModel {
		public int sum(int a, int b) {
			return a+b;
		}
		
	}			
	CalculatorSubModel calcSubModel = new CalculatorSubModel();		

	
	public Device(IAssetAdministrationShellManager manager, AASHandler handler) {
		this.manager = manager;
		this.handler = handler;
	}
	
	void init() throws NoSuchMethodException, SecurityException, Exception {
		
		// check whether asset administration shell already exists
		ConnectedAssetAdministrationShell caas = null;
		caas = this.manager.retrieveAAS(ID);		
		
		// publish the device's asset administration shell to the basyx middleware
		if (caas == null) {
			AssetAdministrationShell aas = new AssetAdministrationShell();
		    aas.setId(ID);
		    aas.setAssetId(ID);
		    aas.setAssetKind(AssetKind.INSTANCE);
		    aas.setAssetTypeDefinition("ExampleDeviceType");
		    aas.setDisplayName("My Example Device");
			
		    SubModel sm = new SubModel();
		    sm.setAssetKind(AssetKind.INSTANCE);
		    sm.setName(SUBMODEL_NAME);
		    sm.setTypeDefinition("CalculatorSubModelType");
		    aas.addSubModel(sm);
		    
		    Operation op = new Operation();
		    op.setName(OPERATION_NAME);
		    ArrayList<ParameterType> pt = new ArrayList<ParameterType>();
		    pt.add(new ParameterType(0, DataType.INTEGER, "a"));
		    pt.add(new ParameterType(1, DataType.INTEGER, "b"));
		    op.setParameterTypes(pt);
		    op.setReturnDataType(DataType.INTEGER);	    	    
		    sm.addOperation(op);
		    caas = this.manager.createAAS(aas);
		}
	    
	    // connect the sum method to the operation that is stored within the aas on the basyx middleware
	    ConnectedSubModel csm = (ConnectedSubModel) caas.getSubModels().values().iterator().next();
	    ConnectedOperation cop = csm.getConnectedOperations().values().iterator().next();		
	    this.handler.handleOperation((OneM2MConnectedOperation) cop, this.calcSubModel, CalculatorSubModel.class.getMethod("sum", int.class, int.class));
	}
	
	void cleanup() throws Exception {
		this.manager.deleteAAS(Device.ID);
	}
}
