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

#include <basyx/any.h>
#include <basyx/basysid/BaSysID.h>
#include <basyx/serialization/json.h>

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
        auto& res = providerBackend->getModelPropertyValue(path);
        return serializeToJSON(path, res);
    }

    void processBaSysSet(std::string const& path, std::string const& serializedJSONValue)
    {
        if (!isFrozen(BaSysID::getAddress(path))) {
            auto deserialized = basyx::serialization::json::deserialize(serializedJSONValue);
            providerBackend->setModelPropertyValue(path, std::move(deserialized));
            incrementClock(BaSysID::getAddress(path));
        }
    }

    void processBaSysCreate(std::string const& path, std::string const& serializedJSONValue)
    {
        auto deserialized = basyx::serialization::json::deserialize(serializedJSONValue);
        providerBackend->createValue(path, std::move(deserialized));
    }

    void processBaSysDelete(std::string const& path, std::string const& serializedJSONValue)
    {
        if (!isFrozen(BaSysID::getAddress(path))) {
            auto deserialized = basyx::serialization::json::deserialize(serializedJSONValue);
            providerBackend->deleteValue(path, std::move(deserialized));
            incrementClock(BaSysID::getAddress(path));
        }
    }

    void processBaSysDelete(std::string const& path)
    {
        if (!isFrozen(BaSysID::getAddress(path))) {
            providerBackend->deleteValue(path);
            incrementClock(BaSysID::getAddress(path));
        }
    }

    std::string processBaSysInvoke(std::string const& path, std::string const& serializedJSONValue, char* output, size_t* size)
    {
        // ToDo: invoke operations
		auto deserialized = basyx::serialization::json::deserialize(serializedJSONValue);

        basyx::any res;

        if (deserialized.InstanceOf<basyx::objectCollection_t>()) {
            auto& parameters = deserialized.Get<basyx::objectCollection_t&>();
            res = providerBackend->invokeOperation(path, parameters);
        } else {
            basyx::objectCollection_t parameters { deserialized };
            res = providerBackend->invokeOperation(path, parameters);
        }

        return serializeToJSON(path, res);
    }

private:
    Provider* providerBackend;

    //void serializeToJSON(std::string const& path, BRef<BType> const& val, char* output, size_t* size)
    //{
    //	//std::string serialized = jsonTools->serialize(val, 0, providerBackend->getElementScope(path)).dump();
    //	//memcpy(output, serialized.c_str(), serialized.length());
    //	//*size = serialized.length();
    //}

    std::string serializeToJSON(const std::string& path, const basyx::any& value)
    {
        auto json = basyx::serialization::json::serialize(value);

        nlohmann::json retJson { { "success", true }, { "entity", json } };

        return retJson.dump(4);
    }

    void incrementClock(std::string const& submodelPath)
    {
        std::string clockPath = submodelPath + "/properties/clock";
        basyx::any& clock = providerBackend->getModelPropertyValue(clockPath);
        providerBackend->setModelPropertyValue(clockPath, clock.Get<int>() + 1);
    }

    bool isFrozen(std::string const& submodelPath)
    {
        basyx::any& modelPropertyValue = providerBackend->getModelPropertyValue(submodelPath + "/properties/frozen");

        if (modelPropertyValue.IsNull())
            return false;

        return providerBackend->getModelPropertyValue(submodelPath + "/properties/frozen").template Get<bool>();
    }
};

#endif /* VAB_VAB_BACKEND_CONNECTOR_JSONPROVIDER_H */
