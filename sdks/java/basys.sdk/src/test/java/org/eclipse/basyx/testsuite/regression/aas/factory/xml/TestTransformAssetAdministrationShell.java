package org.eclipse.basyx.testsuite.regression.aas.factory.xml;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.aas.factory.xml.TransformAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.api.parts.IConceptDictionary;
import org.eclipse.basyx.aas.metamodel.api.parts.IView;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.vab.factory.xml.XmlParser;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Test case to test TransformAssetAdministrationShell class
 * 
 * @author rajashek
 *
 */
public class TestTransformAssetAdministrationShell {
	private Map<String, Object> rootObj = new HashMap<>();

	
	static final String xmlTestContent = "<aas:assetAdministrationShells>\r\n" + 
			"        <aas:assetAdministrationShell>\r\n" + 
			"            <aas:identification idType=\"URI\">www.admin-shell.io/aas-sample/1/0</aas:identification>\r\n" + 
			"            <aas:administration>\r\n" + 
			"                <aas:version>1</aas:version>\r\n" + 
			"                <aas:revision>0</aas:revision>\r\n" + 
			"            </aas:administration>\r\n" + 
			"            <aas:assetRef>\r\n" +
			"                <aas:keys>\r\n" + 
			"                    <aas:key type=\"Asset\" local=\"false\" idType=\"URI\">http://pk.festo.com/3s7plfdrs35</aas:key>\r\n" + 
			"                </aas:keys>\r\n" + 
			"            </aas:assetRef>\r\n" + 
			"            <aas:submodelRefs>\r\n" + 
			"                <aas:submodelRef>\r\n" + 
			"                    <aas:keys>\r\n" + 
			"                        <aas:key type=\"Submodel\" local=\"true\" idType=\"URI\">\"http://www.zvei.de/demo/submodel/12345679\"</aas:key>\r\n" + 
			"                    </aas:keys>\r\n" + 
			"                </aas:submodelRef>\r\n" + 
			"            </aas:submodelRefs>\r\n" + 
			"            <aas:views>\r\n" + 
			"                <aas:view>\r\n" + 
			"                    <aas:idShort>SampleView</aas:idShort>\r\n" + 
			"                    <aas:containedElements>\r\n" + 
			"                        <aas:containedElementRef>\r\n" + 
			"                            <aas:keys>\r\n" + 
			"                                <aas:key type=\"Submodel\" local=\"true\" idType=\"URI\">\"http://www.zvei.de/demo/submodel/12345679\"</aas:key>\r\n" + 
			"                                <aas:key type=\"Property\" local=\"true\" idType=\"idShort\">rotationSpeed</aas:key>\r\n" + 
			"                            </aas:keys>\r\n" + 
			"                        </aas:containedElementRef>\r\n" + 
			"                    </aas:containedElements>\r\n" + 
			"                </aas:view>\r\n" + 
			"            </aas:views>\r\n" + 
			"            <aas:conceptDictionaries>\r\n" + 
			"                <aas:conceptDictionary>\r\n" + 
			"                    <aas:idShort>SampleDic</aas:idShort>\r\n" + 
			"                    <aas:conceptDescriptionRefs>\r\n" + 
			"                        <aas:conceptDescriptionRef>\r\n" + 
			"                            <aas:keys>\r\n" + 
			"                                <aas:key type=\"ConceptDescription\" local=\"true\" idType=\"IRDI\">0173-1#02-BAA120#007</aas:key>\r\n" + 
			"                            </aas:keys>\r\n" + 
			"                        </aas:conceptDescriptionRef>\r\n" + 
			"                    </aas:conceptDescriptionRefs>\r\n" + 
			"                </aas:conceptDictionary>\r\n" + 
			"            </aas:conceptDictionaries>\r\n" + 
			"        </aas:assetAdministrationShell>\r\n" + 
			"    </aas:assetAdministrationShells>";
	@Before
	public void TestBuildXmlMap() throws Exception {
		rootObj.putAll(XmlParser.buildXmlMap(xmlTestContent));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testAssetAdminshellObject() throws ParserConfigurationException, SAXException, IOException {
		ArrayList<AssetAdministrationShell> assetSet = new ArrayList<AssetAdministrationShell>();
		// get the Asset admin shell info from root and start processing it one by one
		Object administrationShellObj = ((Map<String, Object>) rootObj.get("aas:assetAdministrationShells")).get("aas:assetAdministrationShell");

		if (administrationShellObj instanceof Collection<?>) {
			ArrayList<Object> administrationShellArrayList = (ArrayList<Object>) administrationShellObj;
			for (Object object : administrationShellArrayList) {
				assetSet.add(TransformAssetAdministrationShell.transformAssetAdministrationShell((Map<String, Object>) object));
			}
		} else {
			Map<String, Object> administrationShellMapObj = (Map<String, Object>) administrationShellObj;
			assetSet.add(TransformAssetAdministrationShell.transformAssetAdministrationShell(administrationShellMapObj));
		}

		assertEquals(assetSet.size(), 1);
		assertEquals(assetSet.get(0).getAdministration().getVersion(), "1");
		assertEquals(assetSet.get(0).getAdministration().getRevision(), "0");
		assertEquals(assetSet.get(0).getIdentification().getId(), "www.admin-shell.io/aas-sample/1/0");
		assertEquals(assetSet.get(0).getIdentification().getIdType(), "URI");
		Set<IConceptDictionary> conceptDictionary = assetSet.get(0).getConceptDictionary();
		for (IConceptDictionary iConceptDictionary : conceptDictionary) {
			assertEquals(iConceptDictionary.getIdShort(), "SampleDic");
			Set<IReference> conceptDescription = iConceptDictionary.getConceptDescription();
			List<IKey> keys = conceptDescription.iterator().next().getKeys();
			assertEquals(1, keys.size());

			// Test key
			IKey key = keys.get(0);
			assertEquals("ConceptDescription", key.getType());
			assertEquals("IRDI", key.getidType());
			assertEquals("0173-1#02-BAA120#007", key.getValue());
			assertEquals(true, key.isLocal());
		}

		// Test view retrieval
		Set<IView> views = assetSet.get(0).getViews();
		IView iView = views.iterator().next();
		assertEquals(iView.getIdShort(), "SampleView");
		Set<IReference> containedElement = iView.getContainedElement();
		IReference ref = containedElement.iterator().next();

		// Text keys
		IKey key0 = ref.getKeys().get(0);
		assertEquals("Submodel", key0.getType());
		assertEquals("URI", key0.getidType());
		assertEquals("\"http://www.zvei.de/demo/submodel/12345679\"", key0.getValue());
		assertEquals(true, key0.isLocal());

		IKey key1 = ref.getKeys().get(1);
		assertEquals("Property", key1.getType());
		assertEquals("idShort", key1.getidType());
		assertEquals("rotationSpeed", key1.getValue());
		assertEquals(true, key1.isLocal());
	}
}
