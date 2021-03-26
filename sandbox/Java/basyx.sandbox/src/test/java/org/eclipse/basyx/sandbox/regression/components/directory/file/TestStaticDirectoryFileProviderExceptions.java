package org.eclipse.basyx.sandbox.regression.components.directory.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.ServerErrorException;

import org.eclipse.basyx.regression.support.server.context.ComponentsRegressionContext;
import org.eclipse.basyx.testsuite.regression.vab.protocol.http.AASHTTPServerResource;
import org.eclipse.basyx.tools.webserviceclient.WebServiceJSONClient;
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
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testUnsupportedCalls() throws UnsupportedEncodingException {
		// Invoke BaSyx service calls via web services
		WebServiceJSONClient client = new WebServiceJSONClient();
		
		// Directory web service URL
		String wsURL = "http://localhost:8080/basys.components/Testsuite/Directory/CFGFile";
		

		
		// Register a new AAS (using POST)
		try {
			// Get a known AAS by its ID
			client.post(wsURL + "/api/v1/registry", "Some content");

			// Exception was not thrown
			fail("Expected exception indicating that feature is not implemented was not thrown");
		} catch (ServerErrorException e) {
			// Check return code for expected return value (501)
			assertEquals(501, e.getResponse().getStatus());
			
		}

		
		
		// Renew a specific AAS registration (using PUT)
		try {
			// Get a known AAS by its ID
			client.put(wsURL + "/api/v1/registry/urn:de.FHG:es.iese:aas:0.98:5:lab/" + URLEncoder.encode("microscope#A-19", "UTF-8"), "Some updated content");

			// Exception was not thrown
			fail("Expected exception indicating that feature is not implemented was not thrown");
		} catch (ServerErrorException e) {
			// Check return code for expected return value (501)
			assertEquals(501, e.getResponse().getStatus());
		}

		
		// Delete a specific AAS registration (using PUT)
		try {
			// Get a known AAS by its ID
			client.delete(wsURL+"/api/v1/registry/urn:de.FHG:es.iese:aas:0.98:5:lab/"+URLEncoder.encode("microscope#A-19","UTF-8"));

			// Exception was not thrown
			fail("Expected exception indicating that feature is not implemented was not thrown");
		} catch (ServerErrorException e) {
			// Check return code for expected return value (501)
			assertEquals(501, e.getResponse().getStatus());
		}

	}
}


