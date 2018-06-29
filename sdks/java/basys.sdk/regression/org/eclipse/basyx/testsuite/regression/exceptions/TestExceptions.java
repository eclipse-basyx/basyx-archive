package org.eclipse.basyx.testsuite.regression.exceptions;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.eclipse.basyx.aas.api.exception.AtomicTransactionFailedException;
import org.eclipse.basyx.aas.api.exception.ResourceNotFoundException;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ICollectionProperty;
import org.eclipse.basyx.aas.api.resources.basic.IMapProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.junit.jupiter.api.Test;


/**
 * AAS Exception test case
 * 
 * Context: Static configured topology
 * - Connect to AAS "Stub1AAS"
 * - Connect to sub model "statusSM"
 * - Test for
 * -- not found values
 * -- param type mismatch
 * -- out-of-bound indices
 * -- read only values
 * - for single properties, collections and maps
 * 
 * @author pschorn
 *
 */
class TestExceptions {
	

	//Store HTTP asset administration shell manager backend
	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory());
	
	// Connect to AAS with ID "Stub1AAS"
	// - Retrieve connected AAS from AAS ID
	IAssetAdministrationShell connAAS = this.aasManager.retrieveAAS("Stub1AAS");
	
	
	// Connect to sub model
	// - FIXME: We need to define technology independent query String for directory (e.g. status.sampleAAS)
	// - Retrieve connected sub model
	// - FIXME: Get submodel by type or ID		
	ISubModel submodel = ((HashMap<String, ISubModel>) connAAS.getSubModels()).get("statusSM");		
	
	/**
	 * Test if ResourceNotFoundException is thrown if unregistered property is queried
	 */
	@Test
	void testPropertyNotFound() throws Exception {

		try {
			// Request non-existing property
			((ISingleProperty) submodel.getProperties().get("notfound")).get();
			fail( "ResourceNotFoundException was not thrown" );
		    
		} catch (ResourceNotFoundException e) {
			System.out.println("ResourceNotFoundException was correctly thrown: "+e.getMessage());
		}


	}
	
	@Test
	void testTypeMismatch() throws Exception {
		
		try {
			// Request map property value
			((IMapProperty) submodel.getProperties().get("sampleProperty5")).getValue("notAmap");
			fail( "ClassCastException was not thrown" );
		    
		} catch (ClassCastException e) {
			System.out.println("ClassCastException was correctly thrown: "+e.getMessage());
		}
	}
}

