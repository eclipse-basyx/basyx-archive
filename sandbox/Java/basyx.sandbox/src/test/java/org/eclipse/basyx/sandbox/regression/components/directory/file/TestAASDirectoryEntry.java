package org.eclipse.basyx.sandbox.regression.components.directory.file;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.sandbox.components.registry.AASDirectoryEntry;
import org.junit.Test;



/**
 * Test the AAS Directory entry class
 * 
 * @author kuhn
 *
 */
public class TestAASDirectoryEntry {

	
	
	/**
	 * Execute test case that tests parsing of id constructor parameter
	 */
	@Test
	public void testConstuctorParameterID() {
		// Test object with minimal ID
		AASDirectoryEntry aasEntry1 = new AASDirectoryEntry("urn:FHG", "<serializedAAS>", "local", "");
		// - Validate object
		assertTrue(aasEntry1.isValidID());
		assertTrue(aasEntry1.getLegalEntity().equals("FHG"));
		assertTrue(aasEntry1.isLegalEntityOf("FHG"));
		assertFalse(aasEntry1.hasSubUnit());
		assertFalse(aasEntry1.hasSubModel());
		assertFalse(aasEntry1.hasVersion());
		assertFalse(aasEntry1.hasRevision());
		
		
		// Test object with empty ID, SubUnit and sub model
		AASDirectoryEntry aasEntry2 = new AASDirectoryEntry("urn:::::", "<serializedAAS>", "local", "");
		// - Validate object
		assertTrue(aasEntry2.isValidID());
		assertFalse(aasEntry2.hasLegalEntity());
		assertFalse(aasEntry2.hasSubUnit());
		assertFalse(aasEntry2.hasSubModel());
		assertFalse(aasEntry2.hasVersion());
		assertFalse(aasEntry2.hasRevision());

		
		// Test object with almost empty ID and SubUnit
		AASDirectoryEntry aasEntry3 = new AASDirectoryEntry("urn: : : : : ", "<serializedAAS>", "local", "");
		// - Validate object
		assertTrue(aasEntry3.isValidID());
		assertFalse(aasEntry3.hasLegalEntity());
		assertFalse(aasEntry3.hasSubUnit());
		assertFalse(aasEntry3.hasSubModel());
		assertFalse(aasEntry3.hasVersion());
		assertFalse(aasEntry3.hasRevision());

		
		// Test object with ID, SubUnit, version and revision
		AASDirectoryEntry aasEntry4 = new AASDirectoryEntry("urn:FHG:iese:aas:0.97:1", "<serializedAAS>", "local", "");
		// - Validate object
		assertTrue(aasEntry4.isValidID());
		assertTrue(aasEntry4.getLegalEntity().equals("FHG"));
		assertTrue(aasEntry4.isLegalEntityOf("FHG"));
		assertTrue(aasEntry4.hasSubUnit());
		assertTrue(aasEntry4.getSubUnit().equals("iese"));
		assertTrue(aasEntry4.hasSubModel());
		assertTrue(aasEntry4.getSubModel().equals("aas"));
		assertTrue(aasEntry4.hasVersion());
		assertTrue(aasEntry4.getVersion().equals("0.97"));
		assertTrue(aasEntry4.hasRevision());
		assertTrue(aasEntry4.getRevision().equals("1"));
		assertFalse(aasEntry4.hasElementID());

		
		// Test object with ID, SubUnit, version and revision
		AASDirectoryEntry aasEntry5 = new AASDirectoryEntry("urn:FHG:iese:aas:0.97:1:entity", "<serializedAAS>", "local", "");
		// - Validate object
		assertTrue(aasEntry5.isValidID());
		assertTrue(aasEntry5.getLegalEntity().equals("FHG"));
		assertTrue(aasEntry5.isLegalEntityOf("FHG"));
		assertTrue(aasEntry5.hasSubUnit());
		assertTrue(aasEntry5.getSubUnit().equals("iese"));
		assertTrue(aasEntry5.hasSubModel());
		assertTrue(aasEntry5.getSubModel().equals("aas"));
		assertTrue(aasEntry5.hasVersion());
		assertTrue(aasEntry5.getVersion().equals("0.97"));
		assertTrue(aasEntry5.hasRevision());
		assertTrue(aasEntry5.getRevision().equals("1"));
		assertTrue(aasEntry5.hasElementID());
		assertTrue(aasEntry5.getElementID().equals("entity"));

		
		// Test object with ID, SubUnit, version and revision
		AASDirectoryEntry aasEntry6 = new AASDirectoryEntry("urn:FHG:iese:aas:0.97:1:entity#001", "<serializedAAS>", "local", "");
		// - Validate object
		assertTrue(aasEntry6.isValidID());
		assertTrue(aasEntry6.getLegalEntity().equals("FHG"));
		assertTrue(aasEntry6.isLegalEntityOf("FHG"));
		assertTrue(aasEntry6.hasSubUnit());
		assertTrue(aasEntry6.getSubUnit().equals("iese"));
		assertTrue(aasEntry6.hasSubModel());
		assertTrue(aasEntry6.getSubModel().equals("aas"));
		assertTrue(aasEntry6.hasVersion());
		assertTrue(aasEntry6.getVersion().equals("0.97"));
		assertTrue(aasEntry6.hasRevision());
		assertTrue(aasEntry6.getRevision().equals("1"));
		assertTrue(aasEntry6.hasElementID());
		assertTrue(aasEntry6.getElementID().equals("entity"));
		assertTrue(aasEntry6.hasElementInstance());
		assertTrue(aasEntry6.getElementInstance().equals("001"));

		
		// Test object with ID, SubUnit, version and revision
		AASDirectoryEntry aasEntry7 = new AASDirectoryEntry("urn:FHG:iese:aas:0.97:1:#001", "<serializedAAS>", "local", "");
		// - Validate object
		assertTrue(aasEntry7.isValidID());
		assertTrue(aasEntry7.getLegalEntity().equals("FHG"));
		assertTrue(aasEntry7.isLegalEntityOf("FHG"));
		assertTrue(aasEntry7.hasSubUnit());
		assertTrue(aasEntry7.getSubUnit().equals("iese"));
		assertTrue(aasEntry7.hasSubModel());
		assertTrue(aasEntry7.getSubModel().equals("aas"));
		assertTrue(aasEntry7.hasVersion());
		assertTrue(aasEntry7.getVersion().equals("0.97"));
		assertTrue(aasEntry7.hasRevision());
		assertTrue(aasEntry7.getRevision().equals("1"));
		assertFalse(aasEntry7.hasElementID());
		assertTrue(aasEntry7.hasElementInstance());
		assertTrue(aasEntry7.getElementInstance().equals("001"));
	}

	
	
