package org.eclipse.basyx.testsuite.regression.submodel.types.digitalnameplate.submodelelementcollections;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.exception.MetamodelConstructionException;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangString;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.MultiLanguageProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.types.digitalnameplate.submodelelementcollections.address.Address;
import org.eclipse.basyx.submodel.types.digitalnameplate.submodelelementcollections.address.Fax;
import org.eclipse.basyx.submodel.types.digitalnameplate.submodelelementcollections.assetspecificproperties.AssetSpecificProperties;
import org.eclipse.basyx.submodel.types.digitalnameplate.submodelelementcollections.markings.Marking;
import org.eclipse.basyx.submodel.types.digitalnameplate.submodelelementcollections.markings.Markings;
import org.eclipse.basyx.submodel.types.digitalnameplate.DigitalNameplateSubmodel;
import org.eclipse.basyx.testsuite.regression.submodel.types.digitalnameplate.submodelelementcollections.address.TestAddress;
import org.eclipse.basyx.testsuite.regression.submodel.types.digitalnameplate.submodelelementcollections.assetspecificproperties.TestAssetSpecificProperties;
import org.eclipse.basyx.testsuite.regression.submodel.types.digitalnameplate.submodelelementcollections.markings.TestMarking;
import org.eclipse.basyx.testsuite.regression.submodel.types.digitalnameplate.submodelelementcollections.markings.TestMarkings;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests createAsFacade and isValid of {@link DigitalNameplateSubmodel} for their
 * correctness
 * 
 * @author haque
 *
 */
public class TestDigitalNameplateSubmodel {
	public static MultiLanguageProperty manufacturerName = new MultiLanguageProperty(DigitalNameplateSubmodel.MANUFACTURERNAMEID);
	public static MultiLanguageProperty designation = new MultiLanguageProperty(DigitalNameplateSubmodel.MANUFACTURERPRODUCTDESIGNATIONID);
	public static Address address = new Address(TestAddress.street, TestAddress.zipCode, TestAddress.cityTown, TestAddress.nationalCode);
	public static MultiLanguageProperty productFamily = new MultiLanguageProperty(DigitalNameplateSubmodel.MANUFACTURERPRODUCTFAMILYID);
	public static Property serialNumber = new Property(DigitalNameplateSubmodel.SERIALNUMBERID, PropertyValueTypeDef.String);
	public static Property yearsOfConstruction = new Property(DigitalNameplateSubmodel.YEARSOFCONSTRUCTIONID, PropertyValueTypeDef.String);
	public static Markings markings;
	public static AssetSpecificProperties assetSpecificProperties = new AssetSpecificProperties(Collections.singletonList(TestAssetSpecificProperties.guidelineSpecificProperties));
	
	private Map<String, Object> submodelMap = new HashMap<String, Object>();
	
	@Before
	public void buildFax() {
		manufacturerName.setValue(new LangStrings(new LangString("DE", "Test Manufacturer")));
		designation.setValue(new LangStrings(new LangString("DE", "Test Designation")));
		productFamily.setValue(new LangStrings(new LangString("DE", "Test Product Family")));
		serialNumber.setValue("123456");
		yearsOfConstruction.setValue("2020");
		
		TestMarking.markingFile.setIdShort(Marking.MARKINGFILEID);
		TestMarking.markingName.setValue("0173-1#07-DAA603#004");
		TestMarkings.marking = new Marking(TestMarking.IDSHORT, TestMarking.markingName, TestMarking.markingFile);
		TestMarkings.marking.setParent(new Reference(new Key(KeyElements.SUBMODELELEMENTCOLLECTION, true, Markings.IDSHORT, IdentifierType.IRDI)));
		TestMarkings.markings = new ArrayList<Marking>();
		TestMarkings.markings.add(TestMarkings.marking);
		markings = new Markings(TestMarkings.markings);
		
		TestAddress.street.setValue(new LangStrings(new LangString("DE", "musterstraße 1")));
		TestAddress.zipCode.setValue(new LangStrings(new LangString("DE", "12345")));
		TestAddress.cityTown.setValue(new LangStrings(new LangString("DE", "MusterStadt")));
		TestAddress.nationalCode.setValue(new LangStrings(new LangString("DE", "DE")));
		
		List<ISubmodelElement> elements = new ArrayList<ISubmodelElement>();
		elements.add(manufacturerName);
		elements.add(designation);
		elements.add(productFamily);
		elements.add(serialNumber);
		elements.add(yearsOfConstruction);
		elements.add(markings);
		elements.add(address);
		elements.add(assetSpecificProperties);
		submodelMap.put(Referable.IDSHORT, DigitalNameplateSubmodel.SUBMODELID);
		submodelMap.put(HasSemantics.SEMANTICID, DigitalNameplateSubmodel.SEMANTICID);
		submodelMap.put(SubModel.SUBMODELELEMENT, elements);
	}

