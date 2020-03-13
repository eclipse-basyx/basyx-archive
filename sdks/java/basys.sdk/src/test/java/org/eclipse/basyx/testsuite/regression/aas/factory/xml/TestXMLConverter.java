package org.eclipse.basyx.testsuite.regression.aas.factory.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.stream.StreamResult;

import org.eclipse.basyx.aas.factory.xml.MetamodelToXMLConverter;
import org.eclipse.basyx.aas.factory.xml.XMLToMetamodelConverter;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.api.parts.IConceptDictionary;
import org.eclipse.basyx.aas.metamodel.api.parts.IView;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.IAsset;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.parts.IConceptDescription;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyType;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.entity.EntityType;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.parts.ConceptDescription;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.Blob;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.File;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.MultiLanguageProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.Range;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.entity.Entity;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.event.BasicEvent;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.relationship.RelationshipElement;
import org.eclipse.basyx.testsuite.regression.vab.protocol.TypeDestroyer;
import org.eclipse.basyx.vab.model.VABModelMap;
import org.junit.Before;
import org.junit.Test;

public class TestXMLConverter {

	private String xmlInPath = "src/test/resources/aas/factory/xml/in.xml";
	
	private XMLToMetamodelConverter converter;
	
	@Before
	public void buildConverter() {
		try {
			String xml = new String(Files.readAllBytes(Paths.get(xmlInPath)));
			converter = new XMLToMetamodelConverter(xml);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testParseAAS() {
		try {
			checkAASs(converter.parseAAS());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testParseAssets() {
		try {
			checkAssets(converter.parseAssets());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testParseConceptDescriptions() {
		try {
			checkConceptDescriptions(converter.parseConceptDescriptions());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testParseSubmodels() {
		try {
			checkSubmodels(converter.parseSubmodels());			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testBuildXML() {
		try {
			//Convert the in.xml to Objects
			List<IAssetAdministrationShell> assetAdministrationShellList = converter.parseAAS();
			List<IAsset> assetList = converter.parseAssets();
			List<IConceptDescription> conceptDescriptionList = converter.parseConceptDescriptions();
			List<ISubModel> submodelList = converter.parseSubmodels();
			
			//Build XML-File from the Objects and write it to a StringWriter
			StringWriter resultWithTypes = new StringWriter();
			MetamodelToXMLConverter.convertToXML(assetAdministrationShellList, assetList, conceptDescriptionList, submodelList, new StreamResult(resultWithTypes));
			
			
			//Read the content of the StringWriter, convert it into Objects and check them
			XMLToMetamodelConverter converterWithTypes = new XMLToMetamodelConverter(resultWithTypes.toString());

			checkAASs(converterWithTypes.parseAAS());
			checkAssets(converterWithTypes.parseAssets());
			checkConceptDescriptions(converterWithTypes.parseConceptDescriptions());
			checkSubmodels(converterWithTypes.parseSubmodels());
			
			//erase the types of the Objects, that they are plain Maps as if they were transferred over the VAB
			List<IAssetAdministrationShell> iAssetAdministrationShellList = destroyAASTypes(assetAdministrationShellList);
			List<IAsset> iAssetList = destroyAssetTypes(assetList);
			List<IConceptDescription> iConceptDescriptionList = destroyConceptDescriptionTypes(conceptDescriptionList);
			List<ISubModel> iSubmodelList = destroySubmodelTypes(submodelList);
			
			//Build XML-File from the Objects and write it to a StringWriter
			StringWriter resultWithoutTypes = new StringWriter();
			MetamodelToXMLConverter.convertToXML(iAssetAdministrationShellList, iAssetList, iConceptDescriptionList, iSubmodelList, new StreamResult(resultWithoutTypes));
			
			
			//Read the content of the StringWriter, convert it into Objects and check them
			XMLToMetamodelConverter converterWithoutTypes = new XMLToMetamodelConverter(resultWithTypes.toString());
			
			checkAASs(converterWithoutTypes.parseAAS());
			checkAssets(converterWithoutTypes.parseAssets());
			checkConceptDescriptions(converterWithoutTypes.parseConceptDescriptions());
			checkSubmodels(converterWithoutTypes.parseSubmodels());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	private void checkAASs(List<IAssetAdministrationShell> aasList) {
		assertEquals(2, aasList.size());
		
		IAssetAdministrationShell aas = null;
		
		//select the AAS with a specific ID form the list
		for(IAssetAdministrationShell shell: aasList) {
			if(shell.getIdShort().equals("asset_admin_shell")) {
				aas = shell;
				break;
			}
		}
		
		assertNotNull(aas);
		
		assertEquals("asset_admin_shell", aas.getIdShort());
		assertEquals("test_category", aas.getCategory());
		assertEquals("aas_parent_id", aas.getParent().getKeys().get(0).getValue());
		
		assertEquals("aas_Description", aas.getDescription().get("EN"));
		assertEquals("Beschreibung Verwaltungsschale", aas.getDescription().get("DE"));
		
		assertEquals("www.admin-shell.io/aas-sample/1/0", aas.getIdentification().getId());
		assertEquals(IdentifierType.IRI, aas.getIdentification().getIdType());
		
		assertEquals("1", aas.getAdministration().getVersion());
		assertEquals("0", aas.getAdministration().getRevision());
		
		Set<IConceptDictionary> conceptDictionary = aas.getConceptDictionary();
		for (IConceptDictionary iConceptDictionary : conceptDictionary) {
			assertEquals("SampleDic", iConceptDictionary.getIdShort());
			Set<IReference> conceptDescription = iConceptDictionary.getConceptDescription();
			List<IKey> keys = conceptDescription.iterator().next().getKeys();
			assertEquals(1, keys.size());

			// Test key
			IKey key = keys.get(0);
			assertEquals(KeyElements.CONCEPTDESCRIPTION, key.getType());
			assertEquals(KeyType.IRDI, key.getIdType());
			assertEquals("0173-1#02-BAA120#007", key.getValue());
			assertEquals(true, key.isLocal());
		}

		// Test submodel reference retrieval
		
		// Select submodel reference's key
		IKey submodelKey = null;
		for (IReference ref : aas.getSubmodelReferences()) {
			for (IKey k : ref.getKeys())
				if (k.getValue().equals("http://www.zvei.de/demo/submodel/12345679")) {
					submodelKey = k;
					break;
				}
		}

		assertNotNull(submodelKey);

		// Equality of value is already guaranteed by selection criteria
		assertEquals(KeyType.IRI, submodelKey.getIdType());
		assertEquals(KeyElements.SUBMODEL, submodelKey.getType());
		assertEquals(true, submodelKey.isLocal());
		
		// Test view retrieval
		Object[] views = aas.getViews().toArray();
		assertEquals(2, views.length);
		IView iView = (IView) views[0];
		if(!iView.getIdShort().equals("SampleView"))
			iView = (IView) views[1];
		assertEquals("SampleView", iView.getIdShort());
		Set<IReference> containedElement = iView.getContainedElement();
		IReference ref = containedElement.iterator().next();

		// Text keys
		IKey key0 = ref.getKeys().get(0);
		assertEquals(KeyElements.SUBMODEL, key0.getType());
		assertEquals(KeyType.IRI, key0.getIdType());
		assertEquals("\"http://www.zvei.de/demo/submodel/12345679\"", key0.getValue());
		assertEquals(true, key0.isLocal());

		IKey key1 = ref.getKeys().get(1);
		assertEquals(KeyElements.PROPERTY, key1.getType());
		assertEquals(KeyType.IDSHORT, key1.getIdType());
		assertEquals("rotationSpeed", key1.getValue());
		assertEquals(true, key1.isLocal());
	}
	
	private void checkAssets(List<IAsset> assets) {
		assertEquals(2, assets.size());
		IAsset asset = null;
		
		//select the Asset with a specific ID form the list
		for(IAsset a: assets) {
			if(a.getIdShort().equals("3s7plfdrs35_asset1")) {
				asset = a;
				break;
			}
		}
		
		assertNotNull(asset);
		
		assertEquals("3s7plfdrs35_asset1", asset.getIdShort());
		assertEquals("asset1_Description", asset.getDescription().get("EN"));
		assertEquals(IdentifierType.IRI, asset.getIdentification().getIdType());
		assertEquals("Instance", asset.getAssetKind().toString());
		assertEquals("www.festo.com/dic/08111234", asset.getAssetIdentificationModel().getKeys().get(0).getValue());
	}
	
	private void checkConceptDescriptions(List<IConceptDescription> conceptDescriptions) {
		assertEquals(2, conceptDescriptions.size());
		IConceptDescription conceptDescription = conceptDescriptions.get(0);
		
		//make sure to select the correct ConceptDescription1 from the list
		//as there is no order given by the XML file
		if(!conceptDescription.getIdShort().equals("conceptDescription1"))
			conceptDescription = conceptDescriptions.get(1);
		
		assertEquals("conceptDescription1", conceptDescription.getIdShort());
		assertEquals("conceptDescription_Description", conceptDescription.getDescription().get("EN"));
		assertEquals("www.festo.com/dic/08111234", conceptDescription.getIdentification().getId());
		Set<IReference> refs = conceptDescription.getIsCaseOf();
		assertEquals(1, refs.size());
	}
	
	private void checkSubmodels(List<ISubModel> submodels) {
		assertEquals(2, submodels.size());
		ISubModel submodel = null;
		
		//select the SubModel with a specific ID form the list
		for(ISubModel s: submodels) {
			if(s.getIdShort().equals("3s7plfdrs35_submodel1")) {
				submodel = s;
				break;
			}
		}
		
		assertNotNull(submodel);
		
		assertEquals("3s7plfdrs35_submodel1", submodel.getIdShort());
		Set<IConstraint> constraints = submodel.getQualifier();
		assertEquals(2, constraints.size());
		checkSubmodelElements(submodel);
	}
	
	@SuppressWarnings("unchecked")
	private void checkSubmodelElements(ISubModel submodel) {
		Map<String, ISubmodelElement> submodelElements = (Map<String, ISubmodelElement>)
				((Map<String, Object>)submodel).get(SubModel.SUBMODELELEMENT);
		assertEquals(12, submodelElements.size());
		
		ISubmodelElement element = submodelElements.get("rotationSpeed");
		assertTrue(element instanceof Property);
		Property property = (Property) element;
		assertEquals("2000", property.get());
		assertEquals("double", property.getValueType());
		assertEquals("rotationSpeed", property.getIdShort());
		
		element = submodelElements.get("emptyDouble");
		assertTrue(element instanceof Property);
		property = (Property) element;
		assertEquals("double", property.getValueType());
		
		element = submodelElements.get("basic_event_id");
		assertTrue(element instanceof BasicEvent);
		BasicEvent basicEvent = (BasicEvent) element;
		List<IKey> keys = basicEvent.getObserved().getKeys();
		assertEquals(1, keys.size());
		assertEquals("http://www.zvei.de/demo/submodelDefinitions/87654346", keys.get(0).getValue());
		
		element = submodelElements.get("entity_id");
		assertTrue(element instanceof Entity);
		Entity entity = (Entity) element;
		assertTrue(entity.getEntityType().equals(EntityType.COMANAGEDENTITY));
		List<ISubmodelElement> statements = entity.getStatements();
		assertEquals(2, statements.size());
		assertTrue((statements.get(0) instanceof File) || (statements.get(1) instanceof File));
		assertTrue((statements.get(0) instanceof Range) || (statements.get(1) instanceof Range));
		
		element = submodelElements.get("multi_language_property_id");
		assertTrue(element instanceof MultiLanguageProperty);
		MultiLanguageProperty mLProperty = (MultiLanguageProperty) element;
		keys = mLProperty.getValueId().getKeys();
		assertEquals(1, keys.size());
		assertEquals("0173-1#05-AAA650#002", keys.get(0).getValue());
		LangStrings langStrings = mLProperty.getValue();
		assertEquals(2, langStrings.size());
		assertEquals("Eine Beschreibung auf deutsch", langStrings.get("de"));
		assertEquals("A description in english", langStrings.get("en"));
		
		element = submodelElements.get("range_id");
		assertTrue(element instanceof Range);
		Range range = (Range) element;
		assertEquals("int", range.getValueType());
		assertEquals("1", range.getMin());
		assertEquals("10", range.getMax());
		
		element = submodelElements.get("file_id");
		assertTrue(element instanceof File);
		File file = (File) element;
		assertEquals("file_mimetype", file.getMimeType());
		assertEquals("file_value", file.getValue());
		
		element = submodelElements.get("blob_id");
		assertTrue(element instanceof Blob);
		Blob blob = (Blob) element;
		assertEquals("blob_mimetype", blob.getMimeType());
		assertEquals("YmxvYit2YWx1ZQ==", Base64.getEncoder().encodeToString(blob.getValue()));
		
		element = submodelElements.get("reference_ELE_ID");
		assertTrue(element instanceof ReferenceElement);
		ReferenceElement refElem = (ReferenceElement) element;
		keys = refElem.getValue().getKeys();
		assertEquals(1, keys.size());
		assertEquals("0173-1#05-AAA650#002", keys.get(0).getValue());
		
		element = submodelElements.get("submodelElementCollection_ID");
		assertTrue(element instanceof SubmodelElementCollection);
		SubmodelElementCollection smCollection = (SubmodelElementCollection) element;
		Collection<ISubmodelElement> elements = smCollection.getValue();
		assertEquals(2, elements.size());
		assertTrue(smCollection.isAllowDuplicates());
		assertFalse(smCollection.isOrdered());
		
		element = submodelElements.get("relationshipElement_ID");
		assertTrue(element instanceof RelationshipElement);
		RelationshipElement relElem = (RelationshipElement) element;
		keys = relElem.getFirst().getKeys();
		assertEquals(1, keys.size());
		assertEquals("0173-1#05-AAA650#001", keys.get(0).getValue());
		keys = relElem.getSecond().getKeys();
		assertEquals(1, keys.size());
		assertEquals("0173-1#05-AAA650#002", keys.get(0).getValue());
		
		element = submodelElements.get("operation_ID");
		assertTrue(element instanceof Operation);
		Operation op = (Operation) element;
		List<IOperationVariable> parameters = op.getInputVariables();
		assertEquals(2, parameters.size());
		List<IOperationVariable> returns = op.getOutputVariables();
		assertEquals(1, returns.size());
		Object o = returns.get(0).getValue();
		assertTrue(o instanceof ReferenceElement);
		List<IOperationVariable> inout = op.getInOutputVariables();
		assertEquals(1, inout.size());
		o = inout.get(0).getValue();
		assertTrue(o instanceof ReferenceElement);
	}
	
	@SuppressWarnings("unchecked")
	private List<IAssetAdministrationShell> destroyAASTypes(List<IAssetAdministrationShell> aasList) {
		List<IAssetAdministrationShell> ret = new ArrayList<>();
		for(IAssetAdministrationShell aas: aasList) {
			ret.add(AssetAdministrationShell.createAsFacade(TypeDestroyer.destroyType((Map<String, Object>) aas)));
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	private List<ISubModel> destroySubmodelTypes(List<ISubModel> submodelList) {
		List<ISubModel> ret = new ArrayList<>();
		for(ISubModel submodel: submodelList) {
			ret.add(SubModel.createAsFacade(new VABModelMap<>(TypeDestroyer.destroyType((Map<String, Object>) submodel))));
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	private List<IAsset> destroyAssetTypes(List<IAsset> assetList) {
		List<IAsset> ret = new ArrayList<>();
		for(IAsset asset: assetList) {
			ret.add(Asset.createAsFacade(TypeDestroyer.destroyType((Map<String, Object>) asset)));

		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	private List<IConceptDescription> destroyConceptDescriptionTypes(List<IConceptDescription> cdList) {
		List<IConceptDescription> ret = new ArrayList<>();
		for(IConceptDescription cd: cdList) {
			ret.add(ConceptDescription.createAsFacade(TypeDestroyer.destroyType((Map<String, Object>) cd)));
		}
		return ret;
	}
	
}
