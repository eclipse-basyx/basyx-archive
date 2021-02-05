package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.submodelelement.dataelement;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.Blob;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if a Blob stores its data correctly
 * 
 * @author espen
 *
 */
public class TestBlob {
	public final String BLOB_CONTENT = "BLOB_VALUE";

	protected Blob blob;
	protected String testString = "NEW!";
	protected byte[] testBytes = testString.getBytes(StandardCharsets.US_ASCII);
	protected String testBase64 = Base64.getEncoder().encodeToString(testBytes);
	
	@Before
	public void build() {
		blob = new Blob("testIdShort", "mimeType");
	}
	
	/**
	 * Tests if getMimeType() returns the correct value
	 */
	@Test
	public void testGetMimeType() {
		assertEquals("mimeType", blob.getMimeType());
	}

	/**
	 * Tests if getValue() returns the correct value if it hasn't been set before
	 */
	@Test
	public void testGetEmptyValue() {
		assertNull(blob.getValue());
	}

	/**
	 * Tests if setValue sets the correct values
	 */
	@Test
	public void testSetValue() {
		blob.setValue(testBase64);
		assertEquals(testString, blob.getASCIIString());
		assertArrayEquals(testBytes, blob.getByteArrayValue());
		assertEquals(testBase64, blob.getValue());
	}
	
	/**
	 * Tests if setASCII sets the correct value
	 */
	@Test
	public void testSetASCII() {
		blob.setASCIIString(testString);
		assertEquals(testString, blob.getASCIIString());
		assertArrayEquals(testBytes, blob.getByteArrayValue());
		assertEquals(testBase64, blob.getValue());
	}

	/**
	 * Tests if SetByteArrayValue() sets the correct value
	 */
	@Test
	public void testSetByteArray() {
		blob.setByteArrayValue(testBytes);
		assertEquals(testString, blob.getASCIIString());
		assertArrayEquals(testBytes, blob.getByteArrayValue());
		assertEquals(testBase64, blob.getValue());
	}
}
