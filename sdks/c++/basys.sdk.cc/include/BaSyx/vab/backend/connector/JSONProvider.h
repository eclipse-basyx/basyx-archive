/*
 * JSONProvider.h
 *	Provider class that supports JSON serialized communication
 *	Ports JSONProvider.java as initially written by Thomas Kuhn
 *  Created on: 07.08.2018
 *      Author: schnicke
 */

#ifndef VAB_BACKEND_CONNECTOR_JSONPROVIDER_H
#define VAB_BACKEND_CONNECTOR_JSONPROVIDER_H

#include <string>

#include <BaSyx/shared/object.h>
#include <BaSyx/shared/serialization/json.h>

// TODO: Repository?
// TODO: Implement exception when JSONTools support them

template <typename Provider>
class JSONProvider {
public:
    JSONProvider(Provider* providerBackend)
        : providerBackend { providerBackend }
    {
    }

    Provider* getBackend()
    {
        return providerBackend;
    }

    std::string processBaSysGet(std::string const& path)
    {
        auto res = providerBackend->getModelPropertyValue(path);
        return serializeToJSON(path, res);
    }

    std::string processBaSysSet(std::string const& path, std::string const& serializedJSONValue)
    {
        auto deserialized = basyx::serialization::json::deserialize(serializedJSONValue);
        providerBackend->setModelPropertyValue(path, std::move(deserialized));

        return serializeSuccess();
    }

    std::string processBaSysCreate(std::string const& path, std::string const& serializedJSONValue)
    {
        auto deserialized = basyx::serialization::json::deserialize(serializedJSONValue);
        providerBackend->createValue(path, std::move(deserialized));

        return serializeSuccess();
    }

    std::string processBaSysDelete(std::string const& path, std::string const& serializedJSONValue)
    {
        auto deserialized = basyx::serialization::json::deserialize(serializedJSONValue);
        providerBackend->deleteValue(path, std::move(deserialized));

        return serializeSuccess();
    }

    std::string processBaSysDelete(std::string const& path)
    {
        providerBackend->deleteValue(path);
        return serializeSuccess();
    }

    std::string processBaSysInvoke(std::string const& path, std::string const& serializedJSONValue, char* output, size_t* size)
    {
		auto deserialized = basyx::serialization::json::deserialize(serializedJSONValue);
		auto res = providerBackend->invokeOperation(path, deserialized);
		return serializeToJSON(path, res);
    }

private:
    Provider* providerBackend;

    std::string serializeSuccess()
    {
        nlohmann::json retJson { { "success", true } };

        return retJson.dump(4);
    }

    std::string serializeToJSON(const std::string& path, const basyx::object& value)
    {
        auto json = basyx::serialization::json::serialize(value);

        nlohmann::json retJson { { "success", true }, { "entity", json } };

        return retJson.dump(4);
    }
};

#endif /* VAB_VAB_BACKEND_CONNECTOR_JSONPROVIDER_H */
