package org.eclipse.basyx.aas.impl.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.platform.commons.util.StringUtils;

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
		return aasID+ "/submodels/" +subModelID;
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
	 * Get unqualified AAS id from a qualified path <aasID>.<qualifier>/<scope> => <aasID> // EDIT <aasID>/<qualifier> => <aasID>
	 */
	public String getAASID(String path) {
		
		String[] pathArray = path.split("/", 2);
		
		return pathArray[0];
				
				
		//String result = path;
		//int    offset = 0;
		
		//System.out.println("Path="+path);
		
		// Check if path contains an AAS part // EDIT Path always contains AAS part except for registry and component API
		//if (path.indexOf(".") == -1) return "";
		
		// Remove everything before first '.' // EDIT Parse everything before first '/', handle case that there is no '/' then only return path
		//result = path.substring(0, path.indexOf('/') != -1? path.indexOf('/') : path.length());
		//if ((offset = result.indexOf('/')) > -1) result = result.substring(0, offset);

		// Remove scope (everything after first '.') // EDIT not relevant for aasID
		//if (result.indexOf(".") > -1) result=result.substring(0, result.indexOf("."));

		//System.out.println("AAS ID="+result);

		// Return AAS ID
		//return result;
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
	 * Get sub model id from a qualified path. Handle the following cases
	 * - <aasID> -> Return the AAS
	 * - <aasID>/submodels -> Return all Submodels of this AAS
	 * - <aasID>/submodels/<submodelID> -> Return Submodel of the AAS
	 * - <aasID>/submodels/<submodelID>/properties -> Return all properties of this Submodel
	 */
	public String getSubmodelID(String path) {
		
		String aasID = this.getAASID(path);
		
		// Remove aas part
		String withoutAAS = path.substring(aasID.length()); 
		
		// If there is no remainder or remainder equals "submodels" returrn empty string
		if (withoutAAS.equals("/submodels") || withoutAAS.isEmpty()) return "";
	
		withoutAAS = withoutAAS.substring("/submodels/".length()); // remove '/submodels/'
		
		// Remove path qualifiers. if only submodel is requested, handle case that there is no '/' at the end
		String result = withoutAAS.substring(0, (withoutAAS.indexOf('/') != -1? withoutAAS.indexOf('/') : withoutAAS.length()));
		
		// Path only defines the sub model
		return result;
	}


	/**
	 * Get qualified path to an object or qualifier. Handle cases
	 * - <aasID>/submodels/<submodelID>/properties
	 * - <aasID>/submodels/<submodelID>/operations
	 * - <aasID>/submodels/<submodelID>/events
	 * - <aasID>/submodels/<submodelID>/properties/<propertyID> -> Returns the property
	 * - <aasID>/submodels/<submodelID>/operations/<operationID> -> Returns the operation
	 * - <aasID>/submodels/<submodelID>/events/<eventID> -> Returns the event
	 *
	 */
	public String getPath(String path) {
		
		String aasID = this.getAASID(path);
		String submodelID = this.getSubmodelID(path);
		String prefix1 = aasID+"/submodels";
		String prefix2 = aasID+"/submodels/"+submodelID+"/properties";
		String prefix3 = aasID+"/submodels/"+submodelID+"/operations";
		String prefix4 = aasID+"/submodels/"+submodelID+"/events";
		String prefix5 = aasID+"/submodels/"+submodelID+"/frozen";
		String prefix6 = aasID+"/submodels/"+submodelID+"/clock";
		
		
		if (path.equals(prefix1)) {
			
			// Returns path qualifier 'submodels'
			return "submodels";
		} else if (path.equals(prefix2) || path.equals(prefix3) || path.equals(prefix4) || path.equals(prefix5) || path.equals(prefix6)) {
			
			// Returns path qualifiers 'properties' | 'operations' | 'events'
			return path.substring((aasID+"/submodels/"+submodelID+"/").length());
	
		} else if (path.startsWith(prefix2)) {
			
			// Returns propertyID
			return path.substring((prefix2+"/").length());
		} else if (path.startsWith(prefix3)) {
			
			// Returns operationID
			return path.substring((prefix3+"/").length());
		} else if (path.startsWith(prefix4)) {
			
			// Returns eventID
			return path.substring((prefix4+"/").length());
		
		} else {
			
			// Returns empty string if the path does not contain a quantifier or propertyID
			return "";
		}
		
		/*
		if (path.indexOf('/') > -1) {
			
			String withoutAAS = path.substring(aasID.length() + 1);
			
			if (withoutAAS.equals("submodels")) {
				// return 'submodels'
				return withoutAAS;
			} else if (withoutAAS.indexOf('/') > -1){
				// return <qualifier>
				return withoutAAS.substring(withoutAAS.indexOf('/') + 1); // +1 for '/'
			}
			else {
				return "";
			}
		}*/
		
		// Path has no object or qualifier component
		//return "";
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
	 * Get the last element of path that identifies the object or qualifier
	 * Example: <aasID>/submodels/<submodelID>/properties/<propertyID> => <propertyID>
	 */
	public String getIdentifier(String path) {
		
		// Try to get property/operation path
		String   propPath   = BaSysID.instance.getPath(path);
		String   objectId   = null;
		
		// - If a path is given, return last path entry
		if (propPath.length() > 0) {
			// Return last path element
			String[] pathArray  = splitPropertyPath(propPath);

			return pathArray[pathArray.length-1];
		}
		
		// Try to get sub model ID or AAS ID
		if ((objectId = getSubmodelID(path)).length() > 0) return objectId; // EDIT do these ever match?
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


