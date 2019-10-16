package org.eclipse.basyx.testsuite.regression.submodel.factory.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.submodel.factory.xml.TransformSubmodel;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.vab.factory.xml.XmlParser;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
/**
 * Test case to test  TransformSubModel class
 * 
 * @author rajashek
 *
 */
public class TestTransformSubModel {
	private Map<String, Object> rootObj = new HashMap<>();


	static final String xmlTestContent = "  <aas:submodels>\r\n" + 
			"        <aas:submodel>\r\n" + 
			"            <aas:identification idType=\"URI\">http://www.zvei.de/demo/submodel/12345679</aas:identification>\r\n" + 
			"            <aas:semanticId >\r\n" + 
			"                <aas:keys>\r\n" + 
			"                    <aas:key idType=\"URI\" local=\"false\" type=\"Submodel\">http://www.zvei.de/demo/submodelDefinitions/87654346</aas:key>\r\n" + 
			"                </aas:keys>\r\n" + 
			"            </aas:semanticId>\r\n" + 
			"            <aas:kind>Instance</aas:kind>\r\n" + 
			"            <aas:submodelElements>\r\n" + 
			"                <aas:submodelElement>\r\n" + 
			"                    <aas:property>\r\n" + 
			"                        <aas:idShort>rotationSpeed</aas:idShort>\r\n" + 
			"                        <aas:category>VARIABLE</aas:category>\r\n" + 
			"                        <aas:semanticId>\r\n" + 
			"                            <aas:keys>\r\n" + 
			"                                <aas:key idType=\"URI\" type=\"ConceptDescription\" local=\"true\">www.festo.com/dic/08111234</aas:key>\r\n" + 
			"                            </aas:keys>\r\n" + 
			"                        </aas:semanticId>\r\n" + 
			"                        <aas:valueType>double</aas:valueType>\r\n" + 
			"                    </aas:property>\r\n" + 
			"                </aas:submodelElement>\r\n" + 
			"                <aas:submodelElement>\r\n" + 
			"                    <aas:property>\r\n" + 
			"                        <aas:idShort>NMAX</aas:idShort>\r\n" + 
			"                        <aas:category>PARAMETER</aas:category>\r\n" + 
			"                        <aas:semanticId>\r\n" + 
			"                            <aas:keys>\r\n" + 
			"                                <aas:key idType=\"IRDI\" type=\"GlobalReference\" local=\"true\">0173-1#02-BAA120#007</aas:key>\r\n" + 
			"                            </aas:keys>\r\n" + 
			"                        </aas:semanticId>\r\n" + 
			"                        <aas:valueType>double</aas:valueType>\r\n" + 
			"                        <aas:value>2000</aas:value>\r\n" + 
			"                    </aas:property>\r\n" + 
			"                </aas:submodelElement>\r\n" + 
			"            </aas:submodelElements>\r\n" + 
			"        </aas:submodel>\r\n" + 
			"    </aas:submodels>";
	@Before
	public void TestBuildXmlMap() throws Exception {
		rootObj.putAll(XmlParser.buildXmlMap(xmlTestContent));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSuModel() throws ParserConfigurationException, SAXException, IOException {
		HashSet<SubModel> submodelSet=new HashSet<SubModel>();
		Object SubModelObj = ((Map<String, Object>)rootObj.get("aas:submodels")).get("aas:submodel");
		Map<String, Object>  subModelMapObj = (Map<String, Object>) SubModelObj;
		submodelSet.add(TransformSubmodel.transformSubmodel(subModelMapObj));
		ArrayList<SubModel> list = new ArrayList<SubModel>(submodelSet);
		assertEquals(submodelSet.size(), 1);
		assertEquals(list.get(0).getIdentification().getIdType(),"URI");
		assertEquals((String)list.get(0).get("kind"),"Instance");
		//assertEquals(list.get(0).get("semanticId"),"{keys=[{idType=URI, type=Submodel, value=http://www.zvei.de/demo/submodelDefinitions/87654346, local=false}]}");
		assertTrue(((Map<String, Object>)list.get(0).get("dataElements")).get("NMAX")!=null);
		assertTrue(((Map<String, Object>)list.get(0).get("dataElements")).get("rotationSpeed")!=null);
		
		
	}

}
