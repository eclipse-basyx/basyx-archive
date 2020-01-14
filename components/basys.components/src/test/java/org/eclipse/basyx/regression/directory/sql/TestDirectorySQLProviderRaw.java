package org.eclipse.basyx.regression.directory.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.regression.support.server.context.ComponentsRegressionContext;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.testsuite.regression.vab.protocol.http.AASHTTPServerResource;
import org.eclipse.basyx.tools.webserviceclient.WebServiceRawClient;
import org.eclipse.basyx.vab.coder.json.metaprotocol.MetaprotocolHandler;
import org.eclipse.basyx.vab.coder.json.serialization.DefaultTypeFactory;
import org.eclipse.basyx.vab.coder.json.serialization.GSONTools;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Test raw http queries to SQL directory provider.
 * 
 * @author kuhn, ps, espen
 *
 */
public class TestDirectorySQLProviderRaw {

	/**
	 * Makes sure Tomcat Server is started
	 */
	@ClassRule
	public static AASHTTPServerResource res = new AASHTTPServerResource(new ComponentsRegressionContext());

	/**
	 * Directory web service URL
	 */
	public static final String wsURL = "http://localhost:8080/basys.components/Testsuite/Directory/SQL";

	/**
	 * Serialization
	 */
	private static final GSONTools serializer = new GSONTools(new DefaultTypeFactory());
	private static final MetaprotocolHandler handler = new MetaprotocolHandler();

	/**
	 * Invoke BaSyx service calls via web services
	 */
	private static final WebServiceRawClient client = new WebServiceRawClient();
	private static final String registryUrl = wsURL + "/api/v1/registry/";

	/**
	 * AASDescriptor to test
	 */
	private static final IIdentifier id1 = new ModelUrn("urn:de.FHG:es.iese:aas:0.98:5:lab/microscope#A-166");
	private static final String endpoint1 = "www.endpoint.de";
	private static final AASDescriptor aasDescriptor1 = new AASDescriptor(id1, endpoint1);
	private static final String serializedDescriptor1 = serializer.serialize(aasDescriptor1);
	private static final IIdentifier id2 = new ModelUrn("urn:de.FHG:es.iese:aas:0.98:5:lab/microscope#A-167");
	private static final String endpoint2 = "www.endpoint2.de";
	private static final String endpoint2b = "www.endpoint2.de";
	private static final AASDescriptor aasDescriptor2 = new AASDescriptor(id2, endpoint2);
	private static final AASDescriptor aasDescriptor2b = new AASDescriptor(id2, endpoint2b);
	private static final String serializedDescriptor2 = serializer.serialize(aasDescriptor2);
	private static final String serializedDescriptor2b = serializer.serialize(aasDescriptor2b);
	private static final IIdentifier idUnknown = new ModelUrn("urn:de.FHG:es.iese:aas:0.98:5:lab/microscope#A-168");
	private static String aasUrl1, aasUrl2, aasUrlUnknown;

	@BeforeClass
	public static void setUpClass() throws UnsupportedEncodingException {
		aasUrl1 = registryUrl + URLEncoder.encode(id1.toString(), "UTF-8");
		aasUrl2 = registryUrl + URLEncoder.encode(id2.toString(), "UTF-8");
		aasUrlUnknown = registryUrl + URLEncoder.encode(idUnknown.toString(), "UTF-8");
	}

	@Before
	public void setUp() {
		// Post serialized descriptor to register it
		client.post(registryUrl, serializedDescriptor1);
		client.post(registryUrl, serializedDescriptor2);
	}

	@After
	public void tearDown() throws UnsupportedEncodingException {
		// Delete AAS registration
		client.delete(aasUrl1);
		client.delete(aasUrl2);
	}

	/**
	 * Execute test case that test working calls
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetterCalls() {
		// First test - get all locally registered AAS
		{
			// Get all locally registered AAS
			Collection<AASDescriptor> result = getResult(client.get(registryUrl));

			// Check if all AAS are contained in result
			assertEquals(2, result.size());
		}

		// Get a specific AAS (1)
		try {
			// Get a known AAS by its ID
			AASDescriptor result = new AASDescriptor((Map<String, Object>) getResult(client.get(aasUrl1)));

			// Check if all AAS are contained in result
			assertEquals(id1.getId(), result.getIdentifier().getId());
		} catch (Exception e) {
			fail("Get specific AAS test case did throw exception:" + e);
		}

		// Get a specific AAS (2)
		try {
			// Get a known AAS by its ID
			AASDescriptor result = new AASDescriptor((Map<String, Object>) getResult(client.get(aasUrl2)));

			// Check if all AAS are contained in result
			assertEquals(id2.getId(), result.getIdentifier().getId());
		} catch (Exception e) {
			fail("Get specific AAS test case did throw exception:" + e);
		}
	}

	/**
	 * Execute update test case
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateCall() throws UnsupportedEncodingException {
		// Update a specific AAS
		// Update AAS registration
		client.put(aasUrl2, serializedDescriptor2b);

		// Get a known AAS by its ID
		AASDescriptor result = new AASDescriptor((Map<String, Object>) getResult(client.get(aasUrl2)));
		// - Check updated registration
		assertEquals(endpoint2b, result.getFirstEndpoint());
	}

	/**
	 * Execute create/Delete test cases
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCreateDeleteCall() throws UnsupportedEncodingException {
		// Update a specific AAS
		// Delete AAS registration (make sure tests work also iff previous test suite
		// did fail)
		client.delete(aasUrl2);

		// Get a known AAS by its ID - check if AAS does not exist already
		Object result = getResult(client.get(aasUrl2));
		// - Check updated registration
		assertNull(result);

		// Create new AAS registration
		client.post(registryUrl, serializedDescriptor2);

		// Get a known AAS by its ID
		AASDescriptor result2 = new AASDescriptor((Map<String, Object>) getResult(client.get(aasUrl2)));

		assertEquals(endpoint2, result2.getFirstEndpoint()); // need deep json string compare here

		// Delete AAS registration
		client.delete(aasUrl2);

		// Check if it is really deleted
		result = getResult(client.get(aasUrl2));
		// - Check updated registration
		assertNull(result);
	}

	/**
	 * Execute test case that test non-working calls
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testNonWorkingCalls() throws UnsupportedEncodingException {
		// Get unknown AAS ID
		Object result = getResult(client.get(aasUrlUnknown));

		// Check if no AAS are contained in result
		assertNull(result);
	}

	@SuppressWarnings("unchecked")
	private <T> T getResult(String res) {
		try {
			return (T) handler.deserialize(res);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
