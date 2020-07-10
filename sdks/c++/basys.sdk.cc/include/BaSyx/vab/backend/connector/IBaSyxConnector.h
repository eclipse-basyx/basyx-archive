/*
 * IBaSyxConnector.h
 *
 *  Created on: 14.08.2018
 *      Author: schnicke
 */

#ifndef VAB_BACKEND_CONNECTOR_IBASYXCONNECTOR_H
#define VAB_BACKEND_CONNECTOR_IBASYXCONNECTOR_H

#include <BaSyx/shared/serialization/json/json.h>

#include <BaSyx/shared/object.h>

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
	virtual basyx::object basysGet(std::string const& path) = 0;

	/**
	 * Invoke a Basys Set operation. Sets or overrides existing property, operation or event.
	 */
	virtual basyx::object basysSet(std::string const& path, const basyx::object &  newValue) = 0;

	/**
	 * Creates a new Property, Operation, Event, Submodel or AAS
	 */
	virtual basyx::object basysCreate(std::string const& servicePath, const basyx::object & value) = 0;

	/**
	 * Invoke a Basys Invoke operation. Invokes an operation on the server.
	 */
	virtual basyx::object basysInvoke(std::string const& servicePath, const basyx::object & param) = 0;

	/**
	 * Invoke a Basys operation. Deletes any resource under the given path.
	 * 
	 */
	virtual basyx::object basysDelete(std::string const& servicePath) = 0;

	/**
	 * Invoke a Basys oxperation. Deletes an entry from a map or collection by the given key
	 */
	virtual basyx::object basysDelete(std::string const& servicePath, const basyx::object & obj) = 0;
};

}
}
}


#endif /* VAB_BACKEND_CONNECTOR_IBASYXCONNECTOR_H */
