package org.eclipse.basyx.testsuite.regression.vab.modelprovider.filesystem;

import org.eclipse.basyx.testsuite.regression.vab.modelprovider.SimpleVABElement;
import org.eclipse.basyx.testsuite.regression.vab.modelprovider.TestProvider;
import org.eclipse.basyx.testsuite.regression.vab.protocol.http.TestsuiteDirectory;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.modelprovider.filesystem.FileSystemProvider;
import org.eclipse.basyx.vab.modelprovider.filesystem.filesystem.FileSystem;
import org.eclipse.basyx.vab.modelprovider.filesystem.filesystem.GenericFileSystem;
import org.eclipse.basyx.vab.protocol.api.ConnectorProvider;

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
