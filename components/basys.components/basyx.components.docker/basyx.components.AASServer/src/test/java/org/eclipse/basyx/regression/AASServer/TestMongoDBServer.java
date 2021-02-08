package org.eclipse.basyx.regression.AASServer;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.components.aas.AASServerComponent;
import org.eclipse.basyx.components.aas.configuration.AASServerBackend;
import org.eclipse.basyx.components.aas.configuration.BaSyxAASServerConfiguration;
import org.eclipse.basyx.components.aas.mongodb.MongoDBAASAggregator;
import org.eclipse.basyx.components.aas.mongodb.MongoDBSubmodelAPI;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxMongoDBConfiguration;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Tests the component using the test suite
 * 
 * @author espen
 *
 */
public class TestMongoDBServer extends AASServerSuite {

	private static AASServerComponent component;
	private static BaSyxMongoDBConfiguration mongoDBConfig;

	@Override
	protected String getURL() {
		return component.getURL() + "/shells";
	}

	@BeforeClass
	public static void setUpClass() throws ParserConfigurationException, SAXException, IOException {
		// just reset the data with this default db configuration
		new MongoDBAASAggregator(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH).reset();

		// Setup component configuration
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
		contextConfig.loadFromResource(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH);
		mongoDBConfig = new BaSyxMongoDBConfiguration();
		BaSyxAASServerConfiguration aasConfig = new BaSyxAASServerConfiguration(AASServerBackend.MONGODB, "");

		// Start component
		component = new AASServerComponent(contextConfig, aasConfig, mongoDBConfig);
		component.startComponent();
	}

	@Test
	public void testAddSubmodelPersistency() throws Exception {
		testAddAAS();

		SubModel sm = new SubModel("MongoDB", new Identifier(IdentifierType.CUSTOM, "MongoDBId"));
		manager.createSubModel(new ModelUrn(aasId), sm);

		MongoDBSubmodelAPI api = new MongoDBSubmodelAPI(mongoDBConfig, sm.getIdentification().getId());
		ISubModel persistentSM = api.getSubmodel();
		assertEquals("MongoDB", persistentSM.getIdShort());
	}

	@AfterClass
	public static void tearDownClass() {
		component.stopComponent();
	}
}
