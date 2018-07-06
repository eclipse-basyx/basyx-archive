package org.eclipse.basyx.regression.sqlprovider.tests;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.regression.support.directory.ComponentTestsuiteDirectory;
import org.junit.jupiter.api.Test;



/**
 * Test SQL queries
 * 
 * @author kuhn
 *
 */
class SQLQueries {

	
	/**
	 * Store HTTP asset administration shell manager backend
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(new ComponentTestsuiteDirectory());

	
	/**
	 * Test basic queries
	 */
	@Test
	void test() throws Exception {

		// Connect to AAS with ID "Stub3AAS"
		// - Retrieve connected AAS from AAS ID
		IAssetAdministrationShell connAAS = this.aasManager.retrieveAAS("SQLTestAAS");


		// Connect to sub model
		// - FIXME: We need to define technology independent query String for directory (e.g. status.sampleAAS)
		// - Retrieve connected sub model
		// - FIXME: Get submodel by type or ID		
		ISubModel submodel = connAAS.getSubModels().get("sampleDB");		


		// Connect to sub model operations
		// - Retrieve property "sensorNames", which is provided by a SQL database
		Object propertyVal = ((ISingleProperty) submodel.getProperties().get("sensorNames")).get();

		// Check test case results
		// - Type check
		assertTrue(propertyVal instanceof Collection);
		// - Value check
		@SuppressWarnings("unchecked") Object[] resultSet = ((Collection<Object>) propertyVal).toArray();
		assertTrue(resultSet[0].equals("VS_0001"));
		assertTrue(resultSet[1].equals("VS_0002"));
	}
}
