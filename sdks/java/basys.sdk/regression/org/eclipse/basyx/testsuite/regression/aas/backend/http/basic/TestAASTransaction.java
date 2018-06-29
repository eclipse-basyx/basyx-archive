package org.eclipse.basyx.testsuite.regression.aas.backend.http.basic;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.basyx.aas.api.exception.AtomicTransactionFailedException;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.ConnectedOperation;
import org.eclipse.basyx.aas.backend.ConnectedSubmodel;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.junit.jupiter.api.Test;

public class TestAASTransaction {

	
	/**
	 * Store HTTP asset administration shell manager backend
	 */
	protected ConnectedAssetAdministrationShellManager client1_aasManager = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory());
	protected ConnectedAssetAdministrationShellManager client2_aasManager = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory());

	
	
	/**
	 * Run JUnit test case
	 */
	@Test
	void runTest() throws Exception {

		// Connect to AAS with ID "Stub1AAS"
		IAssetAdministrationShell client1_connAAS = this.client1_aasManager.retrieveAAS("Stub1AAS");
		IAssetAdministrationShell client2_connAAS = this.client2_aasManager.retrieveAAS("Stub1AAS");

		// Connect to sub model
		ConnectedSubmodel client1_submodel = (ConnectedSubmodel) client1_connAAS.getSubModels().get("statusSM");		
		ConnectedSubmodel client2_submodel = (ConnectedSubmodel) client2_connAAS.getSubModels().get("statusSM");	
		
		// Set up
		((ISingleProperty) client2_submodel.getProperties().get("sampleProperty1")).set(2);
		((ISingleProperty) client2_submodel.getProperties().get("sampleProperty2")).set(3);

		/**
		 * Test clean Transaction
		 */
		
		// START ATOMIC TRANSACTION
		client1_submodel.startTransaction();
		
		// DO ATOMIC CALCULATION
		Integer x = (Integer) ((ISingleProperty) client1_submodel.getProperties().get("sampleProperty1")).get();
		Integer y = (Integer) ((ISingleProperty) client1_submodel.getProperties().get("sampleProperty2")).get();		
		int result1 = (int) ((ConnectedOperation) client1_submodel.getOperations().get("sum")).invoke(x, y);

		// Check test case results
		assertTrue(result1 == 5);
		
		// END ATOMIC TRANSACTION
		client1_submodel.endTransaction(); // throws exception when test is run concurrently to other tests.
		
		/**
		 * Test intercepted Transaction
		 */
		
		// START ATOMIC TRANSACTION with interruption
		client1_submodel.startTransaction();
		
		Integer testclock = client1_submodel.getServerClock();
		
		// Do calculations
		Integer x2 = (Integer) ((ISingleProperty) client1_submodel.getProperties().get("sampleProperty1")).get();
		Integer y2 = (Integer) ((ISingleProperty) client1_submodel.getProperties().get("sampleProperty2")).get();
		
		// Different client changes submodel property
		((ISingleProperty) client2_submodel.getProperties().get("sampleProperty1")).set(5);
		((ISingleProperty) client2_submodel.getProperties().get("sampleProperty1")).set(2);
		int result2 = (int) ((ConnectedOperation) client1_submodel.getOperations().get("sum")).invoke(x2, y2);

		// Test if server clock has been incremented (at least, other test cases run concurrently) 2 times
		assertTrue(client1_submodel.getServerClock() >= testclock+2);
	
		// END ATOMIC TRANSACTION
		try {
			client1_submodel.endTransaction();
		    fail( "AtomicTransactionFailedException was not thrown" );
		    
		} catch (AtomicTransactionFailedException e) {
			System.out.println("Atomic transactions work");
		}

		// Reset Properties
		((ISingleProperty) client2_submodel.getProperties().get("sampleProperty1")).set(2);
		((ISingleProperty) client2_submodel.getProperties().get("sampleProperty1")).set(3);
	}
}
