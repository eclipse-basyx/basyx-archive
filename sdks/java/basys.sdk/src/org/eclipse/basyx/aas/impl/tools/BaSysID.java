package org.eclipse.basyx.aas.impl.tools;


/**
 * Class that supports building IDs of asset administration shell IDs and sub models for the directory server
 * 
 * Format:  <submodel>.<aas>/<propertypath>
 * 
 * Examples: 
 * - status.lsw12.groundfloow.kaiserslautern.iese.fraunhofer.de (globally scoped - can be resolved via DNS)
 * - status.lsw12                                               (local scope)
 * 
 * @author kuhn
 *
 */
public class BaSysID {

	
	/**
	 * Singleton BaSys ID instance 
	 */
	public static final BaSysID instance = new BaSysID();
	
	
	
	/**
	 * Prevent additional instances
	 */
	private BaSysID() {
		// Do nothing
	}
	
	
	
	
	
	/**
	 * Create an ID string that refers to an Asset Administration Shell for the directory server
	 * 
	 * @param aasID ID of asset administration shell
	 * 
	 * @return Built ID in format <aasID>
	 */
	public String buildAASID(String aasID) {
		if (aasID.startsWith("aas.")) return aasID; else return "aas."+aasID;
	}
	
	
	/**
	 * Create an ID string that refers to a single sub model for the directory server
	 * 
	 * @param smID ID of sub model
	 * 
	 * @return Built ID in format <smID>
	 */
	public String buildSMID(String smID) {
		return smID;
	}
	
	
	/**
	 * Create an ID string for the directory server
	 * 
	 * @param aasID ID of asset administration shell
	 * @param subModelID ID of sub model
	 * 
	 * @return Built ID in format <subModelID>.<aasID>
	 */
	public String buildPath(String aasID, String subModelID) {
		// Only return one id if other is is null or empty
		if ((aasID == null)      || (aasID.length()==0))      return buildSMID(subModelID);
		if ((subModelID == null) || (subModelID.length()==0)) return buildAASID(aasID);

		// Build path, remove leading "aas." from AAS because a sub model ID is given
		if (aasID.startsWith("aas.")) aasID = aasID.substring(4);
		
		// Build path
		return subModelID+"."+aasID;
	}
	
	
	/**
	 * Create a scoped ID string for the directory server. The scope will be reversed so that top level scope comes last.
	 * 
	 * @param scope sub model scope
	 * @param aasID ID of asset administration shell
	 * @param subModelID ID of sub model
	 * 
	 * @return Built ID in format <subModelID>.<aasID>/<scope>
	 */
	public String buildPath(String[] scope, String aasID, String subModelID) {
		// Support building the result string
		StringBuilder result = new StringBuilder();
		
		// Remove leading "aas." from AAS because a sub model ID is given
		if (aasID.startsWith("aas.")) aasID = aasID.substring(4);

		// Build sub model and AAS part
		result.append(subModelID); result.append(".");
		result.append(aasID);
		
		// Append reversed scope
		for (int i=0; i<scope.length; i++) result.append("/"+scope[i]);
		
		// Return build ID
		return result.toString();
	}

	
	/**
	 * Create a scoped ID string for the directory server. The scope will be reversed so that top level scope comes last.
	 * 
	 * @param scope sub model scope
	 * @param subModelID ID of sub model
	 * 
	 * @return Built ID in format <subModelID>.<aasID>/<scope>
	 */
	public String buildPath(String[] scope, String subModelID) {
		// Support building the result string
		StringBuilder result = new StringBuilder();
		
		// Build sub model part
		result.append(subModelID); 
		
		// Append reversed scope
		for (int i=0; i<scope.length; i++) result.append("/"+scope[i]);
		
		// Return build ID
		return result.toString();
	}

	
	
	
	/**
	 * Get unqualified AAS id from a qualified path <subModelID>.<aasID>.<qualifier>/<scope> => <aasID>
	 */
	public String getAASID(String path) {
		String result = path;
		int    offset = 0;
		
		System.out.println("Getting 2:"+path);
		
		// Check if path contains an AAS part
		if (path.indexOf(".") == -1) return "";
		
		// Remove everything before first '.'
		result = path.substring(path.indexOf('.')+1);
		if ((offset = result.indexOf('/')) > -1) result = result.substring(0, offset);

		// Remove scope (everything after first '.')
		if (result.indexOf(".") > -1) result=result.substring(0, result.indexOf("."));

		System.out.println("Getting 3:"+result);

		// Return AAS ID
		return result;
	}

	
	
