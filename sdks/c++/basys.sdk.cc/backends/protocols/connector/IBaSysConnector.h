/*
 * IBaSysConnector.h
 *
 *  Created on: 14.08.2018
 *      Author: schnicke
 */

#ifndef BACKENDS_PROTOCOLS_CONNECTOR_IBASYSCONNECTOR_H_
#define BACKENDS_PROTOCOLS_CONNECTOR_IBASYSCONNECTOR_H_

#include "types/BType.h"
#include "ref/BRef.h"
#include "json/json.hpp"

#include <string>

class IBaSysConnector {
public:
	virtual ~IBaSysConnector() {
	}
	/**
	 * Invoke a BaSys get operation. Retrieves the AAS, Submodel, Property, Operation or value at the given path.
	 * @return the de-serialized ElementRef
	 */
	virtual BRef<BType> basysGet(std::string const& path) = 0;

	/**
	 * Invoke a BaSys get operation without de-serialization
	 * @return the serialized element as a JSONObject
	 */
	virtual nlohmann::json basysGetRaw(std::string const& path) = 0;

	/**
	 * Invoke a Basys Set operation. Sets or overrides existing property, operation or event.
	 */
	virtual void basysSet(std::string const& path, BRef<BType> newValue) = 0;

	/**
	 * Creates a new Property, Operation, Event, Submodel or AAS
	 */
	virtual void basysCreate(std::string const& servicePath, BRef<BType> val) = 0;

	/**
	 * Invoke a Basys Invoke operation. Invokes an operation on the server.
	 */
	virtual BRef<BType> basysInvoke(std::string const& servicePath, BRef<BType> param) = 0;

	/**
	 * Invoke a Basys operation. Deletes any resource under the given path.
	 * 
	 */
	virtual void basysDelete(std::string const& servicePath) = 0;

	/**
	 * Invoke a Basys oxperation. Deletes an entry from a map or collection by the given key
	 */
	virtual void basysDelete(std::string const& servicePath, BRef<BType> obj) = 0;

};

#endif /* BACKENDS_PROTOCOLS_CONNECTOR_IBASYSCONNECTOR_H_ */
