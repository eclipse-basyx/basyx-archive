package org.eclipse.basyx.testsuite.regression.extensions.aas.directory.tagged.map;

import java.util.HashMap;

import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.extensions.aas.directory.tagged.api.IAASTaggedDirectory;
import org.eclipse.basyx.extensions.aas.directory.tagged.map.MapTaggedDirectory;
import org.eclipse.basyx.testsuite.regression.extensions.aas.directory.tagged.TestTaggedDirectorySuite;

/**
 * Tests the map variant of the TaggedDirectory
 * 
 * @author schnicke
 *
 */
public class TestMapTaggedDirectory extends TestTaggedDirectorySuite {

	@Override
	protected IAASTaggedDirectory getDirectory() {
		return new MapTaggedDirectory(new HashMap<>(), new HashMap<>());
	}

	@Override
	protected IAASRegistryService getRegistryService() {
		return getDirectory();
	}

}
