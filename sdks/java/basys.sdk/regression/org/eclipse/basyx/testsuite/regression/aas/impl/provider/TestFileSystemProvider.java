package org.eclipse.basyx.testsuite.regression.aas.impl.provider;

import org.eclipse.basyx.aas.impl.provider.filesystem.FileSystemProvider;
import org.eclipse.basyx.aas.impl.provider.filesystem.filesystem.FileSystem;
import org.eclipse.basyx.aas.impl.provider.filesystem.filesystem.impl.GenericFileSystem;
import org.eclipse.basyx.aas.impl.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.SubModel;
import org.eclipse.basyx.testsuite.regression.aas.impl.provider.testfragments.TestProviderFull_delete;
import org.eclipse.basyx.testsuite.regression.aas.impl.provider.testfragments.TestProviderFull_get;
import org.eclipse.basyx.testsuite.regression.aas.impl.provider.testfragments.TestProviderFull_set;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.aas.Stub1AAS;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub1SubmodelType;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestFileSystemProvider {
	static FileSystem fs;
	static FileSystemProvider provider;
	static String root = "regressiontest/HMDR/Test";

	@BeforeClass
	public static void buildFileSystem() throws Exception {
		fs = new GenericFileSystem();
		fs.deleteDirectory(root);
		provider = new FileSystemProvider(fs, root);
		AssetAdministrationShell stub1AAS = new Stub1AAS();
		new Stub1SubmodelType(stub1AAS);
		SubModel dummy = new SubModel();
		dummy.setId("Stub2SM");
		stub1AAS.addSubModel(dummy);
		provider.createValue("", stub1AAS);
	}

	@Test
	public void getTest() {
		TestProviderFull_get.testGetProperties(provider);
	}

	@Test
	public void setTest() throws Exception {
		TestProviderFull_set.testSet(provider);
	}

	@Test
	public void deleteTest() throws Exception {
		TestProviderFull_delete.testDelete(provider);
	}
}
