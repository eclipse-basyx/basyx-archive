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
#include <vector>
#include <iostream>

// C headers
#include <string.h>

/* ************************************************
 * Class declaration
 * ************************************************/
class BaSysID {

private:
	// Constants
	static const std::string AASQualifier;
	static const std::string SubmodelQualifier;
	static const std::string SubmodelsQualifier;
	static const std::string ChildrenQualifier;
	static const std::string PropertiesQualifier;
	static const std::string OperationsQualifier;
	static const std::string EventQualifier;

	// Support functions
public:

	/**
	 * Create an ID string that refers to an Asset Administration Shell for the directory server
	 * @version 0.2
	 *
	 * @param aasID ID of asset administration shell
	 * @return Built ID in format {@code <aasID>/aas}
	 *
	 */
	static std::string buildAASID(std::string aasID) {
		return aasID + "/" + AASQualifier;
	}

	/**
	 * Create an ID string that refers to a single sub model for the directory server
	 * @version 0.2
	 *
	 * @param smID ID of sub model
	 *
	 * @return Built ID in format {@code <smID>/submodel} (mind the "s"!)
	 */
	static std::string buildSMID(std::string smID) {
		return smID + "/" + SubmodelQualifier;
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
	static std::string buildPath(std::string aasID, std::string subModelID) {
		// Only return one id if other is is null or empty
		if ((aasID.empty()) || (aasID.length() == 0))
			return buildSMID(subModelID);
		if ((subModelID.empty()) || (subModelID.length() == 0))
			return buildAASID(aasID);

		// Build path
		return aasID + "/" + AASQualifier + "/" + SubmodelsQualifier + "/"
				+ subModelID;
	}

	/**
	 * Create a scoped ID string for the directory server. The scope will be reversed so that top level scope comes last.
	 * @version 0.2
	 *
	 * @param scope sub model scope as a string array
	 * @param aasID ID of asset administration shell
	 * @param subModelID ID of sub model
	 *
	 * @return Built ID in format {@code <scope1>.<scope2>. ... .<scopeN>/<aasID>/aas/submodels/<subModelID> } where scope1 is the topscope
	 */
	static std::string buildPath(std::string scope[], size_t scopeCount,
			std::string aasID, std::string subModelID) {
		std::string result = scope[0];

		// Append reversed scope

		for (size_t i = 1; i < scopeCount; i++)
			result.append("." + scope[i]);
		result.append(
				"/" + buildAASID(aasID) + "/" + SubmodelsQualifier + "/"
						+ subModelID);

		// Return build ID
		return result;
	}

	/**
	 * Create a scoped ID string for the directory server. The scope will be reversed so that top level scope comes last.
	 * @version 0.2
	 *
	 * @param scope sub model scope
	 * @param subModelID ID of sub model
	 *
	 * @return Built ID in format {@code <scope1>.<scope2>. ... . <scopeN>/<subModelID>/submodel } where scope1 is the topscope
	 */
	static std::string buildPath(std::string scope[], size_t scopeCount,
			std::string subModelID) {
		std::string result = scope[0];

		// Append reversed scope
		for (size_t i = 1; i < scopeCount; i++)
			result.append("." + scope[i]);

		result.append("/" + buildSMID(subModelID));
		// Return build ID
		return result;
	}

	/**
	 * <pre>
	 *   Get aas id from a qualified path that my contain scope. Handle the following cases <br>
	 *  @version 0.2
	 *  @return "" or aasID if available
	 *  @param path has format <br>
	 *  (1) {@code <aasID>/aas } or <br>
	 *  (2) {@code <aasID>/aas/submodels } or <br>
	 *  (3) {@code <aasID>/aas/submodels/<submodelID> } or <br>
	 *  (4) {@code <aasID>/aas/submodels/<submodelID>/... } or <br>
	 *  (5) {@code <submodelID>/submodel/... }
	 */
	static std::string getAASID(std::string path) {
		// (1-4) search for aas id
		if (path.find(AASQualifier) != std::string::npos) {
			std::vector<std::string> splitted = splitPropertyPath(path);
			for (size_t i = 0; i < splitted.size(); i++) {
				if (splitted[i] == "aas") {
					return splitted[i - 1];
				}
			}
		}

		// (5)
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
	static std::string getSubmodelID(std::string path) {
		std::size_t offset = 0;

		if ((offset = path.find(SubmodelsQualifier)) // (2-4)
		!= std::string::npos) {
			if (offset == (path.length() - SubmodelsQualifier.length())) { // (2)
				return "";
			} else { // (3,4)
				std::string subst = path.substr(
						offset + SubmodelsQualifier.length() + 1,
						path.length());
				// Cut off potential trailing part
				if ((offset = subst.find("/")) != std::string::npos) {
					return subst.substr(0, offset);
				} else {
					return subst;
				}
			}
		} else if ((offset = path.find(SubmodelQualifier))
				!= std::string::npos) { // (5) search for submodel
			std::vector<std::string> splitted = splitPropertyPath(path);
			for (size_t i = 0; i < splitted.size(); i++) {
				if (splitted[i] == SubmodelQualifier) {
					return splitted[i - 1];
				}
			}
		}
		// (1)
		return "";
	}

	/**
	 * Get the last element of path that identifies the object
	 */
	static std::string getIdentifier(std::string path) {
		// Try to get path
		std::string objectId = "";

		// If a path is given, return last path entry
		if (path.length() > 0) {
			// Return last path element
			std::vector<std::string> pathArray = splitPropertyPath(path);

			return pathArray.back();
		}

		// Try to get sub model ID or AAS ID
		if ((objectId = getSubmodelID(path)).length() > 0)
			return objectId;
		if ((objectId = getAASID(path)).length() > 0)
			return objectId;

		// No identifier given
		return "";
	}

	/**
	 * Get IElement ID
	 *
	 * (1) <aasid>/aas                        		--> <aasid>
	 * (2) <scope>/<aasid>/aas                		--> <aasid>
	 * (3) <aasid>/aas/submodels               		--> <aasid>
	 * (4) <scope>/<aasid>/aas/submodels       		--> <aasid>
	 * (5) <aasid>/aas/submodels/<submodel>   		--> <submodel>
	 * (6) <scope>/<aasid>/aas/submodels/<submodel>	--> <submodel>
	 * (7) <submodel>/submodel/..						--> <submodel>
	 * (8) <scope>/<submodel>/submodel/..				--> <submodel>
	 */
	static std::string getElementID(std::string path) {
		std::vector<std::string> splitted = splitPropertyPath(path);
		if (path.find("/submodel") != std::string::npos) { // (3-8)
			for (size_t i = 0; i < splitted.size(); i++) {
				if (splitted[i] == "submodels") {
					if (splitted.size() > i + 1) { // (5-6)
						return splitted[i + 1];
					} else {
						return splitted[i - 2]; // (3-4)
					}
				} else if (splitted[i] == "submodel") { // (7-8)
					return splitted[i - 1];
				}
			}
		} else { // (1-2)
			for (size_t i = 0; i < splitted.size(); i++) {
				if (splitted[i] == "aas") {
					return splitted[i - 1];
				}
			}
		}
		return "";
	}

	/**
	 * Get qualified IElement ID
	 *
	 * (1) <aasid>/aas                        		--> <aasid>
	 * (2) <scope>/<aasid>/aas                		--> <scope>/<aasid>
	 * (3) <aasid>/aas/submodels               		--> <aasid>
	 * (4) <scope>/<aasid>/aas/submodels       		--> <scope>/<aasid>
	 * (5) <aasid>/aas/submodels/<submodel>   		--> <submodel>
	 * (6) <scope>/<aasid>/aas/submodels/<submodel>	--> <scope>/<submodel>
	 * (7) <submodel>/submodel/..					--> <submodel>
	 * (8) <scope>/<submodel>/submodel/..			--> <scope>/<submodel>
	 */
	static std::string getQualifiedElementID(std::string path) {
		std::string id = getElementID(path);
		if (path.find(".") == std::string::npos) { // contains no scope
			return id;
		} else {  // Handle scoped ids
			std::size_t offset = path.find("/");
			if (offset != std::string::npos && offset != 0) {
				return path.substr(0, offset) + "/" + id;
			}
		}
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
	static std::string getPath(std::string path) {
		std::vector<std::string> splitted = splitPropertyPath(path);
		for (size_t i = 0; i < splitted.size(); i++) {

			// Handle cases (8 - 13)
			if (splitted[i] == SubmodelQualifier) {

				// Handle case (11 - 13)
				if (splitted.size() >= i + 3)
					return buildPathRest(splitted, i + 2);

				// Handle case (8 - 10)
				if (splitted.size() == i + 2)
					return buildPathRest(splitted, i + 1);

			}

			// Handle cases (1 - 7)
			if (splitted[i] == SubmodelsQualifier) {

				// Handle case (5 - 7)
				if (splitted.size() == i + 4)
					return buildPathRest(splitted, i + 3);

				// Handle case (2 - 4)
				if (splitted.size() == i + 3)
					return buildPathRest(splitted, i + 2);

				// Handle case (1)
				if (splitted.size() == i + 1)
					return SubmodelsQualifier;
			}

			// Handle case 1.1
			if (splitted[i] == ChildrenQualifier) {
				return ChildrenQualifier;
			}
		}

		return "";
	}

	/**
	 * Get the last n path entries of a path
	 */
	static std::vector<std::string> getLastPathEntries(std::string path,
			size_t lastEntries) {
		// Return result
		std::vector<std::string> result;

		// Temporary variables
		std::string propPath = getPath(path);
		std::vector<std::string> pathArray = splitPropertyPath(propPath);

		// Copy requested path elements
		std::vector<std::string>::iterator it = pathArray.begin();
		// - Advance iterator
		std::advance(it, pathArray.size() - lastEntries);
		// - Copy elements
		for (size_t i = 0; i < lastEntries; i++) {
			result.push_back(*it);
			std::advance(it, 1);
		}

		// Return path segments
		return result;
	}

	/**
	 * Get qualified address (submodel ID and AAS ID)
	 * {@code example input: Testsuite/GW/IESE/line1/gateway_line12/device2.line2.manufacturing.de/aas }
	 * TODO check compatible with version 0.2
	 */
	static std::string getAddress(std::string path) {
		std::vector<std::string> scope = getScope(path);
		if (scope.size() > 0) {
			std::string* scopeSplitted = new std::string[scope.size()];
			for (size_t i = 0; i < scope.size(); i++) {
				scopeSplitted[i] = scope[i];
			}
			std::string address = buildPath(scopeSplitted, scope.size(),
					getAASID(path), getSubmodelID(path));

			delete[] scopeSplitted;
			return address;
		} else {
			return buildPath(getAASID(path), getSubmodelID(path));
		}
	}

	/**
	 * Split a property path
	 */
	static std::vector<std::string> splitPropertyPath(std::string pathString) {
		// Result
		std::vector<std::string> result;

		// Return empty array for empty string
		if (pathString.length() == 0)
			return result;

		// Process paths that have no splitting character
		if ((pathString.find_first_of("/") == std::string::npos)
				&& (pathString.find_first_of(".") == std::string::npos)) {
			result.push_back(pathString);
			return result;
		}

		// Get path segments
		// - Copy String
		char *copy = strdup(pathString.c_str());
		// - Get next token
		char *token = strtok(copy, "/\\.");
		// - Repeat until no token is left
		while (token != NULL) {
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
	 * Returns the scope of a path, if there is any
	 */
	static std::vector<std::string> getScope(std::string path) {
		if (path.find('.') != std::string::npos) {
			size_t offset = path.find("/");
			std::string scope = path.substr(0, offset);
			return splitPropertyPath(scope);
		} else {
			return std::vector<std::string>();
		}
	}
	
	/**
	 * Returns the scope of a path, if there is any
	 */
	static std::string getScopeString(std::string path) {
		if (path.find('.') != std::string::npos) {
			size_t offset = path.find("/");
			return path.substr(0, offset);
		} else {
			return "";
		}
	}
	
private:
	static std::string buildPathRest(std::vector<std::string> const& splitted, size_t start) {
		std::string ret = splitted[start];
		start++;
		for(size_t i = start; i < splitted.size(); i++) {
			ret += "/" + splitted[i];
		}
		return ret;
	}
};

#endif /* BASYSID_BASYSID_H_ */
