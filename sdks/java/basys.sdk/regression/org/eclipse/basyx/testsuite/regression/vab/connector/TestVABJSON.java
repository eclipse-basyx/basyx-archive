package org.eclipse.basyx.testsuite.regression.vab.connector;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.eclipse.basyx.aas.backend.connector.IBaSyxConnector;
import org.eclipse.basyx.aas.backend.connector.JSONConnector;
import org.json.JSONObject;
import org.junit.Test;

/**
 * Test json connector with meta information
 * 
 * @author pschorn
 *
 */
public class TestVABJSON {

	private static final String message_get = "{\"success\":true,\"entityType\":\"string\",\"isException\":false,\"messages\":{\"code\":\"string\",\"messageType\":\"Unspecified\",\"text\":\"string\"},\"entity\":{\"type\":12,\"value\":7}}";
	private static final String message_ack_true = "{\"success\":true,\"entityType\":\"string\",\"isException\":false,\"messages\":{\"code\":\"string\",\"messageType\":\"Unspecified\",\"text\":\"string\"}}";
	private static final String message_ack_false = "{\"success\":false,\"entityType\":\"string\",\"isException\":false,\"messages\":{\"code\":\"string\",\"messageType\":\"Unspecified\",\"text\":\"string\"}}";
	private static final String message_exception = "{\"success\":true,\"entityType\":\"string\",\"isException\":true,\"messages\":{\"code\":\"string\",\"messageType\":\"Unspecified\",\"text\":\"string\"}}";
	private static final String message_inv = "{\"success\":true,\"entityType\":\"string\",\"isException\":false,\"messages\":{\"code\":\"string\",\"messageType\":\"Unspecified\",\"text\":\"string\"},\"entity\":{\"type\":12,\"value\":5}}";

	JSONConnector jsonConnector = new JSONConnector(new ProtocolConnectorStub());

	@SuppressWarnings("unchecked")
	@Test
	public void testGet() {
		HashMap<String, Object> value = (HashMap<String, Object>) jsonConnector.getModelPropertyValue(message_get);

		assertTrue((int) value.get("value") == 7); // to be changed when value is automatically casted
	}

	@Test
	public void testInvoke() {

		HashMap<String, Object> value = (HashMap<String, Object>) jsonConnector.getModelPropertyValue(message_inv);

		assertTrue((int) value.get("value") == 5); // to be changed when value is automatically casted

	}

	@Test
	public void testSet() {
		fail(); // TBD

		try {
			jsonConnector.setModelPropertyValue(message_ack_true, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testCreateDelete() {

		fail(); // TBD
	}

	@Test
	public void testException() {

		fail(); // TBD

		try {
			jsonConnector.setModelPropertyValue(message_exception, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testFailed() {

		fail(); // TBD

		try {
			jsonConnector.setModelPropertyValue(message_ack_false, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class ProtocolConnectorStub implements IBaSyxConnector {

		@Override
		public Object getModelPropertyValue(String path) {

			return path;
		}

		@Override
		public Object setModelPropertyValue(String path, JSONObject newValue) throws Exception {

			return path;
		}

		@Override
		public Object createValue(String path, JSONObject newEntity) throws Exception {

			return path;
		}

		@Override
		public Object deleteValue(String path) throws Exception {

			return path;
		}

		@Override
		public Object deleteValue(String path, JSONObject obj) throws Exception {

			return path;
		}

		@Override
		public Object invokeOperation(String path, JSONObject jsonObject) throws Exception {

			return path;
		}

		@Override
		public Object getContainedElements(String path) {

			return path;
		}
	}
}
