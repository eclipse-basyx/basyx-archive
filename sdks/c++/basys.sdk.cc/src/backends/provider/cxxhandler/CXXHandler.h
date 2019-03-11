/*
 * CXXHandler.h
 *
 *  Created on: 16.08.2018
 *      Author: schnicke
 */

#ifndef BACKENDS_PROVIDER_CXXHANDLER_CXXHANDLER_H_
#define BACKENDS_PROVIDER_CXXHANDLER_CXXHANDLER_H_

#include "api/IModelProvider.h"
#include "ref/BRef.h"
#include "types/BType.h"

#include <functional>
#include <unordered_map>

class CXXHandler: public IModelProvider {
public:
	void addProperty(std::string const& propName,
			std::function<BRef<BType>()> get,
			std::function<void(BRef<BType>)> set,
			std::function<void(BRef<BType>)> create,
			std::function<void(BRef<BType>)> del) {
		getMap[propName] = get;
		setMap[propName] = set;
		createMap[propName] = create;
		deleteMap[propName] = del;
	}

	void addFunction(std::string const& path,
			std::function<BRef<BType>(BRef<BType>)> invoke) {
		invokeMap[path] = invoke;
	}

	/**
	 * Get scope of a provided element.
	 *
	 * This is the namespace that is served by this model provider. E.g. iese.fraunhofer.de
	 */
	virtual std::string getElementScope(std::string elementPath) override {
		return "";
	}

	/**
	 * Get a sub model property value
	 */
	virtual BRef<BType> getModelPropertyValue(std::string path) override {
		return getMap[path]();
	}

	/**
	 * Set a sub model property value
	 */
	virtual void setModelPropertyValue(std::string path, BRef<BType> newValue) override {
		setMap[path](newValue);
	}

	/**
	 * Create a new property under the given path
	 */
	virtual void createValue(std::string path, BRef<BType> addedValue) override {
		createMap[path](addedValue);
	}

	/**
	 * Delete a value from a collection
	 */
	virtual void deleteValue(std::string path, BRef<BType> deletedValue) override {
		deleteMap[path](deletedValue);
	}

	/**
	 * Delete a property, operation, event, submodel or aas under the given path
	 *
	 * @param path Path to the entity that should be deleted
	 */
	virtual void deleteValue(std::string path) override {
		setMap.erase(path);
		getMap.erase(path);
		createMap.erase(path);
		deleteMap.erase(path);
		invokeMap.erase(path);
	}

	/**
	 * Invoke an operation
	 */
	virtual BRef<BType> invokeOperation(std::string path,
			BRef<BObjectCollection> parameter) override {
		return invokeMap[path](parameter);
	}
private:

	std::unordered_map<std::string, std::function<BRef<BType>()>> getMap;
	std::unordered_map<std::string, std::function<void(BRef<BType>)>> setMap;
	std::unordered_map<std::string, std::function<void(BRef<BType>)>> createMap;
	std::unordered_map<std::string, std::function<void(BRef<BType>)>> deleteMap;

	std::unordered_map<std::string, std::function<BRef<BType>(BRef<BType>)>> invokeMap;

};

#endif /* BACKENDS_PROVIDER_CXXHANDLER_CXXHANDLER_H_ */
