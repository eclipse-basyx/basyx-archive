package org.eclipse.basyx.testsuite.regression.aas.impl.provider.filesystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.impl.provider.filesystem.FileSystemProvider;
import org.eclipse.basyx.aas.impl.provider.filesystem.filesystem.File;
import org.eclipse.basyx.aas.impl.provider.filesystem.filesystem.FileSystem;
import org.eclipse.basyx.aas.impl.provider.filesystem.filesystem.FileType;
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

	@Test
	public void singlePropertyTest() throws Exception {
		System.out.println("--- singlePropertyTest ---");
		MockUpFileSystem mockup = new MockUpFileSystem();
		String singleProp = "AAS1/aas/submodels/SM1/properties/SingleProp";
		String root = "HMDR/Test";

		FileSystemProvider provider = new FileSystemProvider(mockup, "HMDR/Test");
		provider.createValue(singleProp, 0);

		Object o = provider.getModelPropertyValue(singleProp);
		assertEquals(0, o);

		int value = 2;
		try {
			provider.setModelPropertyValue(singleProp, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Check if the written value is as expected
		o = provider.getModelPropertyValue(singleProp);
		assertEquals(2, o);

		// Check if the files are created as expected
		assertEquals(2, mockup.files.size());
		assertTrue(mockup.files.containsKey(root + "/" + singleProp + "/data"));
		assertTrue(mockup.files.containsKey(root + "/" + singleProp));

		String singleProp2 = "AAS1/aas/submodels/SM1/properties/SingleProp2";
		String helloWorld = "Hello World";
		provider.createValue(singleProp2, helloWorld);

		o = provider.getModelPropertyValue(singleProp2);
		assertEquals(helloWorld, o);

		assertEquals(4, mockup.files.size());
		assertTrue(mockup.files.containsKey(root + "/" + singleProp2 + "/data"));
		assertTrue(mockup.files.containsKey(root + "/" + singleProp2));
		assertTrue(mockup.files.containsKey(root + "/" + singleProp + "/data"));
		assertTrue(mockup.files.containsKey(root + "/" + singleProp));

		provider.deleteValue(singleProp2);

		assertEquals(2, mockup.files.size());
		assertTrue(mockup.files.containsKey(root + "/" + singleProp));
		assertTrue(mockup.files.containsKey(root + "/" + singleProp + "/data"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getSpecialTest() throws Exception {
		System.out.println("--- getSpecialTest ---");
		MockUpFileSystem mockup = new MockUpFileSystem();
		String singleProp = "AAS1/aas/submodels/SM1/properties/SingleProp";

		String root = "HMDR/Test";
		
		FileSystemProvider provider = new FileSystemProvider(mockup, root);
		provider.createValue(singleProp, 0);
		provider.createValue(singleProp + 1, 1);
		List<String> props = (List<String>) provider.getModelPropertyValue("iese.fraunhofer.de/AAS1/aas/submodels/SM1/properties");

		assertEquals(2, props.size());

		assertTrue(props.contains(root + "/" + singleProp));
		assertTrue(props.contains(root + "/" + singleProp + 1));

	}

	@Test
	public void collectionPropertyTest() throws Exception {
		System.out.println("--- collectionPropertyTest ---");
		MockUpFileSystem mockup = new MockUpFileSystem();
		String root = "HMDR/Test2";
		FileSystemProvider provider = new FileSystemProvider(mockup, root);
		String testPath = "AAS1/aas/submodels/SM1/properties/TestList";
		String testEntry1 = "TestEntry1";
		String testEntry2 = "TestEntry2";

		provider.createValue(testPath, new ArrayList<>());

		// Test Set -> Get
		provider.setModelPropertyValue(testPath, testEntry1, "X");

		Object o = provider.getModelPropertyValue(testPath);
		assertTrue(o instanceof Collection<?>);
		assertEquals(((Collection<?>) o).iterator().next(), testEntry1);

		// Test adding a value
		provider.setModelPropertyValue(testPath, testEntry2, "X");

		o = provider.getModelPropertyValue(testPath);
		assertTrue(o instanceof Collection<?>);
		assertTrue(((Collection<?>) o).contains(testEntry1));
		assertTrue(((Collection<?>) o).contains(testEntry2));

		// Test removing a value
		provider.deleteValue(testPath, testEntry1);

		o = provider.getModelPropertyValue(testPath);
		assertTrue(o instanceof Collection<?>);
		assertFalse(((Collection<?>) o).contains(testEntry1));
		assertTrue(((Collection<?>) o).contains(testEntry2));

		// Check if files exist
		assertEquals(2, mockup.files.size());
		assertTrue(mockup.files.containsKey(root + "/" + testPath));
		assertTrue(mockup.files.containsKey(root + "/" + testPath + "/data"));
	}
}
