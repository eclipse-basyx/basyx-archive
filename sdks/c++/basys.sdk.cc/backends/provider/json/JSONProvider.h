/*
 * JSONProvider.h
 *	Provider class that supports JSON serialized communication
 *	Ports JSONProvider.java as initially written by Thomas Kuhn
 *  Created on: 07.08.2018
 *      Author: schnicke
 */

#ifndef BACKENDS_PROVIDER_JSON_JSONPROVIDER_H_
#define BACKENDS_PROVIDER_JSON_JSONPROVIDER_H_

#include <string>
#include "json/JSONTools.h"

// TODO: Clock?
// TODO: Repository?

template<typename T>
class JSONProvider {
public:

	JSONProvider(T* providerBackend, JSONTools* jsonTools) {
		this->providerBackend = providerBackend;
		this->jsonTools = jsonTools;
	}

	T* getBackend() {
		return providerBackend;
	}

	void processBaSysGet(std::string const& path, char* output, size_t* size) {
		BRef<BType> res = providerBackend->getModelPropertyValue(path);
		serializeToJSON(path, res, output, size);
	}

	void processBaSysSet(std::string const& path,
			std::string const& serializedJSONValue) {
		BRef<BType> deserialized = jsonTools->deserialize(json::parse(serializedJSONValue),
				0, providerBackend->getElementScope(path));
		providerBackend->setModelPropertyValue(path, deserialized);
	}

	void processBaSysCreate(std::string const& path,
			std::string const& serializedJSONValue) {
		BRef<BType> deserialized = jsonTools->deserialize(json::parse(serializedJSONValue),
				0, providerBackend->getElementScope(path));
		providerBackend->createValue(path, deserialized);
	}

	void processBaSysDelete(std::string const& path,
			std::string const& serializedJSONValue) {
		BRef<BType> deserialized = jsonTools->deserialize(json::parse(serializedJSONValue),
				0, providerBackend->getElementScope(path));
		providerBackend->deleteValue(path, deserialized);
	}

	void processBaSysInvoke(std::string const& path,
			std::string const& serializedJSONValue, char* output,
			size_t* size) {
		BRef<BType> deserialized = jsonTools->deserialize(json::parse(serializedJSONValue),
				0, providerBackend->getElementScope(path));
		BRef<BType> res = providerBackend->invokeOperation(path, deserialized);
		serializeToJSON(path, res, output, size);
	}

private:
	T* providerBackend;
	JSONTools* jsonTools;

	void serializeToJSON(std::string const& path, BRef<BType> const& val,
			char* output, size_t* size) {
		std::string serialized = jsonTools->serialize(val, 0,
				providerBackend->getElementScope(path)).dump();
		memcpy(output, serialized.c_str(), serialized.length());
		*size = serialized.length();
	}
};

#endif /* BACKENDS_PROVIDER_JSON_JSONPROVIDER_H_ */
