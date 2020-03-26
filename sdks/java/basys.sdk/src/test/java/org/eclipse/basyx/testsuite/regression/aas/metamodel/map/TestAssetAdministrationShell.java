package org.eclipse.basyx.testsuite.regression.aas.metamodel.map;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
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

		Set<SubmodelDescriptor> descriptors = new HashSet<>();
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

}
