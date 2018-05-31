/*
 * BaSysID.h
 *
 * Class that supports building IDs of asset administration shell IDs and sub models for the directory server
 *
 * Format:  <submodel>.<aas>/<propertypath>
 *
 * Examples:
 * - status.lsw12.groundfloow.kaiserslautern.iese.fraunhofer.de (globally scoped - can be resolved via DNS)
 * - status.lsw12                                               (local scope)
 *
 *      Author: kuhn
 */

#ifndef BASYSID_BASYSID_H_
#define BASYSID_BASYSID_H_


/* ************************************************
 * Includes
 * ************************************************/

// StdC++ library
#include <string>
#include <list>

// C headers
#include <string.h>



/* ************************************************
 * Class declaration
 * ************************************************/
class BaSysID {


	// Support functions
	public:

		/**
		 * Create an ID string that refers to an Asset Administration Shell for the directory server
		 *
		 * @param aasID ID of asset administration shell
		 *
		 * @return Built ID in format <aasID>
		 */
		static std::string buildAASID(std::string aasID) {
			if (aasID.find("aas.") == 0) return aasID; else return "aas."+aasID;
		}


		/**
		 * Create an ID string that refers to a single sub model for the directory server
		 *
		 * @param smID ID of sub model
		 *
		 * @return Built ID in format <smID>
		 */
		static std::string buildSMID(std::string smID) {
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
		static std::string buildPath(std::string aasID, std::string subModelID) {
			// Only return one id if other is is null or empty
			if ((aasID.empty())      || (aasID.length()==0))      return buildSMID(subModelID);
			if ((subModelID.empty()) || (subModelID.length()==0)) return buildAASID(aasID);

			// Build path, remove leading "aas." from AAS because a sub model ID is given
			if (aasID.find("aas.") == 0) aasID = aasID.substr(4);

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
		static std::string buildPath(std::string scope[], int scopeCount, std::string aasID, std::string subModelID) {
			// Support building the result string
			std::string result;

			// Remove leading "aas." from AAS because a sub model ID is given
			if (aasID.find("aas.") == 0) aasID = aasID.substr(4);

			// Build sub model and AAS part
			result.append(subModelID); result.append(".");
			result.append(aasID);

			// Append reversed scope
			for (int i=0; i<scopeCount; i++) result.append("/"+scope[i]);

			// Return build ID
			return result;
		}


		/**
		 * Create a scoped ID string for the directory server. The scope will be reversed so that top level scope comes last.
		 *
		 * @param scope sub model scope
		 * @param subModelID ID of sub model
		 *
		 * @return Built ID in format <subModelID>.<aasID>/<scope>
		 */
		static std::string buildPath(std::string scope[], int scopeCount, std::string subModelID) {
			// Support building the result string
			std::string result;

			// Build sub model part
			result.append(subModelID);

			// Append reversed scope
			for (int i=0; i<scopeCount; i++) result.append("/"+scope[i]);

			// Return build ID
			return result;
		}


		/**
		 * Get unqualified AAS id from a qualified path <subModelID>.<aasID>.<qualifier>/<scope> => <aasID>
		 */
		static std::string getAASID(std::string path) {
			std::string result = path;
			int         offset = 0;

			// Check if path contains an AAS part
			if (path.find_first_of(".") == std::string::npos) return "";

			// Remove everything before first '.'
			result = path.substr(path.find_first_of('.')+1);
			if ((offset = result.find_first_of('/')) != std::string::npos) result = result.substr(0, offset);

			// Remove scope (everything after first '.')
			if (result.find_first_of(".") != std::string::npos) result=result.substr(0, result.find_first_of("."));

			// Return AAS ID
			return result;
		}


		/**
		 * Get qualified AAS id from a qualified path <subModelID>.<aasID>.<qualifier>/<scope> => <aasID>
		 */
		static std::string getQualifiedAASID(std::string path) {
			std::string result = path;
			int         offset = 0;

			// Check if path contains an AAS part
			if (path.find_first_of(".") == std::string::npos) return "";

			// Remove everything before first '.'
			result = path.substr(path.find_first_of('.')+1);
			if ((offset = result.find_first_of('/')) != std::string::npos) result = result.substr(0, offset);

			// Return AAS ID
			return result;
		}


		/**
		 * Get sub model id from a qualified path <subModelID>.<aasID>/<scope>
		 */
		static std::string getSubmodelID(std::string path) {
			int    offset = 0;

			// "aas." is not a sub model
			if (path.find("aas.") == 0) return "";

			// Extract sub model ID
			if ((offset = path.find_first_of('.')) != std::string::npos) return path.substr(0, offset);
			if ((offset = path.find_first_of('/')) != std::string::npos) return path.substr(0, offset);

			// Path only defines the sub model
			return path;
		}


		/**
		 * Get qualified path to AAS
		 */
		static std::string getPath(std::string path) {
			int    offset = 0;

			// Remove everything but path if a path component is present in string
			if ((offset = path.find_first_of('/')) != std::string::npos) return path.substr(offset+1);

			// Path has no path component
			return "";
		}


		/**
		 * Split a property path
		 */
		static std::list<std::string> splitPropertyPath(std::string pathString) {
			// Result
			std::list<std::string> result;

			// Return empty array for empty string
			if (pathString.length() == 0) return result;

			// Process paths that have no splitting character
			if ((pathString.find_first_of("/") == std::string::npos) && (pathString.find_first_of(".") == std::string::npos)) {
				result.push_back(pathString);
				return result;
			}

			// Get path segments
			// - Copy String
			char *copy  = strdup(pathString.c_str());
			// - Get next token
		    char *token = strtok(copy, "/\\.");
		    // - Repeat until no token is left
		    while(token != NULL) {
		    	// Add token
		    	result.push_back(std::string(token));
		        // Get next token
		        token = strtok(NULL, "/\\.");
		    }
		    // - Free copy of input string
		    free(copy);

			// Return path segments
			return result;
		}


		/**
		 * Get the last n path entries of a path
		 */
		static std::list<std::string> getLastPathEntries(std::string path, int lastEntries) {
			// Return result
			std::list<std::string> result;

			// Temporary variables
			std::string             propPath   = getPath(path);
			std::list<std::string>  pathArray  = splitPropertyPath(propPath);

			// Copy requested path elements
		    std::list<std::string>::iterator it = pathArray.begin();
		    // - Advance iterator
		    std::advance(it, pathArray.size()-lastEntries);
		    // - Copy elements
			for (int i=0; i<lastEntries; i++) {result.push_back(*it); std::advance(it, 1);}

			// Return path segments
			return result;
		}


		/**
		 * Get the last element of path that identifies the object
		 */
		static std::string getIdentifier(std::string path) {
			// Try to get path
			std::string   propPath   = getPath(path);
			std::string   objectId   = "";

			// If a path is given, return last path entry
			if (propPath.length() > 0) {
				// Return last path element
				std::list<std::string>  pathArray  = splitPropertyPath(propPath);

				return pathArray.back();
			}

			// Try to get sub model ID or AAS ID
			if ((objectId = getSubmodelID(path)).length() > 0) return objectId;
			if ((objectId = getAASID(path)).length() > 0) return objectId;

			// No identifier given
			return "";
		}


		/**
		 * Get qualified address (submodel ID and AAS ID)
		 */
		static std::string getAddress(std::string path) {
			int    offset = 0;

			// Remove everything but address if a path component is present in string
			if ((offset = path.find_first_of('/')) != std::string::npos) return path.substr(0, offset);

			// Path has no address component
			return "";
		}


		/**
		 * Get IElement ID
		 *
		 * - aas.<aasid>                        --> <aasid>
		 * - aas.<aasid>.<scope>                --> <aasid>
		 * - <submodel>.<aasid>                 --> <submodel>
		 * - <submodel>.<aasid>.<scope>         --> <submodel>
		 * - aas.<aasid>/<path>                 --> <aasid>
		 * - aas.<aasid>.<scope>/<path>         --> <aasid>
		 * - <submodel>.<aasid>/<path>          --> <submodel>
		 * - <submodel>.<aasid>.<scope>/<path>  --> <submodel>
		 */
		static std::string getElementID(std::string path) {
			int    offset = 0;

			// Remove leading "aas." from path
			if (path.find("aas.") == 0) path = path.substr(4);

			// Extract unscoped ID
			if ((offset = path.find_first_of('.')) != std::string::npos) path = path.substr(0, offset);

			// Remove everything but address if a path component is present in string
			if ((offset = path.find_first_of('/')) != std::string::npos) path = path.substr(0, offset);

			// Return processed path
			return path;
		}


		/**
		 * Get qualified IElement ID
		 *
		 * - aas.<aasid>                        --> <aasid>
		 * - aas.<aasid>.<scope>                --> <aasid>.<scope>
		 * - <aubmodel>.<aasid>                 --> <submodel>
		 * - <aubmodel>.<aasid>.<scope>         --> <submodel>.<scope>
		 * - aas.<aasid>/<path>                 --> <aasid>
		 * - aas.<aasid>.<scope>/<path>         --> <aasid>.<scope>
		 * - <submodel>.<aasid>/<path>          --> <submodel>
		 * - <submodel>.<aasid>.<scope>/<path>  --> <submodel>.<scope>
		 */
		static std::string getQualifiedElementID(std::string path) {
			int    offset = 0;

			// Remove leading "aas." from path
			if (path.find("aas.") == 0) path = path.substr(4);

			// Remove everything but address if a path component is present in string
			if ((offset = path.find_first_of('/')) != std::string::npos) path = path.substr(0, offset);

			// Return processed path
			return path;
		}
};




#endif /* BASYSID_BASYSID_H_ */
