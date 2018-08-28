package org.eclipse.basyx.testsuite.regression.aas.impl.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.impl.provider.filesystem.FileSystemProvider;
import org.eclipse.basyx.aas.impl.provider.filesystem.filesystem.File;
import org.eclipse.basyx.aas.impl.provider.filesystem.filesystem.FileSystem;
import org.eclipse.basyx.aas.impl.provider.filesystem.filesystem.FileType;
import org.eclipse.basyx.aas.impl.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.testsuite.regression.aas.impl.provider.testfragments.TestProviderFull_delete;
import org.eclipse.basyx.testsuite.regression.aas.impl.provider.testfragments.TestProviderFull_get;
import org.eclipse.basyx.testsuite.regression.aas.impl.provider.testfragments.TestProviderFull_set;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub1SubmodelType;
import org.junit.Before;
import org.junit.Test;

public class TestFileSystemProvider {
	private static class MockUpFileSystem implements FileSystem {

		public Map<String, String> files = new HashMap<>();

		@Override
		public String readFile(String path) {
			System.out.println("Read File " + path);
			return files.getOrDefault(path, "");
		}

		@Override
		public void writeFile(String path, String content) {
			System.out.println("Write File " + path);
			files.put(path, content);
		}

		@Override
		public void deleteFile(String path) {
			files.remove(path);
		}

		@Override
		public void createDirectory(String path) {
			System.out.println("Creating directory " + path);
			files.put(path, "");
		}

		@Override
		public List<File> readDirectory(String path) {
			List<File> ret = new ArrayList<>();
			for (String s : files.keySet()) {
				if (s.startsWith(path)) {
					if (s.endsWith("/data")) {
						ret.add(new File(s, FileType.DATA));
					} else {
						ret.add(new File(s, FileType.DIRECTORY));
					}
				}
			}
			return ret;
		}

		@Override
		public void deleteDirectory(String path) {
			System.out.println("Remove Directory: " + path);
			List<String> toRemove = new ArrayList<>();
			for (String s : files.keySet()) {
				if (s.startsWith(path)) {
					toRemove.add(s);
				}
			}
			for (String t : toRemove) {
				files.remove(t);
			}
		}
	}
	
	MockUpFileSystem mockup;
	FileSystemProvider provider;
	String root = "HMDR/Test";
	
	@Before
	public void buildFileSystem() throws Exception {
		mockup = new MockUpFileSystem();
		provider = new FileSystemProvider(mockup, root);
		AssetAdministrationShell shell = new AssetAdministrationShell();
		shell.addSubModel(new Stub1SubmodelType());
		shell.setId("Stub1AAS");
		provider.createValue("", shell);
	}
	
	@Test
	public void getTest() {
		TestProviderFull_get.testGet(provider);
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
