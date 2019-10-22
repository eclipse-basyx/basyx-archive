package org.eclipse.basyx.testsuite.regression.aas.factory.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.aas.factory.xml.TransformConceptDescription;
import org.eclipse.basyx.aas.metamodel.map.parts.ConceptDescription;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.vab.factory.xml.XmlParser;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
/**
 * Test case to test TransformConceptDescription class
 * 
 * @author rajashek
 *
 */
public class TestTransformConceptDescription {
	private Map<String, Object> rootObj = new HashMap<>();

	
	static final String xmlTestContent = "     <aas:conceptDescriptions>\r\n" + 
			"        <aas:conceptDescription>\r\n" + 
			"            <aas:identification idType=\"URI\">www.festo.com/dic/08111234</aas:identification>\r\n" + 
			"            <aas:embeddedDataSpecification>\r\n" + 
			"                <aas:hasDataSpecification>\r\n" + 
			"                    <aas:keys>\r\n" + 
			"                        <aas:key idType=\"URI\" local=\"false\" type=\"GlobalReference\">www.admin-shell.io/DataSpecificationTemplates/DataSpecificationIEC61360</aas:key>\r\n" + 
			"                    </aas:keys>\r\n" + 
			"                </aas:hasDataSpecification>\r\n" + 
			"                <aas:dataSpecificationContent>\r\n" + 
			"                    <aas:dataSpecificationIEC61360>\r\n" + 
			"                        <IEC:preferredName>\r\n" + 
			"                            <aas:langString lang=\"DE\">Drehzahl</aas:langString>\r\n" + 
			"                            <aas:langString lang=\"EN\">Rotation Speed</aas:langString>\r\n" + 
			"                        </IEC:preferredName>\r\n" + 
			"                        <IEC:shortName>N</IEC:shortName>\r\n" + 
			"                        <IEC:unitId>\r\n" + 
			"                            <aas:keys>\r\n" + 
			"                                <aas:key local=\"false\" type=\"GlobalReference\" idType=\"IRDI\">0173-1#05-AAA650#002</aas:key>\r\n" + 
			"                            </aas:keys>\r\n" + 
			"                        </IEC:unitId>\r\n" + 
			"                        <IEC:valueFormat>NR1..5</IEC:valueFormat>\r\n" + 
			"                    </aas:dataSpecificationIEC61360>\r\n" + 
			"                </aas:dataSpecificationContent>\r\n" + 
			"            </aas:embeddedDataSpecification>\r\n" + 
			"        </aas:conceptDescription>\r\n" + 
			"        <aas:conceptDescription>\r\n" + 
			"            <aas:identification idType=\"IRDI\">0173-1#02-BAA120#007</aas:identification>\r\n" + 
			"            <aas:embeddedDataSpecification>\r\n" + 
			"                <aas:hasDataSpecification>\r\n" + 
			"                    <aas:keys>\r\n" + 
			"                        <aas:key idType=\"URI\" type=\"GlobalReference\" local=\"false\">www.admin-shell.io/DataSpecificationTemplates/DataSpecificationIEC61360</aas:key>\r\n" + 
			"                    </aas:keys>\r\n" + 
			"                </aas:hasDataSpecification>\r\n" + 
			"                <aas:dataSpecificationContent>\r\n" + 
			"                    <aas:dataSpecificationIEC61360>\r\n" + 
			"                        <IEC:preferredName>\r\n" + 
			"                            <aas:langString lang=\"DE\">maximale Drehzahl</aas:langString>\r\n" + 
			"                            <aas:langString lang=\"EN\">max rotation speed</aas:langString>\r\n" + 
			"                        </IEC:preferredName>\r\n" + 
			"                        <IEC:shortName>NMax</IEC:shortName>\r\n" + 
			"                        <IEC:unitId>\r\n" + 
			"                            <aas:keys>\r\n" + 
			"                                <aas:key type=\"GlobalReference\" idType=\"IRDI\" local=\"false\">0173-1#05-AAA650#002</aas:key>\r\n" + 
			"                            </aas:keys>\r\n" + 
			"                        </IEC:unitId>\r\n" + 
			"                        <IEC:valueFormat>NR1..5</IEC:valueFormat>\r\n" + 
			"                    </aas:dataSpecificationIEC61360>\r\n" + 
			"                </aas:dataSpecificationContent>\r\n" + 
			"            </aas:embeddedDataSpecification>\r\n" + 
			"        </aas:conceptDescription>\r\n" + 
			"    </aas:conceptDescriptions>";
	@Before
	public void TestBuildXmlMap() throws Exception {
		rootObj.putAll(XmlParser.buildXmlMap(xmlTestContent));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testConceptDescription() throws ParserConfigurationException, SAXException, IOException {
		Object SubModelObj = ((Map<String, Object>)rootObj.get("aas:conceptDescriptions")).get("aas:conceptDescription");
		ArrayList<Object> conceptDescriptionArrayList=(ArrayList<Object>)SubModelObj;
		assertEquals(conceptDescriptionArrayList.size(), 2);
		ConceptDescription transformConceptDescription = TransformConceptDescription.transformConceptDescription((Map<String, Object>) conceptDescriptionArrayList.get(0));
		assertEquals(transformConceptDescription.getIdentification().getIdType(),"URI");
		Set<IReference> dataSpecificationReferences = transformConceptDescription.getDataSpecificationReferences();
		for (IReference iReference : dataSpecificationReferences) {
			assertNotNull(iReference.getKeys());
		}
	}

}