	@Test
	public void testCreateAsFacade() {
		DigitalNameplateSubmodel submodelFromMap = DigitalNameplateSubmodel.createAsFacade(submodelMap);
		assertEquals(DigitalNameplateSubmodel.SEMANTICID, submodelFromMap.getSemanticId());
		assertEquals(manufacturerName, submodelFromMap.getManufacturerName());
		assertEquals(designation, submodelFromMap.getManufacturerProductDesignation());
		assertEquals(address, submodelFromMap.getAddress());
		assertEquals(productFamily, submodelFromMap.getManufacturerProductFamily());
		assertEquals(serialNumber, submodelFromMap.getSerialNumber());
		assertEquals(yearsOfConstruction, submodelFromMap.getYearOfConstruction());
		assertEquals(markings, submodelFromMap.getMarkings());
		assertEquals(assetSpecificProperties, submodelFromMap.getAssetSpecificProperties());
		assertEquals(DigitalNameplateSubmodel.SUBMODELID, submodelFromMap.getIdShort());
	}
	
	@Test (expected = MetamodelConstructionException.class)
	public void testCreateAsFacadeExceptionIdShort() {
		submodelMap.remove(Referable.IDSHORT);
		Fax.createAsFacade(submodelMap);
	}
	
	@SuppressWarnings("unchecked")
	@Test (expected = ResourceNotFoundException.class)
	public void testCreateAsFacadeExceptionManufacturerName() {
		List<ISubmodelElement> elements = (List<ISubmodelElement>)submodelMap.get(SubModel.SUBMODELELEMENT);
		elements.remove(manufacturerName);
		DigitalNameplateSubmodel.createAsFacade(submodelMap);
	}
	
	@SuppressWarnings("unchecked")
	@Test (expected = ResourceNotFoundException.class)
	public void testCreateAsFacadeExceptionZearsOfConstruction() {
		List<ISubmodelElement> elements = (List<ISubmodelElement>)submodelMap.get(SubModel.SUBMODELELEMENT);
		elements.remove(yearsOfConstruction);
		DigitalNameplateSubmodel.createAsFacade(submodelMap);
	}
	
	@SuppressWarnings("unchecked")
	@Test (expected = ResourceNotFoundException.class)
	public void testCreateAsFacadeExceptionManufacturerProductDesignation() {
		List<ISubmodelElement> elements = (List<ISubmodelElement>)submodelMap.get(SubModel.SUBMODELELEMENT);
		elements.remove(designation);
		DigitalNameplateSubmodel.createAsFacade(submodelMap);
	}
	
	@SuppressWarnings("unchecked")
	@Test (expected = ResourceNotFoundException.class)
	public void testCreateAsFacadeExceptionAddress() {
		List<ISubmodelElement> elements = (List<ISubmodelElement>)submodelMap.get(SubModel.SUBMODELELEMENT);
		elements.remove(address);
		DigitalNameplateSubmodel.createAsFacade(submodelMap);
	}
	
	@SuppressWarnings("unchecked")
	@Test (expected = ResourceNotFoundException.class)
	public void testCreateAsFacadeExceptionManufacturerProductFamily() {
		List<ISubmodelElement> elements = (List<ISubmodelElement>)submodelMap.get(SubModel.SUBMODELELEMENT);
		elements.remove(productFamily);
		DigitalNameplateSubmodel.createAsFacade(submodelMap);
	}
}
