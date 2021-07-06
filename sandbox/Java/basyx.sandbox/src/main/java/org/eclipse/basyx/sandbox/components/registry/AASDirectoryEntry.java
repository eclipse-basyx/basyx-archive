package org.eclipse.basyx.sandbox.components.registry;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.basyx.sandbox.components.registry.exception.AASDirectoryFormatException;



/**
 * Asset Administration Shell or sub model directory entry
 * 
 * IDs usually are formed as URIs: urn:<legalBody>:<SubUnit>:<Submodel>:<version>:<revision>:<elementID>#<instance>
 * 
 * @author kuhn
 *
 */
public class AASDirectoryEntry {

	
	/**
	 * Indicate undefined AAS content type
	 */
	public static final int AAS_CONTENTTYPE_UNDEFINED = -1;

	
	/**
	 * Indicate local AAS content type. The serialized AAS is stored in the content property of this class. 
	 */
	public static final int AAS_CONTENTTYPE_LOCAL = 1;

	
	/**
	 * Indicate remote AAS content type. The content property of this class contains a URL that points to the directory service. 
	 */
	public static final int AAS_CONTENTTYPE_REMOTE = 2;

	
	/**
	 * Indicate proxy AAS content type. The content property of this class contains an ID (specified as URI) that points to the
	 * AAS entry that all requests should be forwarded to. 
	 */
	public static final int AAS_CONTENTTYPE_PROXY = 3;

	
	
	
	
	/**
	 * Store the AAS ID
	 */
	protected String id = null;
	
	
	/**
	 * Store the AAS content
	 */
	protected String content = null;
	
	
	/**
	 * Store the AAS content type (local, remote, or proxy)
	 * 
	 * Local AAS content means that the AAS is serialized in the content property. Remote indicates
	 * that the content property contains a URL that points to the AAS. Proxy indicates that the
	 * content contains another AAS ID, to which requests will be forwarded. 
	 */
	protected int contentType = -1;
	
	
	/**
	 * Store the tags for this AAS
	 */
	protected HashSet<String> tags = new HashSet<>();
	
	
	
	
	
