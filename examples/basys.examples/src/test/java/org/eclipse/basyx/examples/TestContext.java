package org.eclipse.basyx.examples;

import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;

/**
 * This class is used to define test context once for all the test projects.
 * So that multiple context does not get created
 * For now, only SQL context is created here
 * @author haque
 *
 */
public class TestContext {
	
	// A new instance of SQL context used in all test project
	public static BaSyxContext sqlContext = new BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory();
}
