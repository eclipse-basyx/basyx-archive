package org.eclipse.basyx.testsuite.regression.vab.serialization;

public class TestJSONAcceptors {

	public static String a = "{\r\n" + "    \"success\": true,\r\n" + "    \"entityType\": \"string\",\r\n"
			+ "    \"isException\": false,\r\n" + "    \"messages\": {\r\n" + "        \"code\": \"string\",\r\n"
			+ "        \"messageType\": \"Unspecified\",\r\n" + "        \"text\": \"string\"\r\n" + "    },\r\n"
			+ "    \"entity\": {\r\n" + "        \"idShort\": \"\",\r\n" + "        \"kind\": 1,\r\n"
			+ "        \"valueType\": {\r\n" + "            \"dataObjectType\": 12,\r\n"
			+ "            \"isCollection\": false,\r\n" + "            \"isMap\": false\r\n" + "        },\r\n"
			+ "        \"description\": \"\",\r\n" + "        \"category\": \"\",\r\n"
			+ "        \"hasFullSemanticDescription\": [],\r\n" + "        \"value\": 5\r\n" + "    }\r\n" + "}";

	public static String b = "{\r\n" + "    \"success\": true,\r\n" + "    \"entityType\": \"string\",\r\n"
			+ "    \"isException\": false,\r\n" + "    \"messages\": {\r\n" + "        \"code\": \"string\",\r\n"
			+ "        \"messageType\": \"Unspecified\",\r\n" + "        \"text\": \"string\"\r\n" + "    },\r\n"
			+ "    \"entity\": {\r\n" + "        \"idShort\": \"\",\r\n" + "        \"kind\": 1,\r\n"
			+ "        \"valueType\": {\r\n" + "            \"dataObjectType\": 4,\r\n"
			+ "            \"isCollection\": false,\r\n" + "            \"isMap\": false\r\n" + "        },\r\n"
			+ "        \"description\": \"\",\r\n" + "        \"category\": \"\",\r\n"
			+ "        \"hasFullSemanticDescription\": [],\r\n" + "        \"value\": 5.4\r\n" + "    }\r\n" + "}";

	public static String c = "{\r\n" + "    \"success\": true,\r\n" + "    \"entityType\": \"string\",\r\n"
			+ "    \"isException\": false,\r\n" + "    \"messages\": {\r\n" + "        \"code\": \"string\",\r\n"
			+ "        \"messageType\": \"Unspecified\",\r\n" + "        \"text\": \"string\"\r\n" + "    },\r\n"
			+ "    \"entity\": {\r\n" + "        \"idShort\": \"\",\r\n" + "        \"kind\": 1,\r\n"
			+ "        \"valueType\": {\r\n" + "            \"dataObjectType\": 3,\r\n"
			+ "            \"isCollection\": false,\r\n" + "            \"isMap\": false\r\n" + "        },\r\n"
			+ "        \"description\": \"\",\r\n" + "        \"category\": \"\",\r\n"
			+ "        \"hasFullSemanticDescription\": [],\r\n" + "        \"value\": 5.2\r\n" + "    }\r\n" + "}";

	public static String d = "{\r\n" + "    \"success\": true,\r\n" + "    \"entityType\": \"string\",\r\n"
			+ "    \"isException\": false,\r\n" + "    \"messages\": {\r\n" + "        \"code\": \"string\",\r\n"
			+ "        \"messageType\": \"Unspecified\",\r\n" + "        \"text\": \"string\"\r\n" + "    },\r\n"
			+ "    \"entity\": {\r\n" + "        \"idShort\": \"\",\r\n" + "        \"kind\": 1,\r\n"
			+ "        \"valueType\": {\r\n" + "            \"dataObjectType\": 104,\r\n"
			+ "            \"isCollection\": true,\r\n" + "            \"isMap\": false\r\n" + "        },\r\n"
			+ "        \"description\": \"\",\r\n" + "        \"category\": \"\",\r\n"
			+ "        \"hasFullSemanticDescription\": [],\r\n" + "        \"value\": [\r\n" + "            1,\r\n"
			+ "            2\r\n" + "        ]\r\n" + "    }\r\n" + "}";

