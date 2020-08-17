package org.eclipse.basyx.testsuite.regression.aas.metamodel.map.descriptor;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link AASDescriptor}
 * 
 * @author schnicke
 *
 */
public class TestAASDescriptor {
	
	private Map<String, Object> map;
	
	@Before
	public void initialize() {
		map = new HashMap<String, Object>();
		map.put(Referable.IDSHORT, "123");
		map.put(Identifiable.IDENTIFICATION, new Identifier(IdentifierType.IRDI, "123"));
		map.put(AssetAdministrationShell.ENDPOINTS, Arrays.asList(new HashMap<String, String>()));
		map.put(AssetAdministrationShell.SUBMODELS, new HashSet<SubmodelDescriptor>());
	}

	/**
	 * Tests retrieval of all registered submodel descriptors
	 */
	@Test
	public void testGetAllSubModels() {
		// Setup descriptor and add one submodel descriptor
		AASDescriptor descriptor = new AASDescriptor(new Identifier(IdentifierType.CUSTOM, "Test"), "http://a.b/c/aas");
		descriptor.addSubmodelDescriptor(new SubmodelDescriptor("SM1", new Identifier(IdentifierType.CUSTOM, "SM1"), "http://a.b/c/aas/submodels/SM1"));

		// Assert correct retrieval
		assertEquals(1, descriptor.getSubModelDescriptors().size());
		assertEquals("SM1", descriptor.getSubModelDescriptors().iterator().next().getIdentifier().getId());

		// Add a second descriptor
		descriptor.addSubmodelDescriptor(new SubmodelDescriptor("SM2", new Identifier(IdentifierType.CUSTOM, "SM2"), "http://a.b/c/aas/submodels/SM2"));

		// Assert correct retrieval
		assertEquals(2, descriptor.getSubModelDescriptors().size());
	}
	
	@Test(expected = MalformedRequestException.class)
	public void testValidateNoIdShort() {
		map.remove(Referable.IDSHORT);
		new AASDescriptor(map);
	}
	
	@Test(expected = MalformedRequestException.class)
	public void testValidateNullIdShort() {
		map.put(Referable.IDSHORT, null);
		new AASDescriptor(map);
	}
	
	@Test(expected = MalformedRequestException.class)
	public void testValidateWrongIdShort() {
		map.put(Referable.IDSHORT, 0);
		new AASDescriptor(map);
	}
	
	@Test(expected = MalformedRequestException.class)
	public void testValidateNoIdentification() {
		map.remove(Identifiable.IDENTIFICATION);
		new AASDescriptor(map);
	}
	
	@Test(expected = MalformedRequestException.class)
	public void testValidateNullIdentification() {
		map.put(Identifiable.IDENTIFICATION, null);
		new AASDescriptor(map);
	}
	
	@Test(expected = MalformedRequestException.class)
	public void testValidateWrongdentification() {
		map.put(Identifiable.IDENTIFICATION, "testId");
		new AASDescriptor(map);
	}
	
	@Test(expected = MalformedRequestException.class)
	public void testValidateNoEndpoints() {
		map.remove(AssetAdministrationShell.ENDPOINTS);
		new AASDescriptor(map);
	}
	
	@Test(expected = MalformedRequestException.class)
	public void testValidateNullEndpoints() {
		map.put(AssetAdministrationShell.ENDPOINTS, null);
		new AASDescriptor(map);
	}
	
	@Test(expected = MalformedRequestException.class)
	public void testValidateWrongEndpoints() {
		map.put(AssetAdministrationShell.ENDPOINTS, "testEndpoint");
		new AASDescriptor(map);
	}
	
	@Test(expected = MalformedRequestException.class)
	public void testValidateNoSubmodels() {
		map.remove(AssetAdministrationShell.SUBMODELS);
		new AASDescriptor(map);
	}
	
	@Test(expected = MalformedRequestException.class)
	public void testValidateNullSubmodels() {
		map.put(AssetAdministrationShell.SUBMODELS, null);
		new AASDescriptor(map);
	}
	
	@Test(expected = MalformedRequestException.class)
	public void testValidateWrongSubmodels() {
		map.put(AssetAdministrationShell.SUBMODELS, "testSubmodel");
		new AASDescriptor(map);
	}
}