	/**
	 * Get qualified AAS id from a qualified path <subModelID>.<aasID>.<qualifier>/<scope> => <aasID>
	 */
	public String getQualifiedAASID(String path) {
		String result = path;
		int    offset = 0;
		
		System.out.println("Getting 2:"+path);
		
		// Check if path contains an AAS part
		if (path.indexOf(".") == -1) return "";
		
		// Remove everything before first '.'
		result = path.substring(path.indexOf('.')+1);
		if ((offset = result.indexOf('/')) > -1) result = result.substring(0, offset);

		System.out.println("Getting 3:"+result);

		// Return AAS ID
		return result;
	}

	

	/**
	 * Get sub model id from a qualified path <subModelID>.<aasID>/<scope>
	 */
	public String getSubmodelID(String path) {
		int    offset = 0;

		// "aas." is not a sub model
		if (path.startsWith("aas.")) return "";

		// Extract sub model ID
		if ((offset = path.indexOf('.')) > -1) return path.substring(0, offset);		
		if ((offset = path.indexOf('/')) > -1) return path.substring(0, offset);
		
		// Path only defines the sub model
		return path;
	}


	/**
	 * Get qualified path to AAS
	 */
	public String getPath(String path) {
		int    offset = 0;

		// Remove everything but path if a path component is present in string
		if ((offset = path.indexOf('/')) > -1) return path.substring(offset+1);
		
		// Path has no path component
		return "";
	}
	
	
	/**
	 * Split a property path
	 */
	protected String[] splitPropertyPath(String pathString) {
		// Return empty array for empty string
		if (pathString.length() == 0) return new String[0];
		
		// Process paths that have no splitting character
		if ((pathString.indexOf("/") == -1) && (pathString.indexOf(".") == -1)) return new String[] {pathString};

		// Split string into path segments
		return pathString.split("[/\\.]");
	}

	
	/**
	 * Get the last n path entries of a path
	 */
	public String[] getLastPathEntries(String path, int lastEntries) {
		// Return result
		String[] result = new String[lastEntries];

		// Temporary variables
		String   propPath   = BaSysID.instance.getPath(path);
		String[] pathArray  = splitPropertyPath(propPath);

		// Copy requested path elements
		for (int i=0; i<lastEntries; i++) result[i]=pathArray[pathArray.length-lastEntries+i];

		// Return path
		return result;
	}


	/**
	 * Get the last element of path that identifies the object
	 */
	public String getIdentifier(String path) {
		// Try to get path
		String   propPath   = BaSysID.instance.getPath(path);
		String   objectId   = null;
		
		// - If a path is given, return last path entry
		if (propPath.length() > 0) {
			// Return last path element
			String[] pathArray  = splitPropertyPath(propPath);

			return pathArray[pathArray.length-1];
		}
		
		// Try to get sub model ID or AAS ID
		if ((objectId = getSubmodelID(path)).length() > 0) return objectId;
		if ((objectId = getAASID(path)).length() > 0) return objectId;
		
		// No identifier given
		return null;
	}
	
	
	/**
	 * Get qualified address (submodel ID and AAS ID)
	 */
	public String getAddress(String path) {
		int    offset = 0;

		// Remove everything but address if a path component is present in string
		if ((offset = path.indexOf('/')) > -1) return path.substring(0, offset);
		
		// Path has no address component
		return "";
	}	
}


