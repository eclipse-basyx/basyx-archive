package org.eclipse.basyx.aas.impl.tools;


/**
 * Class that supports building IDs of asset administration shell IDs and sub models for the directory server <br>
 * 
 * Format:  {@code <aas>/<qualifier>/<submodel>/<qualifier>/<propertypath> } <br>
 * 
 * Examples: <br>
 * - status.lsw12.groundfloow.kaiserslautern.iese.fraunhofer.de (globally scoped - can be resolved via DNS)<br>
 * - status.lsw12                                               (local scope)
 * 
 * @author kuhn, pschorn
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
	 * @version 0.2
	 * 
	 * @param aasID ID of asset administration shell
	 * @return Built ID in format {@code <aasID>/aas}
	 * 
	 * 
	 */
	public String buildAASID(String aasID) {
	
		return aasID.endsWith("/aas") ? aasID : aasID + "/aas";
		
	}
	
	
	/**
	 * Create an ID string that refers to a single sub model for the directory server
	 * @version 0.2
	 * 
	 * @param smID ID of sub model
	 * 
	 * @return Built ID in format {@code <smID>/submodel} (mind the "s"!)
	 */
	public String buildSMID(String smID) {
		
		return smID.endsWith("/submodel") ? smID : smID + "/submodel";
	}
	
	
	/**
	 * Create an ID string for the directory server
	 * @version 0.2
	 * 
	 * @param aasID ID of asset administration shell
	 * @param subModelID ID of sub model
	 * 
	 * @return Built ID in format {@code <aasID>/aas} or {@code <smID>/submodel} or {@code <aasID>/aas/submodels/<subModelID> }
	 */
	public String buildPath(String aasID, String subModelID) {
		// Only return one id if other is is null or empty
		if ((aasID == null)      || (aasID.length()==0))      return buildSMID(subModelID);
		if ((subModelID == null) || (subModelID.length()==0)) return buildAASID(aasID);

		// Build path
		return aasID+ "/aas/submodels/" +subModelID; // return subModelID+"."+aasID;
	}
	
	/**
	 * Create default service path 
	 * @param qualifier refers to a qualifier "properties", "operations" or "events"
	 * @param path can be null if a type qualifier
	 */
	public String buildPath(String aasID, String aasSubmodelID, String path, String qualifier) {
		String servicePath = "";
		
		if (aasSubmodelID!=null && aasID != null) { 
			servicePath = buildAASID(aasID) + "/submodels/"+aasSubmodelID;		
		
		} else if (aasSubmodelID != null && aasID == null) {
			servicePath = aasSubmodelID +"/submodel"; // handles single submodel case
		}
		
		if (qualifier!=null) 	 servicePath = servicePath + "/" + qualifier;
		if (path!=null)      	 servicePath = servicePath + "/" + path;
		
		return servicePath;
	}
	
	
	/**
	 * Create a scoped ID string for the directory server. The scope will be reversed so that top level scope comes last.
	 * @version 0.2
	 * 
	 * @param scope sub model scope as a string array
	 * @param aasID ID of asset administration shell
	 * @param subModelID ID of sub model
	 * 
	 * @return Build ID in format {@code <scope1>.<scope2>. ... .<scopeN>/<aasID>/aas/submodels/<subModelID> } where scope1 is the topscope
	 */
	public String buildPath(String[] scope, String aasID, String subModelID) {
		// Support building the result string
		StringBuilder result = new StringBuilder();
		
		// Append reversed scope
		for (int i=0; i<scope.length; i++) result.append("."+scope[i]);
		
		// Build sub model and AAS part
		result.append("/" + buildAASID(aasID) + "/submodels/" + subModelID);
		
		// Return build ID
		return result.toString();
	}

	
	/**
	 * Create a scoped ID string for the directory server. The scope will be reversed so that top level scope comes last.
	 * @version 0.2
	 * 
	 * @param scope sub model scope
	 * @param subModelID ID of sub model
	 * 
	 * @return Built ID in format {@code <scope1>.<scope2>.....<scopeN>/<subModelID>/submodel } where scope1 is the topscope
	 */
	public String buildPath(String[] scope, String subModelID) {
		// Support building the result string
		StringBuilder result = new StringBuilder();
		
		// Append reversed scope 
		for (int i=0; i<scope.length; i++) result.append("/"+scope[i]);
		
		// Build sub model part
		result.append("/" + buildSMID(subModelID)); 
		
		// Return build ID
		return result.toString();
	}

	
	
	
	/**
	 * <pre>
	 *   Get aas id from a qualified path that may contain scope. Handle the following cases <br> Truncates scope!
	 *  @version 0.2
	 *  @return "" or aasID and scope if available
	 *  @param path has format <br>
	 *  (1) {@code <aasID>/aas } or <br>
	 *  (2) {@code <aasID>/aas/submodels } or <br>
	 *  (3) {@code <aasID>/aas/submodels/<submodelID> } or <br>
	 *  (4) {@code <aasID>/aas/submodels/<submodelID>/... } or <br>
	 *  (5) {@code <submodelID>/submodel/... } 			
	 */
	public String getAASID(String path) {
		
		String[] splitted = path.split("/");
		
		// (1-4) search for aas id
		for (int i=1;i<splitted.length;i++) {
			
			// Search for <aasID>/aas pattern and return preceding id (and scope)
			if (splitted[i].equals("aas")) return splitted[i-1];
		}
		// (5) A submodelID gets processed
		return "";
		
		
	}

	
	
	/**
	 * Takes the possibly scoped address and returns the service path starting from it
	 * @param path {@code <scope1>...<scopeN>/<aasID>/<qualifiers> or <scope1>...<scopeN>/<submodelID>/<qualifiers>}
	 * @param address is the scoped aasId or submodel
 	 * @return {@code scope/<aasID>/<qualifiers> or scope/<submodelID>/<qualifiers>}
	 */
	public String getScopedServicePath(String path, String address) {
		
		return path.substring(path.indexOf(address));
	}
	
	
	
	/**
	 * Return un-scoped service path
	 * @param path
	 * @return the service path without the scope
	 */
	public String getUnScopedServicePath(String path) {
		String aasID = getAASID(path);
		String submodelID = getSubmodelID(path);
		
		if (aasID != null) return aasID + path.substring(path.indexOf("/aas")); 
		if (submodelID != null) return aasID + path.substring(path.indexOf("/submodel"));
		return ""; 
	}

	

	/**
	 *  <pre>
	 *   Get sub model id from a qualified path that my contain scope. Handle the following cases <br>
	 *   @version 0.2
	 *  @return "" or submodelID if available
	 *  @param path has format <br>
	 *  (1) {@code <aasID>/aas } or <br>
	 *  (2) {@code <aasID>/aas/submodels } or <br>
	 *  (3) {@code <aasID>/aas/submodels/<submodelID> } or <br>
	 *  (4) {@code <aasID>/aas/submodels/<submodelID>/... } or <br>
	 *  (5) {@code <submodelID>/submodel/... } 						 	 
	 * 
	 */
	public String getSubmodelID(String path) {
		
		String[] splitted = path.split("/");
		
		// Search for submodel ID
		for (int i=1;i<splitted.length;i++) {
			
			// (5) search for submodel identifier and return preceding id
			if (splitted[i].equals("submodel")) return splitted[i-1];
			
			// Case (1-4) search for /aas/submodels/<submodelID> pattern and return <submodelID>
			else if (splitted[i].equals("aas") && splitted.length>i+2 && splitted[i+1].equals("submodels")) return splitted[i+2];
			
		}
		// Submodel Identifier not found.
		return "";
		
	}


	/**
	 * <pre>
	 * Get qualified element ID or qualifier from path that my contain scope. Handle the following cases <br>
	 * @version 0.2
	 * @return If an AAS or Submodel is requested, return ""; otherwise, return qualifier or element ID
	 * @param path has format <br>
	 * (1) {@code <aasID>/aas/submodels } <br> - return "submodels" identifier
	 * (1.1) {@code <aasID>/aas/children  -> } return "children" identifier
	 * (2) {@code <aasID>/aas/submodels/<submodelID>/properties} <br>
	 * (3) {@code <aasID>/aas/submodels/<submodelID>/operations} <br>
	 * (4) {@code <aasID>/aas/submodels/<submodelID>/events} <br>
	 * (5) {@code <aasID>/aas/submodels/<submodelID>/properties/<propertyID> -> Returns the property ID }<br>
	 * (6) {@code <aasID>/aas/submodels/<submodelID>/operations/<operationID> -> Returns the operation ID }<br>
	 * (7) {@code <aasID>/aas/submodels/<submodelID>/events/<eventID> -> Returns the event ID} <br>
	 * (X) {@code <submodelID>/submodel
	 * (8) {@code <submodelID>/submodel/properties }<br>
	 * (9) {@code <submodelID>/submodel/operations }<br>
	 * (10) {@code <submodelID>/submodel/events} <br>
	 * (11) {@code <submodelID>/submodel/properties/<propertyID> }<br>
	 * (12) {@code <submodelID>/submodel/operations/<operationID> }<br>
	 * (13) {@code <submodelID>/submodel/events/<eventID>} <br>
	 * 
	 * TODO add frozen and clock
	 * 
	 */
	public String getPath(String path) {
		
		String[] splitted = path.split("/");
		
		// Search for element ID or qualifier. Start with Index 1 because first identifier is always element ID
		for (int i = 1; i < splitted.length; i++) {
			
			// Handle cases (8 - 13)
			if (splitted[i].equals("submodel")) {
				
				// Handle case (11 - 13)
				if (splitted.length == i+3 ) return splitted[i+2];
				
				// Handle case (8 - 10)
				if (splitted.length == i+2) return splitted[i+1];
				
			}
			
			// Handle cases (1 - 7)
			if (splitted[i].equals("submodels")) {
				
				// Handle case (5 - 7)
				if (splitted.length == i+4) return splitted[i+3];
				
				// Handle case (2 - 4) 
				if (splitted.length == i+3 ) return splitted[i+2];
					
				// Handle case (1)
				if (splitted.length == i+1) return "submodels";
			}
			
			// Handle case 1.1
			if (splitted[i].equals("children")) {
				return "children";
			}
		}
		
		// If an AAS or Submodel is requested, return "". Also handles Case (X).
		return "";
	}
	
	
	/**
	 * Split a property path to extract next property id TODO check compatible with version 0.2
	 */
	private String[] splitPropertyPath(String pathString) {
		
		// Return empty array for empty string
		if (pathString.length() == 0) return new String[0];
		
		// Process paths that have no splitting character
		if ((pathString.indexOf(".") == -1)) return new String[] {pathString};

		// Split string into path segments
		return pathString.split("[/\\.]");
	}

	
	/**
	 * Get the last n path entries of a path. Used for identifying nested operations.
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
	 * Get scoped, qualified address (submodel ID and AAS ID)
	 * {@code example input: Testsuite/GW/IESE/line1/gateway_line12/device2.line2.manufacturing.de/aas }
	 * TODO check compatible with version 0.2
	 */
	public String getAddress(String path) {
		
		System.out.println("getAddress " +path);
		
		return buildPath(getAASID(path), getSubmodelID(path));
		
		/*int    offset = 0;

		// Remove everything but address if a path component is present in string
		if ((offset = path.indexOf('/')) > -1) return path.substring(0, offset);
		
		// Path has no address component
		return "";*/
	}





	/**
	 * Add Scope to an AAS ID
	 * @param aasID
	 * @param scope
	 * @return
	 */
	public String addScope(String aasID, String scope) {
		
		// Case that aasID has other scope
		if (aasID.indexOf("/") != -1 ) return scope +"."+ aasID;
		
		// Otherwise
		return scope + "/" + aasID;
	}




	/**
	 * Expects '{aasId}/aas' and return '{aasId}/aas/submodels'
	 * @param id
	 * @return
	 */
	public String buildSubmodelsQuery(String id) {
		return id + "/submodels";
	}



	
	/**
	 *  Expects '{aasId}/aas/submodels/{smId}' and returns '{aasId}/aas/submodels/{smId}/{type}'
	 * @param id
	 * @param type
	 * @return
	 */
	public String buildQualifiedQuery(String id, String type) {
		return id + "/" + type;
	}	
}