	/**
	 * Execute test case that tests parsing of content and content type constructor parameter
	 */
	@Test
	public void testConstuctorParameterContentAndContentType() {
		// Test object with local content and without tags
		AASDirectoryEntry aasEntry1 = new AASDirectoryEntry("urn:FHG:iese:aas:0.97:1:#001", "<serializedAAS>", "local", "");
		// - Validate object
		assertTrue(aasEntry1.getAASContent().equals("<serializedAAS>"));
		assertTrue(aasEntry1.getAASContentType() == AASDirectoryEntry.AAS_CONTENTTYPE_LOCAL);
		assertTrue(aasEntry1.getAASTags().isEmpty());
		
		
		// Test object with remote content and without tags
		AASDirectoryEntry aasEntry2 = new AASDirectoryEntry("urn:FHG:iese:aas:0.97:1:#001", "<serializedAAS>", "remote", "");
		// - Validate object
		assertTrue(aasEntry2.getAASContent().equals("<serializedAAS>"));
		assertTrue(aasEntry2.getAASContentType() == AASDirectoryEntry.AAS_CONTENTTYPE_REMOTE);
		assertTrue(aasEntry2.getAASTags().isEmpty());

		
		// Test object with remote content and without tags
		AASDirectoryEntry aasEntry3 = new AASDirectoryEntry("urn:FHG:iese:aas:0.97:1:#001", "<serializedAAS>", "ReMote", "");
		// - Validate object
		assertTrue(aasEntry3.getAASContent().equals("<serializedAAS>"));
		assertTrue(aasEntry3.getAASContentType() == AASDirectoryEntry.AAS_CONTENTTYPE_REMOTE);
		assertTrue(aasEntry3.getAASTags().isEmpty());

		
		// Test object with proxy content and without tags
		AASDirectoryEntry aasEntry4 = new AASDirectoryEntry("urn:FHG:iese:aas:0.97:1:#001", "<serializedAAS>", "PROXY", "");
		// - Validate object
		assertTrue(aasEntry4.getAASContent().equals("<serializedAAS>"));
		assertTrue(aasEntry4.getAASContentType() == AASDirectoryEntry.AAS_CONTENTTYPE_PROXY);
		assertTrue(aasEntry4.getAASTags().isEmpty());
	}

	
	
	/**
	 * Execute test case that tests parsing of tags constructor parameter
	 */
	@Test
	public void testConstuctorParameterTags() {
		// Test object without tags
		AASDirectoryEntry aasEntry1 = new AASDirectoryEntry("urn:FHG:iese:aas:0.97:1:#001", "<serializedAAS>", "local", "");
		// - Validate object
		assertTrue(aasEntry1.getAASTags().isEmpty());
		
		
		// Test object with one tag
		AASDirectoryEntry aasEntry2 = new AASDirectoryEntry("urn:FHG:iese:aas:0.97:1:#001", "<serializedAAS>", "local", "tag1");
		// - Validate object
		assertTrue(aasEntry2.getAASTags().size() == 1);
		assertTrue(aasEntry2.getAASTags().toArray(new String[1])[0].equals("tag1"));

		
		// Test object with one tag
		AASDirectoryEntry aasEntry3 = new AASDirectoryEntry("urn:FHG:iese:aas:0.97:1:#001", "<serializedAAS>", "local", "tag1,tag2");
		// - Validate object
		assertTrue(aasEntry3.getAASTags().size() == 2);
		assertTrue(aasEntry3.getAASTags().toArray(new String[2])[0].equals("tag1"));
		assertTrue(aasEntry3.getAASTags().toArray(new String[2])[1].equals("tag2"));
	}

	
	
	/**
	 * Execute test case that tests invalid directory entries
	 */
	@Test
	public void testInvalidConstuctorParameter() {
		// Test object with minimal ID
		AASDirectoryEntry aasEntry1 = new AASDirectoryEntry("FHG", "<serializedAAS>", "local", "");
		// - Validate object
		assertFalse(aasEntry1.isValidID());
		assertFalse(aasEntry1.hasLegalEntity());
		assertFalse(aasEntry1.hasSubUnit());
		assertFalse(aasEntry1.hasSubModel());
	}
}

