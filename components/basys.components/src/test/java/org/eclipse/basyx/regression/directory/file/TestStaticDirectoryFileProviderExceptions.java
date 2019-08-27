package org.eclipse.basyx.regression.directory.file;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URLEncoder;

import javax.ws.rs.ServerErrorException;

import org.eclipse.basyx.aas.api.webserviceclient.WebServiceJSONClient;
import org.eclipse.basyx.regression.support.server.context.ComponentsRegressionContext;
import org.eclipse.basyx.testsuite.support.backend.servers.AASHTTPServerResource;
import org.junit.ClassRule;
import org.junit.Test;



/**
 * Test queries to CFG file provider
 * 
 * @author kuhn
 *
 */
public class TestStaticDirectoryFileProviderExceptions {

	/** 
	 * Makes sure Tomcat Server is started
	 */
	@ClassRule
	public static AASHTTPServerResource res = new AASHTTPServerResource(new ComponentsRegressionContext());
	
	/**
	 * Execute test case that tests not implemented calls
	 */
	@Test
	public void testUnsupportedCalls() {
		// Invoke BaSyx service calls via web services
		WebServiceJSONClient client = new WebServiceJSONClient();
		
		// Directory web service URL
		String wsURL = "http://localhost:8080/basys.components/Testsuite/Directory/CFGFile";
		

		
		// Register a new AAS (using POST)
		try {
			// Get a known AAS by its ID
			client.post(wsURL+"/api/v1/registry", "{Some content}");

			// Exception was not thrown
			fail("Expected exception indicating that feature is not implemented was not thrown");
		} catch (ServerErrorException e) {
			// Check return code for expected return value (501)
			assertTrue(e.getResponse().getStatus() == 501);
			
		} catch (Exception e) {
			// Unexpected exception was thrown
			//fail("Unexpected Exception thrown:"+e);
		}

		
		
		// Renew a specific AAS registration (using PUT)
		try {
			// Get a known AAS by its ID
			client.put(wsURL+"/api/v1/registry/urn:de.FHG:es.iese:aas:0.98:5:lab/"+URLEncoder.encode("microscope#A-19","UTF-8"), "{Some updated content}");

			// Exception was not thrown
			fail("Expected exception indicating that feature is not implemented was not thrown");
		} catch (ServerErrorException e) {
			// Check return code for expected return value (501)
			assertTrue(e.getResponse().getStatus() == 501);
		} catch (Exception e) {
			// Unexpected exception was thrown
			//fail("Unexpected Exception thrown:"+e);
		}
		

		
		// Delete a specific AAS registration (using PUT)
		try {
			// Get a known AAS by its ID
			client.delete(wsURL+"/api/v1/registry/urn:de.FHG:es.iese:aas:0.98:5:lab/"+URLEncoder.encode("microscope#A-19","UTF-8"));

			// Exception was not thrown
			fail("Expected exception indicating that feature is not implemented was not thrown");
		} catch (ServerErrorException e) {
			// Check return code for expected return value (501)
			assertTrue(e.getResponse().getStatus() == 501);
		} catch (Exception e) {
			// Unexpected exception was thrown
			//fail("Unexpected Exception thrown:"+e);
		}

	}
}


