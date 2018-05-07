package org.eclipse.basyx.testsuite.regression.aas.backend.http.basic;

import static org.junit.Assert.assertTrue;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.impl.resources.basic.DataType;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.junit.jupiter.api.Test;




/**
 * AAS Operations test case
 * 
 * Context: Static configured topology
 * - Connect to AAS "Stub1AAS"
 * - Connect to sub model "statusSM"
 * - Connect to properties "sampleProperty1" and "sampleProperty2"
 * - Retrieve values of both properties
 * 
 * @author kuhn
 *
 */
class TestAASSimpleGetPropertyMetaData {
	

	/**
	 * Store HTTP asset administration shell manager backend
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new TestsuiteDirectory());
	
	
	
	/**
	 * Run JUnit test case
	 */
	@Test
	void runTest() throws Exception {

		// Connect to AAS with ID "Stub1AAS"
		// - Retrieve connected AAS from AAS ID
		IAssetAdministrationShell connAAS = this.aasManager.retrieveAAS("Stub1AAS");
		
		
		// Connect to sub model
		// - FIXME: We need to define technology independent query String for directory (e.g. status.sampleAAS)
		// - Retrieve connected sub model
		// - FIXME: Get submodel by type or ID		
		ISubModel submodel = connAAS.getSubModels().get("statusSM");		
		
		
		// Connect to sub model property
		// - Get property values (shortcut here, we know that it is a single property type)
		DataType property1Type = ((ISingleProperty) submodel.getProperties().get("sampleProperty1")).getDataType();

		
		// Check test case results
		assertTrue(property1Type == DataType.INTEGER);
	}
}

