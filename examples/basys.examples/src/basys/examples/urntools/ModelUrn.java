package basys.examples.urntools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Create URNs with the format urn:<legalEntity>:<subUnit>:<subModel>:<version>:<revision>:<elementID>#<elementInstance>
 * 
 * @author kuhn
 *
 */
public class ModelUrn {

	
	/**
	 * URN string
	 */
	protected String urnString = null;
	
	
	
	/**
	 * Constructor that accepts a single, raw URN
	 */
	public ModelUrn(String rawURN) {
		urnString = rawURN;
	}
	
	
	/**
	 * Constructor that build a URN
	 */
	public ModelUrn(String legalEntity, String subUnit, String subModel, String version, String revision, String elementId, String elementInstance) {
		// Goal is: urn:<legalEntity>:<subUnit>:<subModel>:<version>:<revision>:<elementID>#<elementInstance>
		StringBuffer urnBuilder = new StringBuffer();
		
		// Start with header
		urnBuilder.append("urn:");
		// - Add URN components until instance
		if (legalEntity != null) urnBuilder.append(legalEntity); urnBuilder.append(":");
		if (subUnit != null)     urnBuilder.append(subUnit);     urnBuilder.append(":");
		if (subModel != null)    urnBuilder.append(subModel);    urnBuilder.append(":");
		if (version != null)     urnBuilder.append(version);     urnBuilder.append(":");
		if (revision != null)    urnBuilder.append(revision);    urnBuilder.append(":");
		if (elementId != null)   urnBuilder.append(elementId); 
		// - Add element instance, prefix with '#'
		if (elementInstance != null) urnBuilder.append("#"+elementInstance);
		
		// Build URN
		urnString = urnBuilder.toString();
	}
	
	
	
	/**
	 * Get URN as string
	 */
	public String getURN() {
		return urnString;
	}
	
	
	/**
	 * Get URL encoded URN as string
	 */
	public String getEncodedURN() {
		try {
			// Try to encode urn string
			return URLEncoder.encode(urnString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// Catch block
			e.printStackTrace(); return null;
		}
	}
}
