package org.eclipse.basyx.testsuite.regression.submodel.types.digitalnameplate.submodelelementcollections.markings;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.exception.MetamodelConstructionException;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.File;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.types.digitalnameplate.submodelelementcollections.markings.Marking;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests createAsFacade and isValid of {@link Marking} for their
 * correctness
 * 
 * @author haque
 *
 */
public class TestMarking {
	public static final String IDSHORT = "Marking01";
	public static Property markingName = new Property(Marking.MARKINGNAMEID, PropertyValueTypeDef.String);
	public static File markingFile = new File("/to/the/image.jpg", "image/jpg");
	public static Property additText = new Property(Marking.MARKINGADDITIONALTEXTPREFIX + "01", PropertyValueTypeDef.String);
	
	private Map<String, Object> markingMap = new HashMap<String, Object>();
	
	@Before
	public void buildFax() {
		markingFile.setIdShort(Marking.MARKINGFILEID);
		markingName.setValue("0173-1#07-DAA603#004");
		additText.setValue("text additional");
		
		List<ISubmodelElement> elements = new ArrayList<ISubmodelElement>();
		elements.add(markingFile);
		elements.add(markingName);
		elements.add(additText);
		
		markingMap.put(Referable.IDSHORT, IDSHORT);
		markingMap.put(HasSemantics.SEMANTICID, Marking.SEMANTICID);
		markingMap.put(Property.VALUE, elements);
	}
	
	@Test
	public void testCreateAsFacade() {
		Marking markingFromMap = Marking.createAsFacade(markingMap);
		assertEquals(Marking.SEMANTICID, markingFromMap.getSemanticId());
		assertEquals(markingFile, markingFromMap.getMarkingFile());
		assertEquals(markingName, markingFromMap.getMarkingName());
		assertEquals(Collections.singletonList(additText), markingFromMap.getMarkingAdditionalText());
		assertEquals(IDSHORT, markingFromMap.getIdShort());
	}
	
	@Test (expected = MetamodelConstructionException.class)
	public void testCreateAsFacadeExceptionIdShort() {
		markingMap.remove(Referable.IDSHORT);
		Marking.createAsFacade(markingMap);
	}
	
	@SuppressWarnings("unchecked")
	@Test (expected = ResourceNotFoundException.class)
	public void testCreateAsFacadeExceptionMarkingName() {
		List<ISubmodelElement> elements = (List<ISubmodelElement>)markingMap.get(Property.VALUE);
		elements.remove(markingName);
		Marking.createAsFacade(markingMap);
	}

	@SuppressWarnings("unchecked")
	@Test (expected = ResourceNotFoundException.class)
	public void testCreateAsFacadeExceptionMarkingFile() {
		List<ISubmodelElement> elements = (List<ISubmodelElement>)markingMap.get(Property.VALUE);
		elements.remove(markingFile);
		Marking.createAsFacade(markingMap);
	}
}
