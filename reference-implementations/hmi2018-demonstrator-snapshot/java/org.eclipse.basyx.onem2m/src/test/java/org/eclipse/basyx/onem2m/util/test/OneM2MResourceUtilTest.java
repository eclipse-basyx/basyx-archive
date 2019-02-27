package org.eclipse.basyx.onem2m.util.test;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.onem2m.util.OneM2MResourceUtil;
import org.eclipse.basyx.onem2m.xml.protocols.Notification;
import org.junit.Test;

public class OneM2MResourceUtilTest {
	
	
	@Test
	public void testJsonToObject() {
		String json = "					{ \r\n" + 
				"					   \"m2m:sgn\" : {\r\n" + 
				"					      \"m2m:nev\" : {\r\n" + 
				"					         \"m2m:rep\" : {\r\n" + 
				"					            \"m2m:cin\" : {\r\n" + 
				"					               \"rn\" : \"cin_846913904\",\r\n" + 
				"					               \"ty\" : 4,\r\n" + 
				"					               \"ri\" : \"/in-cse/cin-846913904\",\r\n" + 
				"					               \"pi\" : \"/in-cse/cnt-706600628\",\r\n" + 
				"					               \"ct\" : \"20180122T152834\",\r\n" + 
				"					               \"lt\" : \"20180122T152834\",\r\n" + 
				"					               \"st\" : 0,\r\n" + 
				"					               \"cnf\" : \"text/plain:0\",\r\n" + 
				"					               \"cs\" : 6,\r\n" + 
				"					               \"con\" : \"[3,10]\"\r\n" + 
				"					            }\r\n" + 
				"					         },\r\n" + 
				"					         \"m2m:rss\" : 1\r\n" + 
				"					      },\r\n" + 
				"					      \"m2m:sud\" : false,\r\n" + 
				"					      \"m2m:sur\" : \"/in-cse/sub-925079313\"\r\n" + 
				"					   }\r\n" + 
				"					}\r\n" + 
				"";
		
		Object o = OneM2MResourceUtil.jsonToObject(json);
		assertTrue(o instanceof Notification);
	
	}

}
