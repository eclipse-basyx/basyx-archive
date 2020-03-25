package org.eclipse.basyx.testsuite.regression.aas.metamodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyType;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.property.ISingleProperty;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.junit.Test;


/**
 * Test suite for AAS testing. <br />
 * Can be extended to test arbitrary AAS implementations, as long as they
 * implement {@link IAssetAdministrationShell}
 * 
 * 
 * @author schnicke
 *
 */
public abstract class AssetAdministrationShellSuite {


	protected static final Reference EXPECTEDASSETREF = new Reference(new Key(KeyElements.ASSET, false, "AssetRef", KeyType.CUSTOM));

	// String constants used in this test case
	protected static final IIdentifier SMID = new Identifier(IdentifierType.CUSTOM, "smId");
	protected static final IIdentifier AASID = new Identifier(IdentifierType.CUSTOM, "aasId");
	protected static final String SMIDSHORT = "smName";
	protected static final String AASIDSHORT = "aasName";
	protected static final String PROPID = "propId";
	protected static final int PROPVAL = 11;

	/**
	 * Abstract method returning the IAssetAdministrationShell implementation to
	 * test
	 * 
	 * @return
	 */
	protected abstract IAssetAdministrationShell retrieveShell();

	/**
	 * Sets up a baseline AAS that can be used for concrete AAS implementation
	 * initialization
	 * 
	 * @return
	 */
	protected static AssetAdministrationShell retrieveBaselineShell() {
		// Create descriptor for the SubModel
		SubmodelDescriptor smDescriptor = new SubmodelDescriptor(retrieveBaselineSM(), "");

		// Create an AAS containing a reference to the created SubModel
		AssetAdministrationShell aas = new AssetAdministrationShell();
		aas.addSubModel(smDescriptor);
		aas.setIdShort(AASIDSHORT);
		aas.setAssetReference(EXPECTEDASSETREF);

		return aas;
	}

	/**
	 * Sets up a baseline SM that can be used for concrete SM implementation
	 * initialization
	 * 
	 * @return
	 */
	protected static SubModel retrieveBaselineSM() {
		// Create a SubModel containing no operations and one property
		Property p = new Property(PROPVAL);
		p.setIdShort(PROPID);

		SubModel sm = new SubModel();
		sm.addSubModelElement(p);
		sm.setIdShort(SMIDSHORT);

		return sm;
	}

	@Test
	public void testAssetRef() {
		assertEquals(EXPECTEDASSETREF, retrieveShell().getAssetReference());
	}

	/**
	 * Tests the getId() function
	 */
	@Test
	public void testGetId() {
		assertEquals(AASIDSHORT, retrieveShell().getIdShort());
	}

	/**
	 * Tests retrieving the contained SubModels
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetSubmodel() throws Exception {
		IAssetAdministrationShell shell = retrieveShell();

		// Check if the number of SubModels is as expected
		assertEquals(1, shell.getSubModels().size());

		// Check if the contained SubModel id is as expected
		assertTrue(shell.getSubModels().containsKey(SMIDSHORT));

		// Check if the submodel has been retrieved correctly
		ISubModel sm = shell.getSubModels().get(SMIDSHORT);
		ISingleProperty prop = (ISingleProperty) sm.getDataElements().get(PROPID);
		assertEquals(PROPVAL, prop.get());
	}
}
