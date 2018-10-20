package org.eclipse.basyx.regression.sqlprovider.tests;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.regression.support.directory.ComponentsTestsuiteDirectory;
import org.eclipse.basyx.vab.VABConnectionManager;
import org.eclipse.basyx.vab.proxy.VABElementProxy;
import org.junit.jupiter.api.Test;



/**
 * Test SQL queries
 * 
 * @author kuhn
 *
 */
class SQLQueriesTest {

	
	/**
	 * Store HTTP asset administration shell manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(new ComponentsTestsuiteDirectory(), new HTTPConnectorProvider());

	
	/**
	 * Test basic queries
	 */
	@Test
	void test() throws Exception {

		// Connect to sub model "CfgFileTestAAS"
		VABElementProxy connSubModel = this.connManager.connectToVABElement("SQLTestSubmodel");

		
		// Get property value
		Object value1 = connSubModel.readElementValue("/aas/submodels/SQLTestSubmodel/properties/sensorNames");
		System.out.println("Value:"+value1);

		
		// Create a new property
		// - HashMap that contains new table line
		Map<String, Object> newTableLine = new HashMap<>();
			newTableLine.put("sensorname", "VS_0003");
			newTableLine.put("sensorid",   "033542");
		// - Insert line into table
		connSubModel.createElement("/aas/submodels/SQLTestSubmodel/properties/sensorNames", newTableLine);
		
		// Get property value again
		Object value2 = connSubModel.readElementValue("/aas/submodels/SQLTestSubmodel/properties/sensorNames");
		System.out.println("Value:"+value2);

		
		// Delete property 'VS_0003'
		// - Collection that contains call values
		Collection<String> callValues = new LinkedList<>();
		callValues.add("VS_0003");
		// - Delete sensor from table
		connSubModel.deleteElement("/aas/submodels/SQLTestSubmodel/properties/sensorNames", callValues);
		
		// Get property value again
		Object value3 = connSubModel.readElementValue("/aas/submodels/SQLTestSubmodel/properties/sensorNames");
		System.out.println("Value:"+value3);
		

		// Update property value
		// - Here this adds a new value into the table
		// - Collection that contains call values
		Collection<String> callValues2 = new LinkedList<>();
		callValues2.add("sensorname, sensorid");
		callValues2.add("'VS_0004', '11223'");
		// - Insert line into table
		connSubModel.updateElementValue("/aas/submodels/SQLTestSubmodel/properties/sensorNames", callValues2);
		
		// Get property value again
		Object value4 = connSubModel.readElementValue("/aas/submodels/SQLTestSubmodel/properties/sensorNames");
		System.out.println("Value:"+value4);

		
		// Delete property 'VS_0004'
		// - Collection that contains call values
		Collection<String> callValues4 = new LinkedList<>();
		callValues4.add("VS_0004");
		// - Delete sensor from table
		connSubModel.deleteElement("/aas/submodels/SQLTestSubmodel/properties/sensorNames", callValues4);
		
		// Get property value again
		Object value5 = connSubModel.readElementValue("/aas/submodels/SQLTestSubmodel/properties/sensorNames");
		System.out.println("Value:"+value5);
	}
}
