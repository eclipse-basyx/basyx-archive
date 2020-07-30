package org.eclipse.basyx.testsuite.regression.submodel.metamodel.connected.submodelelement.dataelement;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedBlob;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.Blob;
import org.eclipse.basyx.submodel.restapi.PropertyProvider;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;
import org.eclipse.basyx.vab.support.TypeDestroyingProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if a ConnectedBlob can be created and used correctly
 * 
 * @author conradi
 *
 */
public class TestConnectedBlob {

	ConnectedBlob connectedBlob;
	Blob blob;
	
	@Before
	public void build() {
		blob = new Blob();
		blob.setValue("BLOB_VALUE".getBytes());
		blob.setMimeType("mimeType");
		
		VABConnectionManagerStub manager = new VABConnectionManagerStub(
				new PropertyProvider(new TypeDestroyingProvider(new VABLambdaProvider(blob))));

		connectedBlob = new ConnectedBlob(manager.connectToVABElement(""));
	}
	
	/**
	 * Tests if getValue() returns the correct value
	 */
	@Test
	public void testGetValue() {
		assertTrue(Arrays.equals(blob.getValue(), connectedBlob.getValue()));
		assertArrayEquals(blob.getValue(), connectedBlob.getValue());
	}
	
	/**
	 * Tests if getMimeType() returns the correct value
	 */
	@Test
	public void testGetMimeType() {
		assertEquals(blob.getMimeType(), connectedBlob.getMimeType());
	}
	
}
