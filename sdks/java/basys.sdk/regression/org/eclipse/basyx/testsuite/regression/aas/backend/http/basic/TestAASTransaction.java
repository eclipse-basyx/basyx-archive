package org.eclipse.basyx.testsuite.regression.aas.backend.http.basic;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.basyx.aas.api.exception.AtomicTransactionFailedException;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.ConnectedOperation;
import org.eclipse.basyx.aas.backend.ConnectedSubmodel;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.junit.jupiter.api.Test;

public class TestAASTransaction {

	
	/**
	 * Store HTTP asset administration shell manager backend
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory(), new HTTPConnectorProvider());
	protected ConnectedAssetAdministrationShellManager aasManager2 = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory(), new HTTPConnectorProvider());
	
	
	
	/**
	 * Run JUnit test case
	 */
	@Test
	void runTest() throws Exception {

		// Connect to AAS with ID "Stub1AAS"
		IAssetAdministrationShell connAAS = this.aasManager.retrieveAAS("Stub1AAS");
		IAssetAdministrationShell connAAS2 = this.aasManager2.retrieveAAS("Stub1AAS");

		// Connect to sub model
		ConnectedSubmodel submodel = (ConnectedSubmodel) connAAS.getSubModels().get("statusSM");		
		ConnectedSubmodel submodel2 = (ConnectedSubmodel) connAAS2.getSubModels().get("statusSM");		

		/**
		 * Test clean Transaction
		 */
		
		// START ATOMIC TRANSACTION
		submodel.startTransaction();
		
		// DO ATOMIC CALCULATION
		Integer x = (Integer) ((ISingleProperty) submodel.getProperties().get("sampleProperty1")).get();
		Integer y = (Integer) ((ISingleProperty) submodel.getProperties().get("sampleProperty2")).get();		
		int result1 = (int) ((ConnectedOperation) submodel.getOperations().get("sum")).invoke(x, y);

		// Check test case results
		assertTrue(result1 == 5);
		
		// END ATOMIC TRANSACTION
		submodel.endTransaction();
		
		/**
		 * Test intercepted Transaction
		 */
		
		// START ATOMIC TRANSACTION with interruption
		submodel.startTransaction();
		
		Integer testclock = submodel.getServerClock();
		
		// Do calculations
		Integer x2 = (Integer) ((ISingleProperty) submodel.getProperties().get("sampleProperty1")).get();
		Integer y2 = (Integer) ((ISingleProperty) submodel.getProperties().get("sampleProperty2")).get();
		
		// Different guy changes submodel property
		((ISingleProperty) submodel2.getProperties().get("sampleProperty1")).set(5);
		((ISingleProperty) submodel2.getProperties().get("sampleProperty1")).set(2);
		int result2 = (int) ((ConnectedOperation) submodel.getOperations().get("sum")).invoke(x2, y2);

		// Test if server clock has been incremented 2 times
		assertTrue(submodel.getServerClock() == testclock+2);
	
		// Check test case results
		assertTrue(result2 == 5);
		
		// END ATOMIC TRANSACTION
		try {
			submodel.endTransaction();
		    fail( "AtomicTransactionFailedException was not thrown" );
		    
		} catch (AtomicTransactionFailedException e) {
			System.out.println("Atomic transactions work");
		}

		
		
	}
}
