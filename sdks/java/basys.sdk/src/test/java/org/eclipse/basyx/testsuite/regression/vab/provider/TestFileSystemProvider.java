package org.eclipse.basyx.testsuite.regression.vab.provider;

import org.eclipse.basyx.aas.backend.connector.ConnectorProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.eclipse.basyx.testsuite.support.vab.stub.elements.SimpleVABElement;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.provider.filesystem.FileSystemProvider;
import org.eclipse.basyx.vab.provider.filesystem.filesystem.FileSystem;
import org.eclipse.basyx.vab.provider.filesystem.filesystem.impl.GenericFileSystem;

/**
 * Tests the functionality of the VABHashmapProvider according to the test cases
 * in the snippet package
 * 
 * @author schnicke
 *
 */
public class TestFileSystemProvider extends TestProvider {
	private VABConnectionManager connManager;

	@Override
	protected VABConnectionManager getConnectionManager() {
		if (connManager == null) {
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
		return connManager;
	}

	@Override
	public void testMapInvoke() {
		// not implemented for file system providers
	}
}
