/*
 * IBaSyxConnector.h
 *
 *  Created on: 14.08.2018
 *      Author: schnicke
 */

#ifndef VAB_BACKEND_CONNECTOR_IBASYXCONNECTOR_H
#define VAB_BACKEND_CONNECTOR_IBASYXCONNECTOR_H

#include <basyx/serialization/json/json.h>

#include <basyx/any.h>

#include <string>

namespace basyx {
namespace vab {
namespace connector	{ 

class IBaSyxConnector {
public:
	virtual ~IBaSyxConnector() {
	}
	/**
	 * Invoke a BaSys get operation. Retrieves the AAS, Submodel, Property, Operation or value at the given path.
	 * @return the de-serialized ElementRef
	 */
	virtual basyx::any basysGet(std::string const& path) = 0;

	/**
	 * Invoke a BaSys get operation without de-serialization
	 * @return the serialized element as a JSONObject
	 */
	virtual basyx::serialization::json::json_t basysGetRaw(std::string const& path) = 0;

	/**
	 * Invoke a Basys Set operation. Sets or overrides existing property, operation or event.
	 */
	virtual void basysSet(std::string const& path, const basyx::any &  newValue) = 0;

	/**
	 * Creates a new Property, Operation, Event, Submodel or AAS
	 */
	virtual void basysCreate(std::string const& servicePath, const basyx::any & value) = 0;

	/**
	 * Invoke a Basys Invoke operation. Invokes an operation on the server.
	 */
	virtual basyx::any basysInvoke(std::string const& servicePath, const basyx::any & param) = 0;

	/**
	 * Invoke a Basys operation. Deletes any resource under the given path.
	 * 
	 */
	virtual void basysDelete(std::string const& servicePath) = 0;

	/**
	 * Invoke a Basys oxperation. Deletes an entry from a map or collection by the given key
	 */
	virtual void basysDelete(std::string const& servicePath, const basyx::any & obj) = 0;
};

}
}
}


#endif /* VAB_BACKEND_CONNECTOR_IBASYXCONNECTOR_H */
