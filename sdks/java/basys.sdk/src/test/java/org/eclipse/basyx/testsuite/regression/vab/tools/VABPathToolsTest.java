package org.eclipse.basyx.testsuite.regression.vab.tools;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.vab.core.tools.VABPathTools;
import org.junit.Test;

public class VABPathToolsTest {
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
	}

	/**
	 * Tests get address
	 */
	@Test
	public void testGetAddress() {
		assertEquals("", VABPathTools.getAddressEntry(pathWithoutAddress));
		assertEquals("basyx://127.0.0.1:6889", VABPathTools.getAddressEntry(pathWithTwoAddress));
		assertEquals(onlyAddress, VABPathTools.getAddressEntry(onlyAddress));
	}

}
