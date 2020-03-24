package org.eclipse.basyx.testsuite.regression.aas.metamodel.map;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyType;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.testsuite.regression.aas.metamodel.AssetAdministrationShellSuite;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the map implementation of {@link IAssetAdministrationShell} based on
 * the AAS test suite
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
	public void testSetAndGetReference() {
		AssetAdministrationShell aas = retrieveShell();
		Reference ref = new Reference(new Key(KeyElements.ASSET, false, "123", KeyType.CUSTOM));
		aas.setAssetReference(ref);
		assertEquals(ref, aas.getAssetReference());
	}

	@Override
	public void testGetSubmodel() throws Exception {
		// Overwritten because getting submodels on local AAS is not supported
	}

}
