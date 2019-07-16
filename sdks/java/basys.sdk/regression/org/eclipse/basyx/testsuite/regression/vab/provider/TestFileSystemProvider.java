package org.eclipse.basyx.testsuite.regression.vab.provider;

import org.eclipse.basyx.aas.backend.connector.ConnectorProvider;
import org.eclipse.basyx.testsuite.regression.vab.snippet.CreateDelete;
import org.eclipse.basyx.testsuite.regression.vab.snippet.GetPropertyValue;
import org.eclipse.basyx.testsuite.regression.vab.snippet.ListReferences;
import org.eclipse.basyx.testsuite.regression.vab.snippet.SetPropertyValue;
import org.eclipse.basyx.testsuite.regression.vab.snippet.TestCollectionProperty;
import org.eclipse.basyx.testsuite.regression.vab.snippet.TestMapProperty;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.eclipse.basyx.testsuite.support.vab.stub.elements.SimpleVABElement;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.provider.filesystem.FileSystemProvider;
import org.eclipse.basyx.vab.provider.filesystem.filesystem.FileSystem;
import org.eclipse.basyx.vab.provider.filesystem.filesystem.impl.GenericFileSystem;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the functionality of the VABHashmapProvider according to the test cases
 * in the snippet package
 * 
 * @author schnicke
 *
 */
public class TestFileSystemProvider {

	protected static VABConnectionManager connManager;

	@BeforeClass
	public static void build() {
		connManager = new VABConnectionManager(new TestsuiteDirectory(), new ConnectorProvider() {

			@Override
			protected IModelProvider createProvider(String addr) {

				String root = "regressiontest/HMDR/Test";
				FileSystem fs = new GenericFileSystem();
				try {
					FileSystemProvider provider = new FileSystemProvider(fs, root, new SimpleVABElement(), true);
					return provider;
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException();
				}
			}
		});
	}
	
	@Test
	public void testCreateDelete() {
		CreateDelete.test(connManager);
	}

	@Test
	public void testGet() {
		GetPropertyValue.test(connManager);
	}

	@Test
	public void testSet() {
		SetPropertyValue.test(connManager);
	}

	@Test
	public void testListReferences() {
		ListReferences.test(connManager);
	}

	@Test
	public void testMapGet() {
		TestMapProperty.testGet(connManager);
	}

	@Test
	public void testMapUpdateComplete() {
		TestMapProperty.testUpdateComplete(connManager);
	}

	@Test
	public void testMapUpdateNonexisting() {
		TestMapProperty.testUpdateNonexisting(connManager);
	}

	@Test
	public void testMapUpdateElement() {
		TestMapProperty.testUpdateElement(connManager);
	}

	@Test
	public void testMapRemoveElement() {
		TestMapProperty.testRemoveElement(connManager);
	}

	@Test
	public void testCollectionGet() {
		TestCollectionProperty.testGet(connManager);
	}

	@Test
	public void testCollectionUpdateComplete() {
		TestCollectionProperty.testUpdateComplete(connManager);
	}

	@Test
	public void testCollectionUpdateElement() {
		TestCollectionProperty.testUpdateElement(connManager);
	}

	@Test
	public void testCollectionRemoveElement() {
		TestCollectionProperty.testRemoveElement(connManager);
	}
}
