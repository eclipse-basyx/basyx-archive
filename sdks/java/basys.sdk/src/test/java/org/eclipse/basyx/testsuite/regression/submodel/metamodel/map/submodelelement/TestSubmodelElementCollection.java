package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.submodelelement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Formula;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifiable;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests constructor and getter of {@link SubmodelElementCollection} for their
 * correctness
 * 
 * @author haque
 *
 */
public class TestSubmodelElementCollection {
	private static final Reference REFERENCE = new Reference(new Identifier(IdentifierType.CUSTOM, "testValue"), KeyElements.ACCESSPERMISSIONRULE, false);
	private Collection<ISubmodelElement> elements = getSubmodelElements(); 
	private SubmodelElementCollection elementCollection;
	
	@Before
	public void buildSubmodelElementCollection() {
		elementCollection = new SubmodelElementCollection(elements, true, true);
	} 
	
	@Test
	public void testConstructor() {
		assertTrue(elementCollection.isAllowDuplicates());
		assertTrue(elementCollection.isOrdered());
		assertEquals(elements, elementCollection.getValue());
	} 
	
	@Test
	public void testAddValue() {
		ISubmodelElement element = new Property("testIdNew");
		elementCollection.addElement(element);
		elements.add(element);
		assertEquals(elements, elementCollection.getValue());
	} 
	
	@Test
	public void testSetDataSpecificationReferences() {
		Collection<IReference> refs = Collections.singleton(REFERENCE);
		elementCollection.setDataSpecificationReferences(refs);
		assertEquals(refs, elementCollection.getDataSpecificationReferences());
	} 
	
	@Test
	public void testSetValue() {
		Collection<ISubmodelElement> elements = new ArrayList<ISubmodelElement>();
		elements.add(new Property("testId1"));
		elementCollection.setValue(elements);
		assertEquals(elements, elementCollection.getValue());
	} 
	
	@Test
	public void testSetOrdered() {
		elementCollection.setOrdered(false);
		assertTrue(!elementCollection.isOrdered());
	} 
	
	@Test
	public void testSetAllowDuplicates() {
		elementCollection.setAllowDuplicates(false);
		assertTrue(!elementCollection.isAllowDuplicates());
	} 
	
	@Test
	public void testSetElements() {
		String idShort = "testIdShort";
		Key key = new Key(KeyElements.BLOB, true, "TestValue", IdentifierType.IRI);
		Reference reference = new Reference(key);
		Formula formula = new Formula(Collections.singleton(reference));
		Qualifiable qualifiable = new Qualifiable(formula);
		ISubmodelElement element = new Property("testId1", new Referable(idShort, "Category", new LangStrings("DE", "Test")), REFERENCE, qualifiable);
		Map<String, ISubmodelElement> elementsMap = new HashMap<String, ISubmodelElement>();
		elementsMap.put(idShort, element);
		elementCollection.setElements(elementsMap);
		assertEquals(elementsMap, elementCollection.getElements());
	} 
	
	/**
	 * A dummy collection of submodel elements
	 * @return a list of submodel elements
	 */
	private Collection<ISubmodelElement> getSubmodelElements() {
		ISubmodelElement element1 = new Property("testId1");
		ISubmodelElement element2 = new Property("testId2");
		Collection<ISubmodelElement> elements = new ArrayList<ISubmodelElement>();
		elements.add(element1);
		elements.add(element2);
		return elements;
	}
}