	/**
	 * Constructor
	 */
	public AASDirectoryEntry(String aasId, String aasContent, String aasContentType, String aasTags) {
		// Store AAS parameter
		id      = aasId;
		content = aasContent;
		
		
		// Try to process AAS content type
		try {
			// Store AAS content type
			switch(aasContentType.toLowerCase()) {
				case "local":  contentType = AAS_CONTENTTYPE_LOCAL; break;
				case "remote": contentType = AAS_CONTENTTYPE_REMOTE; break;
				case "proxy":  contentType = AAS_CONTENTTYPE_PROXY; break;

				default: throw new AASDirectoryFormatException("Unknown content type: "+aasContentType);
			}
		} catch (NullPointerException e) {
			// Assume local AAS for undefined AAS types
			contentType = AAS_CONTENTTYPE_LOCAL;
		}
		
		
		// Try to Store AAS tags
		try {
			String[] splitTags = aasTags.split(",");
			// - Only add non-empty tags
			for (String tag: splitTags) if (tag.trim().length() > 0) tags.add(tag.trim());
		} catch (NullPointerException e) {
			// Accept AAS without tags
		}		
	}
	
	
	/**
	 * Check if ID is a valid URI. A valid URI starts with "urn:" prefix and defines at least a legal body.
	 */
	public boolean isValidID() {
		return (id.startsWith("urn"));
	}

	
	/**
	 * Check if ID contains a legal entity
	 */
	public boolean hasLegalEntity() {
		// Split ID by ':' token
		String[] idParts = id.split(":");
		
		// Check if subunit is defined
		if (idParts.length < 2) return false;
		
		// Check if any information is contained in subunit field
		return (idParts[1].trim().length() > 0);
	}

	
	/**
	 * Return the legal entity of this AAS
	 */
	public String getLegalEntity() {
		// Split ID by ':' token
		String[] idParts = id.split(":");
		
		// Return legal Entity part
		return idParts[1];
	}
	
	
	/**
	 * Check if AAS legal entity is of type (ends with given suffix, e.g. ".fraunhofer.de")
	 */
	public boolean isLegalEntityOf(String suffix) {
		return getLegalEntity().endsWith(suffix);
	}
	
	
	/**
	 * Check if ID contains a subunit
	 */
	public boolean hasSubUnit() {
		// Split ID by ':' token
		String[] idParts = id.split(":");
		
		// Check if subunit is defined
		if (idParts.length < 3) return false;
		
		// Check if any information is contained in subunit field
		return (idParts[2].trim().length() > 0);
	}
	
	
	/**
	 * Get the subunit of the ID field
	 */
	public String getSubUnit() {
		// Split ID by ':' token
		String[] idParts = id.split(":");
		
		// Check if subunit is defined
		if (idParts.length < 3) return null;
		
		// Check if any information is contained in subunit field
		return idParts[2].trim();
	}

	
	/**
	 * Check if ID contains a sub model
	 */
	public boolean hasSubModel() {
		// Split ID by ':' token
		String[] idParts = id.split(":");
		
		// Check if subunit is defined
		if (idParts.length < 4) return false;
		
		// Check if any information is contained in subunit field
		return (idParts[3].trim().length() > 0);
	}
	
	
	/**
	 * Get the sub model of the ID field
	 */
	public String getSubModel() {
		// Split ID by ':' token
		String[] idParts = id.split(":");
		
		// Check if subunit is defined
		if (idParts.length < 4) return null;
		
		// Check if any information is contained in subunit field
		return idParts[3].trim();
	}

	
	/**
	 * Check if ID contains a version
	 */
	public boolean hasVersion() {
		// Split ID by ':' token
		String[] idParts = id.split(":");
		
		// Check if subunit is defined
		if (idParts.length < 5) return false;
		
		// Check if any information is contained in subunit field
		return (idParts[4].trim().length() > 0);
	}

	
	/**
	 * Get AAS version
	 */
	public String getVersion() {
		// Split ID by ':' token
		String[] idParts = id.split(":");
		
		// Check if subunit is defined
		if (idParts.length < 5) return null;
		
		// Check if any information is contained in subunit field
		return idParts[4].trim();
	}

	
	/**
	 * Check if ID contains a revision
	 */
	public boolean hasRevision() {
		// Split ID by ':' token
		String[] idParts = id.split(":");
		
		// Check if subunit is defined
		if (idParts.length < 6) return false;
		
		// Check if any information is contained in subunit field
		return (idParts[5].trim().length() > 0);
	}

	
	/**
	 * Get AAS revision
	 */
	public String getRevision() {
		// Split ID by ':' token
		String[] idParts = id.split(":");
		
		// Check if subunit is defined
		if (idParts.length < 6) return null;
		
		// Check if any information is contained in subunit field
		return idParts[5].trim();
	}
	
	
	/**
	 * Check if ID contains an element ID
	 */
	public boolean hasElementID() {
		// Split ID by ':' token
		String[] idParts = id.split(":");
		
		// Check if subunit is defined
		if (idParts.length < 7) return false;
		
		// Remove element instance if an instance is defined
		if (idParts[6].indexOf('#') > -1) idParts[6]=idParts[6].substring(0, idParts[6].indexOf('#'));
		// Check if any information is contained in subunit field
		return (idParts[6].trim().length() > 0);
	}

	
	/**
	 * Get element ID
	 */
	public String getElementID() {
		// Split ID by ':' token
		String[] idParts = id.split(":");
		
		// Check if subunit is defined
		if (idParts.length < 7) return null;
		
		// Remove element instance if an instance is defined
		if (idParts[6].indexOf('#') > -1) idParts[6]=idParts[6].substring(0, idParts[6].indexOf('#'));
		// Check if any information is contained in subunit field
		return idParts[6].trim();
	}

	
	/**
	 * Check if ID contains an instance
	 */
	public boolean hasElementInstance() {
		// Split ID by ':' token
		String[] idParts = id.split(":");
		
		// Check if subunit is defined
		if (idParts.length < 7) return false;
		
		// Remove element instance if an instance is defined
		if (idParts[6].indexOf('#') > -1) idParts[6]=idParts[6].substring(idParts[6].indexOf('#'));
		// Check if any information is contained in subunit field
		return (idParts[6].trim().length() > 0);
	}


	/**
	 * Get instance ID
	 */
	public String getElementInstance() {
		// Catch all exceptions 
		try {
			// Split ID by ':' token
			String[] idParts = id.split(":");

			// Check if subunit is defined
			if (idParts.length < 7) return null;

			// Remove element instance if an instance is defined
			if (idParts[6].indexOf('#') > -1) idParts[6]=idParts[6].substring(idParts[6].indexOf('#')+1);
			
			// Check if any information is contained in subunit field
			return idParts[6].trim();
		} catch (Exception e) {
			return null;
		}
	}
	
	
	/**
	 * Get AAS content
	 */
	public String getAASContent() {
		return content;
	}
	
	
	/**
	 * Get AAS content type
	 */
	public int getAASContentType() {
		return contentType;
	}
	
	
	/**
	 * Get AAS tags
	 */
	public Collection<String> getAASTags() {
		return tags;
	}
}