	public static String e = "{\r\n" + "    \"success\": true,\r\n" + "    \"entityType\": \"string\",\r\n"
			+ "    \"isException\": false,\r\n" + "    \"messages\": {\r\n" + "        \"code\": \"string\",\r\n"
			+ "        \"messageType\": \"Unspecified\",\r\n" + "        \"text\": \"string\"\r\n" + "    },\r\n"
			+ "    \"entity\": {\r\n" + "        \"idShort\": \"\",\r\n" + "        \"kind\": 1,\r\n"
			+ "        \"valueType\": {\r\n" + "            \"dataObjectType\": 103,\r\n"
			+ "            \"isCollection\": false,\r\n" + "            \"isMap\": true\r\n" + "        },\r\n"
			+ "        \"description\": \"\",\r\n" + "        \"category\": \"\",\r\n"
			+ "        \"hasFullSemanticDescription\": [],\r\n" + "        \"value\": {\r\n"
			+ "            \"one\": 1,\r\n" + "            \"two\": 2\r\n" + "        }\r\n" + "    }\r\n" + "}";
	
	
	public static String message = "{\r\n" + 
			"     \"success\": true,\r\n" + 
			"     \"entityType\": \"string\",\r\n" + 
			"     \"isException\": false,\r\n" + 
			"     \"messages\": {\r\n" + 
			"          \"code\": \"string\",\r\n" + 
			"          \"messageType\": \"Unspecified\",\r\n" + 
			"          \"text\": \"string\"\r\n" + 
			"     },\r\n" + 
			"     \"entity\": {\r\n" + 
			"          \"id_carrier\": \"id_carrier test\",\r\n" + 
			"          \"id_submodelDefinition\": \"id_submodelDefinition test..\",\r\n" + 
			"          \"id_hasTemplate\": [],\r\n" + 
			"          \"identification\": {\r\n" + 
			"               \"idType\": 0,\r\n" + 
			"               \"id\": \"\"\r\n" + 
			"          },\r\n" + 
			"          \"operations\": {\"foo\": {\r\n" + 
			"               \"idShort\": \"\",\r\n" + 
			"               \"kind\": 1,\r\n" + 
			"               \"description\": \"\",\r\n" + 
			"               \"invokable\": \"isMethod\",\r\n" + 
			"               \"hasFullSemanticDescription\": [],\r\n" + 
			"               \"category\": \"\"\r\n" + 
			"          }},\r\n" + 
			"          \"idShort\": \"MySubmodel\",\r\n" + 
			"          \"kind\": 1,\r\n" + 
			"          \"description\": \"\",\r\n" + 
			"          \"hasFullSemanticDescription\": [],\r\n" + 
			"          \"category\": \"My category\",\r\n" + 
			"          \"properties\": {\r\n" + 
			"               \"a\": {\r\n" + 
			"                    \"idShort\": \"\",\r\n" + 
			"                    \"kind\": 1,\r\n" + 
			"                    \"valueType\": {\r\n" + 
			"                         \"dataObjectType\": 12,\r\n" + 
			"                         \"isCollection\": false,\r\n" + 
			"                         \"isMap\": false\r\n" + 
			"                    },\r\n" + 
			"                    \"description\": \"\",\r\n" + 
			"                    \"category\": \"\",\r\n" + 
			"                    \"hasFullSemanticDescription\": [],\r\n" + 
			"                    \"value\": 5\r\n" + 
			"               },\r\n" + 
			"               \"b\": {\r\n" + 
			"                    \"idShort\": \"\",\r\n" + 
			"                    \"kind\": 1,\r\n" + 
			"                    \"valueType\": {\r\n" + 
			"                         \"dataObjectType\": 4,\r\n" + 
			"                         \"isCollection\": false,\r\n" + 
			"                         \"isMap\": false\r\n" + 
			"                    },\r\n" + 
			"                    \"description\": \"\",\r\n" + 
			"                    \"category\": \"\",\r\n" + 
			"                    \"hasFullSemanticDescription\": [],\r\n" + 
			"                    \"value\": 5.4\r\n" + 
			"               },\r\n" + 
			"               \"c\": {\r\n" + 
			"                    \"idShort\": \"\",\r\n" + 
			"                    \"kind\": 1,\r\n" + 
			"                    \"valueType\": {\r\n" + 
			"                         \"dataObjectType\": 3,\r\n" + 
			"                         \"isCollection\": false,\r\n" + 
			"                         \"isMap\": false\r\n" + 
			"                    },\r\n" + 
			"                    \"description\": \"\",\r\n" + 
			"                    \"category\": \"\",\r\n" + 
			"                    \"hasFullSemanticDescription\": [],\r\n" + 
			"                    \"value\": 5.2\r\n" + 
			"               },\r\n" + 
			"               \"d\": {\r\n" + 
			"                    \"idShort\": \"\",\r\n" + 
			"                    \"kind\": 1,\r\n" + 
			"                    \"valueType\": {\r\n" + 
			"                         \"dataObjectType\": 104,\r\n" + 
			"                         \"isCollection\": true,\r\n" + 
			"                         \"isMap\": false\r\n" + 
			"                    },\r\n" + 
			"                    \"description\": \"\",\r\n" + 
			"                    \"category\": \"\",\r\n" + 
			"                    \"hasFullSemanticDescription\": [],\r\n" + 
			"                    \"value\": [\r\n" + 
			"                         1,\r\n" + 
			"                         2\r\n" + 
			"                    ]\r\n" + 
			"               },\r\n" + 
			"               \"e\": {\r\n" + 
			"                    \"idShort\": \"\",\r\n" + 
			"                    \"kind\": 1,\r\n" + 
			"                    \"valueType\": {\r\n" + 
			"                         \"dataObjectType\": 103,\r\n" + 
			"                         \"isCollection\": false,\r\n" + 
			"                         \"isMap\": true\r\n" + 
			"                    },\r\n" + 
			"                    \"description\": \"\",\r\n" + 
			"                    \"category\": \"\",\r\n" + 
			"                    \"hasFullSemanticDescription\": [],\r\n" + 
			"                    \"value\": {\r\n" + 
			"                         \"one\": 1,\r\n" + 
			"                         \"two\": 2\r\n" + 
			"                    }\r\n" + 
			"               },\r\n" + 
			"               \"f\": {\r\n" + 
			"                    \"operations\": {\"innerFoo\": {\r\n" + 
			"                         \"idShort\": \"\",\r\n" + 
			"                         \"kind\": 1,\r\n" + 
			"                         \"description\": \"\",\r\n" + 
			"                         \"invokable\": \"isMethod\",\r\n" + 
			"                         \"hasFullSemanticDescription\": [],\r\n" + 
			"                         \"category\": \"\"\r\n" + 
			"                    }},\r\n" + 
			"                    \"idShort\": \"\",\r\n" + 
			"                    \"kind\": 1,\r\n" + 
			"                    \"description\": \"\",\r\n" + 
			"                    \"category\": \"\",\r\n" + 
			"                    \"hasFullSemanticDescription\": [],\r\n" + 
			"                    \"properties\": {\"f1\": {\r\n" + 
			"                         \"idShort\": \"\",\r\n" + 
			"                         \"kind\": 1,\r\n" + 
			"                         \"valueType\": {\r\n" + 
			"                              \"dataObjectType\": 12,\r\n" + 
			"                              \"isCollection\": false,\r\n" + 
			"                              \"isMap\": false\r\n" + 
			"                         },\r\n" + 
			"                         \"description\": \"\",\r\n" + 
			"                         \"category\": \"\",\r\n" + 
			"                         \"hasFullSemanticDescription\": [],\r\n" + 
			"                         \"value\": 42\r\n" + 
			"                    }}\r\n" + 
			"               }\r\n" + 
			"          }\r\n" + 
			"     }\r\n" + 
			"}\r\n" + 
			"";
}
