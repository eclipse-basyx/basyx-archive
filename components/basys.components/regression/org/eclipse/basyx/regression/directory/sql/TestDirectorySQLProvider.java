package org.eclipse.basyx.regression.directory.sql;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URLEncoder;

import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.IdentifierType;
import org.eclipse.basyx.regression.support.server.AASHTTPServerResource;
import org.eclipse.basyx.regression.support.server.context.ComponentsRegressionContext;
import org.eclipse.basyx.tools.webserviceclient.WebServiceRawClient;
import org.junit.ClassRule;
import org.junit.Test;

import basys.examples.aasdescriptor.AASDescriptor;

/**
 * Test queries to SQL directory provider
 * 
 * @author kuhn
 *
 */
public class TestDirectorySQLProvider {

	/** 
	 * Makes sure Tomcat Server is started
	 */
	@ClassRule
	public static AASHTTPServerResource res = AASHTTPServerResource.getTestResource(new ComponentsRegressionContext());
	
	/**
	 * Execute test case that test working calls
	 */
	@Test
	public void testGetterCalls() {
		// Invoke BaSyx service calls via web services
		WebServiceRawClient client = new WebServiceRawClient();
		
		// Directory web service URL
		String wsURL = "http://localhost:8080/basys.components/Testsuite/Directory/SQL";
		
		
		// First test - get all locally registered AAS
		{
			// Get all locally registered AAS
			String result = client.get(wsURL+"/api/v1/registry");
			
			// Check if all AAS are contained in result
			assertTrue(result.contains("{content.aas1}"));
			assertTrue(result.contains("{content.aas2}"));
			assertTrue(result.contains("{content.aas3}"));
			assertTrue(result.contains("{content.aas4}"));
		}
		
		
		// Get a specific AAS (1)
		try {
			// Get a known AAS by its ID
			String result = client.get(wsURL+"/api/v1/registry/urn:de.FHG:es.iese:aas:0.98:5:lab/"+URLEncoder.encode("microscope#A-19","UTF-8"));


			System.out.println("Res:"+result);

			// Check if all AAS are contained in result
			assertTrue(result.equals("{content.aas1}"));
		} catch (Exception e) {
			fail("Get specific AAS test case did throw exception:"+e);
		}

		
		// Get a specific AAS (2)
		try {
			// Get a known AAS by its ID
			String result = client.get(wsURL+"/api/v1/registry/urn:de.FHG:es.iese:aas:0.98:5:lab/"+URLEncoder.encode("microscope#A-18","UTF-8"));

			// Check if all AAS are contained in result
			assertTrue(result.equals("{content.aas2}"));
		} catch (Exception e) {
			fail("Get specific AAS test case did throw exception:"+e);
		}

		
		// Get a specific AAS (3)
		try {
			// Get a known AAS by its ID
			String result = client.get(wsURL+"/api/v1/registry/urn:de.FHG:es.iese:aas:0.98:5:lab/"+URLEncoder.encode("microscope#A-17","UTF-8"));

			// Check if all AAS are contained in result
			assertTrue(result.equals("{content.aas3}"));
		} catch (Exception e) {
			fail("Get specific AAS test case did throw exception:"+e);
		}

		
		// Get a specific AAS (4)
		try {
			// Get a known AAS by its ID
			String result = client.get(wsURL+"/api/v1/registry/urn:de.FHG:es.iese:aas:0.98:5:lab/"+URLEncoder.encode("microscope#A-16","UTF-8"));

			// Check if all AAS are contained in result
			assertTrue(result.equals("{content.aas4}"));
		} catch (Exception e) {
			fail("Get specific AAS test case did throw exception:"+e);
		}
	}
	

