package org.eclipse.basyx.testsuite.regression.extensions.aas.directory.tagged.proxy;

import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.extensions.aas.directory.tagged.api.IAASTaggedDirectory;
import org.eclipse.basyx.extensions.aas.directory.tagged.proxy.TaggedDirectoryProxy;
import org.eclipse.basyx.extensions.aas.directory.tagged.restapi.TaggedDirectoryProvider;
import org.eclipse.basyx.testsuite.regression.extensions.aas.directory.tagged.TestTaggedDirectorySuite;

public class TestProxyTaggedDirectory extends TestTaggedDirectorySuite {

	@Override
	protected IAASTaggedDirectory getDirectory() {
		return new TaggedDirectoryProxy(new TaggedDirectoryProvider());
	}

	@Override
	protected IAASRegistryService getRegistryService() {
		return getDirectory();
	}

}
