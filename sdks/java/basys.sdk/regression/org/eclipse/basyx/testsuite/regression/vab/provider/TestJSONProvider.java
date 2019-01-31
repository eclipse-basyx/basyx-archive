package org.eclipse.basyx.testsuite.regression.vab.provider;

import java.io.FileNotFoundException;

import org.eclipse.basyx.testsuite.support.vab.stub.PrintWriterStub;
import org.eclipse.basyx.testsuite.support.vab.stub.VABHashMapProviderStub;
import org.eclipse.basyx.vab.backend.server.JSONProvider;
import org.junit.Test;

public class TestJSONProvider {
	
	private static String seven =  "{\r\n" + 
			"    \"typeid\": \"int\",\r\n" + 
			"    \"value\": 7,\r\n" + 
			"    \"basystype\": \"value\"\r\n" + 
			"}";
	private static String nullParam = "{\"basystype\":\"null\"}";
	private static String droppedHttpParam = "";
	
	private static String acceptFail = "{\"type\":\"org.eclipse.basyx.aas.api.exception.LostHTTPRequestParameterException\",\"message\":\"A request on test/okhas been received without a valid json parameter (unresolved issue)\",\"basystype\":\"exception\"}";
	private static String acceptInvoke = "{\"basystype\":\"null\"}";
	private static String acceptTrue = "{\"typeid\":\"boolean\",\"value\":true,\"basystype\":\"value\"}";
	

	
	JSONProvider<VABHashMapProviderStub> jsonProvider = new JSONProvider<VABHashMapProviderStub>(new VABHashMapProviderStub());

	@Test
	public void testProcessBaSysSet() throws FileNotFoundException {
		
		jsonProvider.processBaSysSet("test/ok", seven, new PrintWriterStub("test.txt", acceptTrue)); // set PrintWriter to accept normal json
		
	}
	
	@Test
	public void testProcessBaSysSetFail() throws FileNotFoundException {
		
		jsonProvider.processBaSysSet("test/ok", droppedHttpParam, new PrintWriterStub("test.txt", acceptFail)); // set PrintWriter to accept exception
		
	}
	
	@Test
	public void testProcessBaSysCreate() throws FileNotFoundException {
		
		jsonProvider.processBaSysCreate("test/ok", seven, new PrintWriterStub("test.txt", acceptTrue)); // set PrintWriter to accept normal json
		
	}
	
	@Test
	public void testProcessBaSysCreateFail() throws FileNotFoundException {
		
		jsonProvider.processBaSysCreate("test/ok", droppedHttpParam, new PrintWriterStub("test.txt", acceptFail)); // set PrintWriter to accept exception
		
	}
	
	@Test
	public void testProcessBaSysInvoke() throws FileNotFoundException {
		
		jsonProvider.processBaSysInvoke("test/ok", seven, new PrintWriterStub("test.txt", acceptInvoke)); // set PrintWriter to accept normal json
		
	}
	
	@Test
	public void testProcessBaSysInvokeFail() throws FileNotFoundException {
		
		jsonProvider.processBaSysInvoke("test/ok", droppedHttpParam, new PrintWriterStub("test.txt", acceptFail)); // set PrintWriter to accept exception
		
	}
	
	@Test
	public void testProcessBaSysDelete() throws FileNotFoundException {
		
		jsonProvider.processBaSysDelete("test/ok", nullParam, new PrintWriterStub("test.txt", acceptTrue)); // set PrintWriter to accept normal json
		jsonProvider.processBaSysDelete("test/ok", seven, new PrintWriterStub("test.txt", acceptTrue)); // set PrintWriter to accept normal json
		
	}
	
	@Test
	public void testProcessBaSysDeleteFail() throws FileNotFoundException {
		
		jsonProvider.processBaSysDelete("test/ok", droppedHttpParam, new PrintWriterStub("test.txt", acceptFail)); // set PrintWriter to accept exception
		jsonProvider.processBaSysDelete("test/ok", droppedHttpParam, new PrintWriterStub("test.txt", acceptFail)); // set PrintWriter to accept exception
		
	}
}
