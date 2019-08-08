package org.eclipse.basyx.testsuite.regression.vab.core.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.vab.core.tools.VABPathTools;
import org.junit.Test;

/**
 * Tests the functionality of the VABPathTools utility class
 * 
 * @author espen
 *
 */
public class VABPathToolsTest {
	// test all cases of "empty" paths
	String[] empty = new String[] { "/", "" };

	// test all cases of /-placement for single elements
	String[] single = new String[] { "element", "/element", "element/", "/element/" };

	// test all cases of /-placement for multiple elements
	String[] pair = new String[] { "parent/child", "/parent/child", "parent/child/", "/parent/child/" };

	// Test strings
	String pathWithoutAddress = "a/b/c";
	String pathWithOneAddress = "http://AASServer//a/b/c";
	String pathWithTwoAddress = "basyx://127.0.0.1:6889//http://AASServer//a/b/c";
	String onlyAddress = "basyx://127.0.0.1:6889";

	/**
	 * Tests remove address
	 */
	@Test
	public void testRemoveAddress() {
		assertEquals(pathWithoutAddress, VABPathTools.removeAddressEntry(pathWithoutAddress));
		assertEquals(pathWithoutAddress, VABPathTools.removeAddressEntry(pathWithOneAddress));
		assertEquals("", VABPathTools.removeAddressEntry(onlyAddress));
		assertNull(VABPathTools.removeAddressEntry(null));
	}

	/**
	 * Tests get address
	 */
	@Test
	public void testGetAddress() {
		assertEquals("", VABPathTools.getAddressEntry(pathWithoutAddress));
		assertEquals("basyx://127.0.0.1:6889", VABPathTools.getAddressEntry(pathWithTwoAddress));
		assertEquals(onlyAddress, VABPathTools.getAddressEntry(onlyAddress));
		assertNull(VABPathTools.getAddressEntry(null));
	}

	/**
	 * For each element in the path, exactly one string element should be present in
	 * the resulting array
	 */
	@Test
	public void testSplitPath() {
		for (String test : empty) {
			assertEquals(0, VABPathTools.splitPath(test).length);
		}
		for (String test : single) {
			assertEquals(1, VABPathTools.splitPath(test).length);
			assertEquals("element", VABPathTools.splitPath(test)[0]);
		}
		for (String test : pair) {
			assertEquals(2, VABPathTools.splitPath(test).length);
			assertEquals("parent", VABPathTools.splitPath(test)[0]);
			assertEquals("child", VABPathTools.splitPath(test)[1]);
		}
		assertNull(VABPathTools.splitPath(null));
	}

	/**
	 * Test concenating multiple paths
	 */
	@Test
	public void concatenatePaths() {
		for (String a : single) {
			assertEquals("element", VABPathTools.concatenatePaths(a));
			for (String b : pair) {
				assertEquals("element/parent/child", VABPathTools.concatenatePaths(a, b));
			}
		}
		assertEquals("", VABPathTools.concatenatePaths(""));
		assertNull(VABPathTools.concatenatePaths());
		assertNull(VABPathTools.concatenatePaths(null, ""));
		assertNull(VABPathTools.concatenatePaths("", null));
	}

	@Test
	public void testGetParentPath() {
		for (String test : empty) {
			assertEquals("", VABPathTools.getParentPath(test));
		}
		for (String test : single) {
			assertEquals("", VABPathTools.getParentPath(test));
		}
		for (String test : pair) {
			assertEquals("parent", VABPathTools.getParentPath(test));
		}
		assertNull(VABPathTools.getParentPath(null));
	}

	@Test
	public void testGetLastElement() {
		for (String test : empty) {
			assertEquals("", VABPathTools.getLastElement(test));
		}
		for (String test : single) {
			assertEquals("element", VABPathTools.getLastElement(test));
		}
		for (String test : pair) {
			assertEquals("child", VABPathTools.getLastElement(test));
		}
		assertNull(VABPathTools.getLastElement(null));
	}

	@Test
	public void testRemovePrefix() {
		for (String test : empty) {
			assertEquals("", VABPathTools.removePrefix(test, "/"));
		}
		for (String test : single) {
			assertTrue(VABPathTools.removePrefix(test, "/").startsWith("element"));
		}
		for (String test : pair) {
			assertTrue(VABPathTools.removePrefix(test, "/").startsWith("parent"));
		}
		assertNull(VABPathTools.removePrefix(null, "/"));
	}

	@Test
	public void testBuildPath() {
		assertEquals("", VABPathTools.buildPath(new String[] {}, 0));
		assertEquals("b", VABPathTools.buildPath(new String[] { "a", "b" }, 1));
		assertEquals("b/c", VABPathTools.buildPath(new String[] { "a", "b", "c" }, 1));
		assertEquals("a/b/c", VABPathTools.buildPath(new String[] { "a", "b", "c" }, 0));
		assertNull(VABPathTools.buildPath(null, 0));
	}

	@Test
	public void testAppend() {
		assertEquals("/parent/child", VABPathTools.append("/parent", "child"));
		assertEquals("/parent/child", VABPathTools.append("/parent/", "child"));
		assertEquals("/parent/x/child", VABPathTools.append("/parent/x", "child"));
		assertEquals("/parent/x/child", VABPathTools.append("/parent/x/", "child"));
		assertNull(VABPathTools.append(null, ""));
		assertNull(VABPathTools.append("", null));
	}

	@Test
	public void testIsOperationPath() {
		String[] positive = { "operations", "operations/", "/operations", "operations/", "operations/test/",
				"operations/test", "/operations/test", "operations/test/" };
		String[] negative = { "", "/operationX/", "/myOperation/", "/operationsFake/", "/operationsFake/operationX/" };
		for (String test : positive) {
			assertTrue(test, VABPathTools.isOperationPath(test));
		}
		for (String test : negative) {
			assertFalse(test, VABPathTools.isOperationPath(test));
		}
		assertFalse(VABPathTools.isOperationPath(null));
	}
}