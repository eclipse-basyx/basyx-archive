package org.eclipse.basyx.regression.directory.file;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URLEncoder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.eclipse.basyx.regression.support.tools.InvokeWebService;
import org.junit.jupiter.api.Test;



/**
 * Test queries to CFG file provider
 * 
 * @author kuhn
 *
 */
class TestStaticDirectoryFileProvider {

	
	
	/**
	 * Execute test case that test working calls
	 */
	@Test
	void testWorkingCalls() {
		// Invoke service call via web services
		Client client = ClientBuilder.newClient();
		
		// Directory web service URL
		String wsURL = "http://localhost:8080/basys.components/Testsuite/Directory/CFGFile";
		
		
		// First test - get all locally registered AAS
		{
			// Get all locally registered AAS
			String result = InvokeWebService.invokeWebService(client,  wsURL+"/api/v1/registry");

			// Check if all AAS are contained in result
			assertTrue(result.contains("{content.aas1}"));
			assertTrue(result.contains("{content.aas2}"));
			assertTrue(result.contains("{content.aas3}"));
			assertTrue(result.contains("{content.aas4}"));
		}
		
		
		// Get a specific AAS (1)
		try {
			// Get a known AAS by its ID
			String result = InvokeWebService.invokeWebService(client,  wsURL+"/api/v1/registry/urn:de.FHG:es.iese:0.98:5:lab/"+URLEncoder.encode("microscope#A-19","UTF-8"));

			// Check if all AAS are contained in result
			assertTrue(result.equals("{content.aas1}"));
		} catch (Exception e) {
			fail("Get specific AAS test case did throw exception:"+e);
		}

		
		// Get a specific AAS (2)
		try {
			// Get a known AAS by its ID
			String result = InvokeWebService.invokeWebService(client,  wsURL+"/api/v1/registry/urn:de.FHG:es.iese:0.98:5:lab/"+URLEncoder.encode("microscope#A-18","UTF-8"));

			// Check if all AAS are contained in result
			assertTrue(result.equals("{content.aas2}"));
		} catch (Exception e) {
			fail("Get specific AAS test case did throw exception:"+e);
		}

		
		// Get a specific AAS (3)
		try {
			// Get a known AAS by its ID
			String result = InvokeWebService.invokeWebService(client,  wsURL+"/api/v1/registry/urn:de.FHG:es.iese:0.98:5:lab/"+URLEncoder.encode("microscope#A-17","UTF-8"));

			// Check if all AAS are contained in result
			assertTrue(result.equals("{content.aas3}"));
		} catch (Exception e) {
			fail("Get specific AAS test case did throw exception:"+e);
		}

		
		// Get a specific AAS (4)
		try {
			// Get a known AAS by its ID
			String result = InvokeWebService.invokeWebService(client,  wsURL+"/api/v1/registry/urn:de.FHG:es.iese:0.98:5:lab/"+URLEncoder.encode("microscope#A-16","UTF-8"));

			// Check if all AAS are contained in result
			assertTrue(result.equals("{content.aas4}"));
		} catch (Exception e) {
			fail("Get specific AAS test case did throw exception:"+e);
		}
	}
	
	
	
	/**
	 * Execute test case that test non-working calls
	 */
	@Test
	void testNonWorkingCalls() {
		// Invoke service call via web services
		Client client = ClientBuilder.newClient();
		
		// Directory web service URL
		String wsURL = "http://localhost:8080/basys.components/Testsuite/Directory/CFGFile";

		
		// Get unknown AAS ID
		try {
			// Get a known AAS by its ID
			String result = InvokeWebService.invokeWebService(client,  wsURL+"/api/v1/registry/urn:de.FHG:es.iese:0.98:5:lab/"+URLEncoder.encode("unknown","UTF-8"));

			// Check if all AAS are contained in result
			assertTrue(result.equals(""));
		} catch (Exception e) {
			fail("Get specific AAS test case did throw exception:"+e);
		}		
	}
}