	/**
	 * Execute update test case
	 */
	@Test
	public void testUpdateCall() {
		// Invoke BaSyx service calls via web services
		WebServiceRawClient client = new WebServiceRawClient();
		
		// Directory web service URL
		String wsURL = "http://localhost:8080/basys.components/Testsuite/Directory/SQL";


		// Update a specific AAS
		try {
			// Update AAS registration
			client.put(wsURL+"/api/v1/registry/urn:de.FHG:es.iese:aas:0.98:5:lab/"+URLEncoder.encode("microscope#A-16","UTF-8"), "{content.aas5}");

			// Get a known AAS by its ID
			String result = client.get(wsURL+"/api/v1/registry/urn:de.FHG:es.iese:aas:0.98:5:lab/"+URLEncoder.encode("microscope#A-16","UTF-8"));
			// - Check updated registration
			assertTrue(result.equals("{content.aas5}"));
			
			// Update AAS registration
			client.put(wsURL+"/api/v1/registry/urn:de.FHG:es.iese:aas:0.98:5:lab/"+URLEncoder.encode("microscope#A-16","UTF-8"), "{content.aas4}");

			// Get a known AAS by its ID
			String result2 = client.get(wsURL+"/api/v1/registry/urn:de.FHG:es.iese:aas:0.98:5:lab/"+URLEncoder.encode("microscope#A-16","UTF-8"));
			// - Check updated registration
			assertTrue(result2.equals("{content.aas4}"));

		} catch (Exception e) {
			fail("Update AAS test case did throw exception:"+e);
		}
	}

	
	/**
	 * Execute create/Delete test cases
	 */
	@Test
	public void testCreateDeleteCall() {
		// Invoke BaSyx service calls via web services
		WebServiceRawClient client = new WebServiceRawClient();
		
		// Directory web service URL
		String wsURL = "http://localhost:8080/basys.components/Testsuite/Directory/SQL";

		// Update a specific AAS
		try {
			// Delete AAS registration (make sure tests work also iff previous test suite did fail)
			client.delete(wsURL+"/api/v1/registry/urn:de.FHG:es.iese:aas:0.98:5:lab/"+URLEncoder.encode("microscope#A-166","UTF-8"));
			
			// Get a known AAS by its ID - check if AAS does not exist already
			String result0 = client.get(wsURL+"/api/v1/registry/urn:de.FHG:es.iese:aas:0.98:5:lab/"+URLEncoder.encode("microscope#A-166","UTF-8"));
			// - Check updated registration
			assertTrue(result0.equals(""));
			
			// Create and register AAS descriptor
			// - Create AAS descriptor
			AASDescriptor aasDescriptor = new AASDescriptor("urn:de.FHG:es.iese:aas:0.98:5:lab/microscope#A-166", IdentifierType.URI, "www.endpoint.de");
			// - Create new AAS registration
			client.post(wsURL + "/api/v1/registry", GSONTools.Instance.getJsonString(GSONTools.Instance.serialize(aasDescriptor)));

			// Get a known AAS by its ID
			String result = client.get(wsURL+"/api/v1/registry/urn:de.FHG:es.iese:aas:0.98:5:lab/"+URLEncoder.encode("microscope#A-166","UTF-8"));
			// - Check updated registration
			assertTrue(result.equals("{\"metaData\":{\"size\":0,\"basystype\":\"map\"},\"endpoints\":{\"0\":{\"typeid\":\"string\",\"value\":\"www.endpoint.de\",\"basystype\":\"value\"},\"size\":1,\"basystype\":\"collection\"},\"identification\":{\"idType\":{\"typeid\":\"string\",\"value\":\"URI\",\"basystype\":\"value\"},\"size\":2,\"id\":{\"typeid\":\"string\",\"value\":\"urn:de.FHG:es.iese:aas:0.98:5:lab/microscope#A-166\",\"basystype\":\"value\"},\"basystype\":\"map\"},\"size\":9,\"idShort\":{\"typeid\":\"string\",\"value\":\"\",\"basystype\":\"value\"},\"administration\":{\"size\":3,\"hasDataSpecification\":{\"size\":0,\"basystype\":\"set\"},\"version\":{\"typeid\":\"string\",\"value\":\"\",\"basystype\":\"value\"},\"basystype\":\"map\",\"revision\":{\"typeid\":\"string\",\"value\":\"\",\"basystype\":\"value\"}},\"category\":{\"typeid\":\"string\",\"value\":\"\",\"basystype\":\"value\"},\"asset\":{\"parent\":{\"basystype\":\"null\"},\"identification\":{\"idType\":{\"typeid\":\"string\",\"value\":\"IRDI\",\"basystype\":\"value\"},\"size\":2,\"id\":{\"typeid\":\"string\",\"value\":\"\",\"basystype\":\"value\"},\"basystype\":\"map\"},\"size\":9,\"idShort\":{\"typeid\":\"string\",\"value\":\"\",\"basystype\":\"value\"},\"hasDataSpecification\":{\"size\":0,\"basystype\":\"set\"},\"kind\":{\"basystype\":\"null\"},\"administration\":{\"basystype\":\"null\"},\"description\":{\"typeid\":\"string\",\"value\":\"\",\"basystype\":\"value\"},\"category\":{\"typeid\":\"string\",\"value\":\"\",\"basystype\":\"value\"},\"basystype\":\"map\",\"assetIdentificationModel\":{\"basystype\":\"null\"}},\"descriptions\":{\"size\":0,\"basystype\":\"collection\"},\"basystype\":\"map\",\"submodels\":{\"size\":0,\"basystype\":\"collection\"}}"));
			
			// Delete AAS registration
			client.delete(wsURL+"/api/v1/registry/urn:de.FHG:es.iese:aas:0.98:5:lab/"+URLEncoder.encode("microscope#A-166","UTF-8"));

			// Get a known AAS by its ID
			String result2 = client.get(wsURL+"/api/v1/registry/urn:de.FHG:es.iese:aas:0.98:5:lab/"+URLEncoder.encode("microscope#A-166","UTF-8"));
			// - Check updated registration
			assertTrue(result2.equals(""));

		} catch (Exception e) {
			fail("Update AAS test case did throw exception:"+e);
		}
	}

	
	
	/**
	 * Execute test case that test non-working calls
	 */
	@Test
	public void testNonWorkingCalls() {
		// Invoke service call via web services
		WebServiceRawClient client = new WebServiceRawClient();
		
		// Directory web service URL
		String wsURL = "http://localhost:8080/basys.components/Testsuite/Directory/CFGFile";

		
		// Get unknown AAS ID
		try {
			// Get a known AAS by its ID
			String result = client.get(wsURL+"/api/v1/registry/urn:de.FHG:es.iese:aas:0.98:5:lab/"+URLEncoder.encode("unknown","UTF-8"));

			// Check if all AAS are contained in result
			assertTrue(result.equals(""));
		} catch (Exception e) {
			fail("Get specific AAS test case did throw exception:"+e);
		}		
	}
}


