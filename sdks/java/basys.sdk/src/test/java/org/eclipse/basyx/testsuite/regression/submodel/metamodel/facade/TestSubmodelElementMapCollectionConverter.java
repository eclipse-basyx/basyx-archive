package org.eclipse.basyx.testsuite.regression.submodel.metamodel.facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.facade.SubmodelElementMapCollectionConverter;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.vab.support.TypeDestroyer;
import org.junit.Test;

/**
 * Tests for SubmodelElementMapCollectionConverter
 * 
 * @author conradi
 *
 */
public class TestSubmodelElementMapCollectionConverter {

	private static final String ID_SHORT = "testElement";


	@Test
	public void testMapToSM() {
		SubModel sm = getSM();
		
		// Replace the smElement Map with a Collection
		sm.put(SubModel.SUBMODELELEMENT, sm.getSubmodelElements().values());
		
		// Make a Map from the SM, as if it was transferred over the VAB
		Map<String, Object> map = TypeDestroyer.destroyType(sm);
		
		
		sm = SubmodelElementMapCollectionConverter.mapToSM(map);
		
		assertTrue(sm.get(SubModel.SUBMODELELEMENT) instanceof Map<?, ?>);
		
		assertNotNull(sm.getSubmodelElements().get(ID_SHORT));
		assertTrue(sm.getSubmodelElements().get(ID_SHORT) instanceof Property);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSMToMap() {
		SubModel sm = getSM();
		
		Map<String, Object> map = SubmodelElementMapCollectionConverter.smToMap(sm);
		
		assertTrue(map.get(SubModel.SUBMODELELEMENT) instanceof Collection<?>);
		assertEquals(1, ((Collection<ISubmodelElement>) map.get(SubModel.SUBMODELELEMENT)).size());
	}


	private SubModel getSM() {
		SubModel sm = new SubModel("submodelIdShort", new ModelUrn("submodelUrn"));
		Property property = new Property();
		property.setIdShort(ID_SHORT);
		
		sm.addSubModelElement(property);
		return sm;
	}
	
	
}
