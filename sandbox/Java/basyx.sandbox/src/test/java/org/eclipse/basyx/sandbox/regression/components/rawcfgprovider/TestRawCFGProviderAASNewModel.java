package org.eclipse.basyx.sandbox.regression.components.rawcfgprovider;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.basyx.regression.support.directory.ComponentsTestsuiteDirectory;
import org.eclipse.basyx.regression.support.server.context.ComponentsRegressionContext;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.restapi.SubmodelElementProvider;
import org.eclipse.basyx.testsuite.regression.vab.protocol.http.AASHTTPServerResource;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.junit.ClassRule;
import org.junit.Test;



/**
 * Test queries to CFG file provider
 * 
 * @author kuhn
 *
 */
public class TestRawCFGProviderAASNewModel {

	
	/**
	 * VAB connection manager backend
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
	@SuppressWarnings({ "unchecked", "unused" })
	@Test
	public void test() throws Exception {

		// Connect to sub model "CfgFileTestAAS"
		VABElementProxy connSubModel = this.connManager.connectToVABElement("RawCfgFileTestAAS");

		
		// Create map with complex type
		Property prop = new Property ();
		prop.setIdShort("prop");
		// Create AAS structure on server
		connSubModel.createValue("/aas/submodels/rawSampleCFG/" + SubmodelElementProvider.PROPERTIES, prop);

		
		// Read complex property completely
		Map<String, Object> aasReadBack = (Map<String, Object>) connSubModel.getModelPropertyValue("/aas/submodels/rawSampleCFG/" + SubmodelElementProvider.PROPERTIES + "/prop");
		assertEquals(prop.getIdShort(), Property.createAsFacade(prop).getIdShort());
	}
}
