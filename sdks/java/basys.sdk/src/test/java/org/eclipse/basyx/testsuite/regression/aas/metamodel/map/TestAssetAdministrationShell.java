package org.eclipse.basyx.testsuite.regression.aas.metamodel.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.api.parts.IConceptDictionary;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.metamodel.map.parts.ConceptDictionary;
import org.eclipse.basyx.aas.metamodel.map.security.Security;
import org.eclipse.basyx.submodel.metamodel.api.dataspecification.IEmbeddedDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.dataspecification.EmbeddedDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.parts.ConceptDescription;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.testsuite.regression.aas.metamodel.AssetAdministrationShellSuite;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the map implementation of {@link IAssetAdministrationShell} based on
 * the AAS test suite. <br />
 * Additionally to the test suite, the setters of the map implementation are
 * tested
 * 
 * @author schnicke
 *
 */
public class TestAssetAdministrationShell extends AssetAdministrationShellSuite {
	private static final Reference REFERENCE = new Reference(new Key(KeyElements.ASSET, true, "testValue", IdentifierType.IRI));
	
	private AssetAdministrationShell shell;

	/**
	 * Ensures that each test case is working on a fresh copy of the AAS
	 */
	@Before
	public void buildShell() {
		shell = retrieveBaselineShell();
	}

	@Override
	protected AssetAdministrationShell retrieveShell() {
		return shell;
	}

	@Test
	public void testSetSubmodelDescriptors() {
		AssetAdministrationShell aas = new AssetAdministrationShell();

		// Set new Submodel descriptors
		Identifier id = new Identifier(IdentifierType.CUSTOM, "identifier");
		String idShort = "idShort";
		String httpEndpoint = "http://endpoint";

		Collection<SubmodelDescriptor> descriptors = new HashSet<>();
		descriptors.add(new SubmodelDescriptor(idShort, id, httpEndpoint));

		aas.setSubModels(descriptors);

		// Check for correct setting
		descriptors = aas.getSubModelDescriptors();
		assertEquals(1, descriptors.size());
		SubmodelDescriptor desc = descriptors.iterator().next();
		assertEquals(idShort, desc.getIdShort());
		
		// Check for correct addition
		// TODO: This could be moved to Suite when API is clear
		Identifier id2 = new Identifier(IdentifierType.CUSTOM, "identifier2");
		String idShort2 = "idShort2";
		String httpEndpoint2 = "http://endpoint2";

		aas.addSubModel(new SubmodelDescriptor(idShort2, id2, httpEndpoint2));
		descriptors = aas.getSubModelDescriptors();
		assertEquals(2, descriptors.size());

		// Select new descriptor
		desc = descriptors.stream().filter(d -> d.getIdShort().equals(idShort2)).findFirst().get();
		assertEquals(idShort2, desc.getIdShort());
	}

	@Override
	public void testGetSubmodel() throws Exception {
		// Overwritten because getting submodels on local AAS is not supported
	}
	
	@Test
	public void testSetEndpoint() {
		String endpoint = "testEndpoint.com";
		String endpointType = "http";
		shell.setEndpoint(endpoint, endpointType);
		List<HashMap<String, String>> endPoints = shell.getEndpoints();
		HashMap<String, String> map = endPoints.iterator().next();
		assertTrue(map.containsValue(endpoint));
		assertTrue(map.containsValue(endpointType));
	}
	
	@Test
	public void testSetDataSpecificationReferences() {
		Collection<IReference> refs = Collections.singleton(REFERENCE);
		shell.setDataSpecificationReferences(refs);
		assertEquals(refs, shell.getDataSpecificationReferences());
	}
	
	@Test
	public void testSetEmbeddedDataSpecifications() {
		EmbeddedDataSpecification embeddedDataSpecification = new EmbeddedDataSpecification();
		Collection<IEmbeddedDataSpecification> specifications = Collections.singleton(embeddedDataSpecification);
		shell.setEmbeddedDataSpecifications(specifications);
		assertEquals(specifications, shell.getEmbeddedDataSpecifications());
	}
	
	@Test
	public void testSecurity() {
		Security security = new Security();
		shell.setSecurity(security);
		assertEquals(security, shell.getSecurity());
	} 
	
	@Test
	public void testSetParent() {
		shell.setParent(REFERENCE);
		assertEquals(REFERENCE, shell.getParent());
	}
	
	@Test
	public void testAddConceptDescription() {
		IdentifierType idType = IdentifierType.IRI;
		String id = "testId";
		ConceptDescription description = new ConceptDescription();
		description.setIdentification(idType, id);
		description.setCategory("testCategory");
		shell.addConceptDescription(description);
		Collection<IConceptDictionary> dictionaries = new HashSet<IConceptDictionary>();
		ConceptDictionary dictionary = new ConceptDictionary();
		dictionary.addConceptDescription(description);
		dictionaries.add(dictionary);
		assertEquals(dictionaries, shell.getConceptDictionary());
	}
}
