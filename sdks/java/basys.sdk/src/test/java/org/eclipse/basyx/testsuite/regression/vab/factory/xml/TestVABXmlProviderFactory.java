package org.eclipse.basyx.testsuite.regression.vab.factory.xml;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.basyx.vab.factory.xml.VABXmlProviderFactory;
import org.eclipse.basyx.vab.modelprovider.list.ReferencedArrayList;
import org.eclipse.basyx.vab.modelprovider.map.VABHashmapProvider;
import org.junit.Test;

public class TestVABXmlProviderFactory {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testResources() throws Exception {

		VABXmlProviderFactory factory = new VABXmlProviderFactory();
		VABHashmapProvider provider = factory.createVABElements(TestXmlParser.xmlTestContent);

		Map<String, String> map;

		assertEquals(provider.getModelPropertyValue("tags/name/"), TestXmlParser.SOME_NAME);
		assertEquals(provider.getModelPropertyValue("tags/value/"), TestXmlParser.VALUEV);
		assertEquals(provider.getModelPropertyValue("tags/nestedTags/attrnt"), TestXmlParser.ATTRNT_1_VAL);
		assertEquals(((ReferencedArrayList) provider.getModelPropertyValue("tags/nestedTags/nestedTag/")).get(0),
				TestXmlParser.NESTED_TAG_1);
		assertEquals(((ReferencedArrayList) provider.getModelPropertyValue("tags/nestedTags/nestedTag/")).get(1),
				TestXmlParser.NESTED_TAG_2);
		assertEquals(((ReferencedArrayList) provider.getModelPropertyValue("tags/nestedTags/nestedTag/")).get(2),
				TestXmlParser.NESTED_TAG_3);

		map = (Map<String, String>) ((ReferencedArrayList) provider
				.getModelPropertyValue("tags/deeplyNestedTagParent/deeplyNestedTagsChild/deeplyNestedTagsLeaf/"))
						.get(0);
		assertEquals(map.get(TestXmlParser.TEXT), TestXmlParser.DN_TEXT_1);
		assertEquals(map.get(TestXmlParser.ATTRDN), TestXmlParser.ATTR_1_VAL);

		map = (Map<String, String>) ((ReferencedArrayList) provider
				.getModelPropertyValue("tags/deeplyNestedTagParent/deeplyNestedTagsChild/deeplyNestedTagsLeaf/"))
						.get(1);
		assertEquals(map.get(TestXmlParser.TEXT), TestXmlParser.DN_TEXT_2);
		assertEquals(map.get(TestXmlParser.ATTRDN), TestXmlParser.ATTR_2_VAL);

		assertEquals(((ReferencedArrayList) provider.getModelPropertyValue("tags/someTag/")).get(0),
				TestXmlParser.SOME_TEXT_1);
		assertEquals(((ReferencedArrayList) provider.getModelPropertyValue("tags/someTag/")).get(1),
				TestXmlParser.SOME_TEXT_2);
		assertEquals(((ReferencedArrayList) provider.getModelPropertyValue("tags/someTag/")).get(2),
				TestXmlParser.SOME_TEXT_3);

	}

}
