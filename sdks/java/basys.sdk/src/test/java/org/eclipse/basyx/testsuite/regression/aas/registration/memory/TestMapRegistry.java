package org.eclipse.basyx.testsuite.regression.aas.registration.memory;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.registration.memory.MapRegistry;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.vab.exception.provider.ResourceAlreadyExistsException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests functionalities of {@link MapRegistry} for their correctness
 * Includes test cases for exceptions
 * 
 * @author haque
 *
 */
public class TestMapRegistry {
	private static final String ID_SHORT = "AASId1";
	private static final IdentifierType ID_TYPE = IdentifierType.IRI;
	private static final String ID_SHORT2 = "AASId2";
	private static final IdentifierType ID_TYPE2 = IdentifierType.IRDI;
	
	private MapRegistry mapRegistry;
	
	@Before
	public void buildMapRegistry() {
		Map<String, AASDescriptor> map = new HashMap<String, AASDescriptor>();
		map.put(ID_SHORT, getExistingAasDescriptor());
		mapRegistry = new MapRegistry(map);
	}
	
	@Test
	public void testRegisterOnly() {
		AASDescriptor descriptorToRegister = getAasDescriptor();
		mapRegistry.registerOnly(descriptorToRegister);
		AASDescriptor descriptor = mapRegistry.lookupAAS(new Identifier(ID_TYPE2, ID_SHORT2));
		assertEquals(descriptorToRegister, descriptor);
	}
	
	@Test(expected = ResourceAlreadyExistsException.class)
	public void testRegisterOnlyAlreadyExisting() {
		AASDescriptor descriptorToRegister = getExistingAasDescriptor();
		mapRegistry.registerOnly(descriptorToRegister);
	}
	
	@Test
	public void testRegister() {
		AASDescriptor existingDescriptor = getExistingAasDescriptor();
		mapRegistry.register(existingDescriptor);
		Collection<AASDescriptor> descriptors = mapRegistry.lookupAll();
		assertEquals(Collections.singletonList(existingDescriptor), descriptors);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testDelete() {
		Identifier identifier = new Identifier(IdentifierType.CUSTOM, "nonExistent");
		mapRegistry.delete(identifier);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteNotExistingIdentifier() {
		mapRegistry.delete(new Identifier(IdentifierType.CUSTOM, "nonExistent"), "nonExistentSubModelId");
	} 

	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteNotExistingSubModel() {
		mapRegistry.delete(new Identifier(ID_TYPE, ID_SHORT), "nonExistentSubModelId");
	} 
	
	private AASDescriptor getExistingAasDescriptor() {
		return new AASDescriptor(
				ID_SHORT, new Identifier(ID_TYPE, ID_SHORT), "http://test");
	}
	
	private AASDescriptor getAasDescriptor() {
		return new AASDescriptor(
				ID_SHORT2, new Identifier(ID_TYPE2, ID_SHORT2), "http://test2");
	}
}
