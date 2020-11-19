package org.eclipse.basyx.regression.sql;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.regression.support.directory.ComponentsTestsuiteDirectory;
import org.eclipse.basyx.regression.support.server.context.ComponentsRegressionContext;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.testsuite.regression.vab.protocol.http.AASHTTPServerResource;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;



/**
 * Test SQL queries
 * 
 * @author kuhn
 *
 */
public class SQLQueriesTest {

	
	/**
	 * Store HTTP asset administration shell manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(new ComponentsTestsuiteDirectory(), new HTTPConnectorProvider());

	/** 
	 * Makes sure Tomcat Server is started
	 */
	@ClassRule
	public static AASHTTPServerResource res = new AASHTTPServerResource(new ComponentsRegressionContext());
	
	
	/**
	 * Test basic queries
	 */
	@Test
	@Ignore
	public void test() throws Exception {

		// Connect to sub model "CfgFileTestAAS"
		VABElementProxy connSubModel = this.connManager.connectToVABElement("SQLTestSubmodel");

		
		// Get sub model
		Object value0A = connSubModel.getModelPropertyValue("/aas/submodels/SQLTestSubmodel");

		
		// Get properties
		Object value0B = connSubModel.getModelPropertyValue("/aas/submodels/SQLTestSubmodel/dataElements");

		
		// Get property value
		Object value1 = connSubModel
				.getModelPropertyValue("/aas/submodels/SQLTestSubmodel/dataElements/sensorNames/value");

		// Get property value with meta data
		Object value1a = connSubModel.getModelPropertyValue("/aas/submodels/SQLTestSubmodel/dataElements/sensorNames");

		
		// Create a new property
		// - HashMap that contains new table line
		Map<String, Object> newTableLine = new HashMap<>();
			newTableLine.put("sensorname", "VS_0003");
			newTableLine.put("sensorid",   "033542");
		Property p = new Property(newTableLine);
		p.setIdShort("sensorNames");
		// - Insert line into table
		connSubModel.createValue("/aas/submodels/SQLTestSubmodel/dataElements", p);
		
		// Get property value again
		Object value2 = connSubModel
				.getModelPropertyValue("/aas/submodels/SQLTestSubmodel/dataElements/sensorNames/value");

		Object value2a = connSubModel.getModelPropertyValue("/aas/submodels/SQLTestSubmodel/dataElements/sensorNames");

		
		// Update property value
		// - Here this adds a new value into the table
		// - Collection that contains call values
		Map<String, Object> updatedTableLine = new HashMap<>();
			updatedTableLine.put("sensorname", "VS_0004");
			updatedTableLine.put("sensorid", "033542");
		// - Update table line
		connSubModel.setModelPropertyValue("/aas/submodels/SQLTestSubmodel/dataElements/sensorNames/value",
				updatedTableLine);

		// Get property value again
		Object value3 = connSubModel
				.getModelPropertyValue("/aas/submodels/SQLTestSubmodel/dataElements/sensorNames/value");

		
		// Delete property with ID 033542
		// - Map that contains call values
		Map<String, Object> removedTableLine = new HashMap<>();
			removedTableLine.put("sensorid", "033542");
		// - Delete sensor from table
		connSubModel.deleteValue("/aas/submodels/SQLTestSubmodel/dataElements/sensorNames/value", removedTableLine);
		
		// Get property value again
		Object value4 = connSubModel
				.getModelPropertyValue("/aas/submodels/SQLTestSubmodel/dataElements/sensorNames/value");

		
		
		// Get property meta data value
		Object value5 = connSubModel
				.getModelPropertyValue("/aas/submodels/SQLTestSubmodel/dataElements/sensorNames/category");
	}
}
