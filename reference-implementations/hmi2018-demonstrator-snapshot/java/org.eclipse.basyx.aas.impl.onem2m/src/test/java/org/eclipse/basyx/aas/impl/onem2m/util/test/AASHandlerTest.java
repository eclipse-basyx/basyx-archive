package org.eclipse.basyx.aas.impl.onem2m.util.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.eclipse.basyx.aas.api.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.AssetKind;
import org.eclipse.basyx.aas.api.resources.basic.DataType;
import org.eclipse.basyx.aas.api.resources.basic.Operation;
import org.eclipse.basyx.aas.api.resources.basic.ParameterType;
import org.eclipse.basyx.aas.api.resources.basic.SubModel;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedOperation;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedSubModel;
import org.eclipse.basyx.aas.impl.onem2m.OneM2MAssetAdministrationShellManager;
import org.eclipse.basyx.aas.impl.onem2m.connected.OneM2MConnectedOperation;
import org.eclipse.basyx.aas.impl.onem2m.managed.AASHandler;
import org.eclipse.basyx.aas.impl.onem2m.util.test.AASHandlerTest.TestAAS.TestSubModel;
import org.eclipse.basyx.onem2m.clients.OneM2MHttpClient;
import org.eclipse.basyx.onem2m.clients.OneM2MHttpClientConfig;
import org.eclipse.basyx.onem2m.clients.OneM2MHttpClientConfig.Protocol;
import org.eclipse.basyx.onem2m.manager.OneM2MResourceManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class AASHandlerTest {
	
	
	OneM2MHttpClient client = null;
	OneM2MResourceManager oneM2MresourceManager = null;
	OneM2MAssetAdministrationShellManager aasManager;
	
	@Before
	public void before() throws Exception {	
		// NOTE: test requires a running oneM2M-CSE -> 127.0.0.1:8080
		client = new OneM2MHttpClient(new OneM2MHttpClientConfig(Protocol.HTTP, "127.0.0.1", 8080, "admin:admin"));
		client.start();
		oneM2MresourceManager = new OneM2MResourceManager(client);
		oneM2MresourceManager.startSubscriptionHandler("unittest-java", "127.0.0.1", 51338);		
		aasManager = new OneM2MAssetAdministrationShellManager(oneM2MresourceManager, null);		
	}
	
	@After
	public void after() throws Exception {		
		client.stop();
		oneM2MresourceManager.stopSubscriptionHandler();
	}
	
	
	public class TestAAS {
		public class TestSubModel {
			
			
			public int sum(int a, int b) {
				return a+b;
			}
			
		}		
		TestSubModel mysubmodel = new TestSubModel();		
	}
	
	@Test
	public void testCallOperation() throws Exception {
		
		TestAAS testAAS = new TestAAS();
		
		AssetAdministrationShell aas = new AssetAdministrationShell();
	    aas.setId("unit_test_java");
	    aas.setAssetId("assetId");
	    aas.setAssetKind(AssetKind.INSTANCE);
	    aas.setAssetTypeDefinition("assetTypeDefinition");
	    aas.setDisplayName("displayName");
		
	    SubModel sm = new SubModel();
	    sm.setAssetKind(AssetKind.INSTANCE);
	    sm.setName("subModelExample");
	    sm.setTypeDefinition("smType");
	    aas.addSubModel(sm);
	    
	    Operation op = new Operation();
	    op.setName("sum");
	    ArrayList<ParameterType> pt = new ArrayList<>();
	    pt.add(new ParameterType(0, DataType.INTEGER, "a"));
	    pt.add(new ParameterType(1, DataType.INTEGER, "b"));
	    op.setParameterTypes(pt);
	    op.setReturnDataType(DataType.INTEGER);	    	    
	    sm.addOperation(op);	    
	    ConnectedAssetAdministrationShell caas = this.aasManager.createAAS(aas);
	    
	    ConnectedSubModel csm = (ConnectedSubModel) caas.getSubModels().values().iterator().next();
	    ConnectedOperation cop = csm.getConnectedOperations().values().iterator().next();
	    
	    AASHandler aasHandler = new AASHandler(this.oneM2MresourceManager);
	    aasHandler.handleOperation((OneM2MConnectedOperation) cop, testAAS.mysubmodel, TestSubModel.class.getMethod("sum", int.class, int.class));
	    
	    Object[] params = {new Integer(3), new Integer(10)};
	    Integer retval = (Integer) cop.call(params, 100000);
	    assertEquals(13, retval.intValue());
	    this.aasManager.deleteAAS(aas.getId());
	}

}
