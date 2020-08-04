package org.eclipse.basyx.testsuite.regression.submodel.metamodel.connected.submodelelement.dataelement;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedFile;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.File;
import org.eclipse.basyx.submodel.restapi.PropertyProvider;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;
import org.eclipse.basyx.vab.support.TypeDestroyingProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if a ConnectedFile can be created and used correctly
 * 
 * @author conradi
 *
 */
public class TestConnectedFile {

	ConnectedFile connectedFile;
	File file;
	
	@Before
	public void build() {
		file = new File();
		file.setValue("FILE_VALUE");
		file.setMimeType("mimeType");
		
		VABConnectionManagerStub manager = new VABConnectionManagerStub(
				new PropertyProvider(new TypeDestroyingProvider(new VABLambdaProvider(file))));

		connectedFile = new ConnectedFile(manager.connectToVABElement(""));
	}
	
	/**
	 * Tests if getValue() returns the correct value
	 */
	@Test
	public void testGetValue() {
		assertEquals(file.getValue(), connectedFile.getValue());
	}
	
	/**
	 * Tests if getMimeType() returns the correct value
	 */
	@Test
	public void testGetMimeType() {
		assertEquals(file.getMimeType(), connectedFile.getMimeType());
	}
	
}
