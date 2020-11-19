package org.eclipse.basyx.regression.AASServer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IFile;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedFile;
import org.eclipse.basyx.vab.protocol.api.IConnectorProvider;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Suite for testing that the XMLAAS servlet is set up correctly. The tests here
 * can be used by the servlet test itself and the integration test
 * 
 * @author schnicke, espen
 *
 */
public abstract class AASXSuite {
	private static Logger logger = LoggerFactory.getLogger(AASXSuite.class);

	protected IAASRegistryService aasRegistry;

	protected static final String aasShortId = "Festo_3S7PM0CP4BD";
	protected static final ModelUrn aasId = new ModelUrn("smart.festo.com/demo/aas/1/1/454576463545648365874");
	protected static final ModelUrn smId = new ModelUrn("www.company.com/ids/sm/4343_5072_7091_3242");
	protected static final String smShortId = "Nameplate";

	// Has to be individualized by each test inheriting from this suite
	protected static String aasEndpoint;
	protected static String smEndpoint;
	protected static String rootEndpoint;

	private ConnectedAssetAdministrationShellManager manager;

	// create a REST client
	private Client client = ClientBuilder.newClient();

	/**
	 * Before each test, a dummy registry is created and an AAS is added in the
	 * registry
	 */
	@Before
	public void setUp() {
		// Create a dummy registry to test integration of XML AAS
		aasRegistry = new InMemoryRegistry();
		AASDescriptor descriptor = new AASDescriptor(aasShortId, aasId, aasEndpoint);
		descriptor.addSubmodelDescriptor(new SubmodelDescriptor(smShortId, smId, smEndpoint));
		aasRegistry.register(descriptor);

		// Create a ConnectedAssetAdministrationShell using a
		// ConnectedAssetAdministrationShellManager
		IConnectorProvider connectorProvider = new HTTPConnectorProvider();
		manager = new ConnectedAssetAdministrationShellManager(aasRegistry, connectorProvider);
	}

	@Test
	public void testGetSingleAAS() throws Exception {
		ConnectedAssetAdministrationShell connectedAssetAdministrationShell = getConnectedAssetAdministrationShell();
		assertEquals(aasShortId, connectedAssetAdministrationShell.getIdShort());
	}

	@Test
	public void testGetSingleSubmodel() throws Exception {
		ISubModel subModel = getConnectedSubmodel();
		assertEquals(smShortId, subModel.getIdShort());
	}

	@Test
	public void testGetSingleModule() throws Exception {
		final String FILE_ENDING = "aasx/Nameplate/marking_rcm.jpg";
		final String FILE_PATH = rootEndpoint + "aasx/Nameplate/marking_rcm.jpg";
		checkFile(FILE_PATH);

		// Get the submdoel nameplate
		ISubModel nameplate = getConnectedSubmodel();
		// Get the submodel element collection marking_rcm
		ConnectedSubmodelElementCollection marking_rcm = (ConnectedSubmodelElementCollection) nameplate.getSubmodelElements().get("Marking_RCM");
		Collection<ISubmodelElement> values = marking_rcm.getValue().values();

		// navigate to the File element
		Iterator<ISubmodelElement> iter = values.iterator();
		while (iter.hasNext()) {
			ISubmodelElement element = iter.next();
			if (element instanceof ConnectedFile) {
				ConnectedFile connectedFile = (ConnectedFile) element;
				// get value of the file element

				String fileurl = connectedFile.getValue();
				assertTrue(fileurl.endsWith(FILE_ENDING));
			}
		}
	}

	@Test
	public void testAllFiles() throws Exception {
		logger.info("Checking all files");
		ConnectedAssetAdministrationShell aas = getConnectedAssetAdministrationShell();
		logger.info("AAS idShort: " + aas.getIdShort());
		logger.info("AAS identifier: " + aas.getIdentification().getId());
		Map<String, ISubModel> submodels = aas.getSubModels();
		logger.info("# Submodels: " + submodels.size());
		for (ISubModel sm : submodels.values()) {
			// FIXME: In Identification, there's a file referenced that is not contained in aasx folder. 
			// Since the current code only works with files in /aasx folder, this will create an error for now
			// Remove this after this issue is fixed!
			if (sm.getIdShort().equals("Identification")) {
				continue;
			}
			
			logger.info("Checking submodel: " + sm.getIdShort());
			checkElementCollectionFiles(sm.getSubmodelElements().values());
		}

	}

	private void checkElementCollectionFiles(Collection<ISubmodelElement> elements) {
		for (ISubmodelElement element : elements) {
			if (element instanceof IFile) {
				String fileUrl = ((IFile) element).getValue();
				checkFile(fileUrl);
			} else if (element instanceof ISubmodelElementCollection) {
				ISubmodelElementCollection col = (ISubmodelElementCollection) element;
				checkElementCollectionFiles(col.getSubmodelElements().values());
			}
		}
	}

	private void checkFile(String absolutePath) {
		// connect to the url of the aas
		WebTarget webTarget = client.target(absolutePath);
		logger.info("Checking file: " + absolutePath);
		Invocation.Builder invocationBuilder = webTarget.request();
		Response response = invocationBuilder.get();

		// validate the response
		assertEquals("Path check failed: " + absolutePath, 200, response.getStatus());
	}

	/**
	 * Gets the connected Asset Administration Shell
	 * 
	 * @return connected AAS
	 * @throws Exception
	 */
	private ConnectedAssetAdministrationShell getConnectedAssetAdministrationShell() throws Exception {
		return manager.retrieveAAS(aasId);
	}

	/**
	 * Gets the connected Submodel
	 * 
	 * @return connected SM
	 * @throws Exception
	 */
	private ISubModel getConnectedSubmodel() {
		return manager.retrieveSubModel(aasId, smId);
	}
}
