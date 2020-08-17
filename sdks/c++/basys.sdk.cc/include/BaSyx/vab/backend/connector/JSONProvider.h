/*
 * JSONProvider.h
 *	Provider class that supports JSON serialized communication
 *	Ports JSONProvider.java as initially written by Thomas Kuhn
 *  Created on: 07.08.2018
 *      Author: schnicke
 */

#ifndef VAB_BACKEND_CONNECTOR_JSONPROVIDER_H
#define VAB_BACKEND_CONNECTOR_JSONPROVIDER_H

#include <BaSyx/shared/object.h>
#include <BaSyx/shared/serialization/json.h>

#include <BaSyx/vab/backend/connector/native/frame/EntityWrapper.h>

#include <string>

namespace basyx {
namespace vab {

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

		return EntityWrapper::build_from_object(res).dump(4);
    }

    std::string processBaSysSet(std::string const& path, std::string const& serializedJSONValue)
    {
        auto deserialized = basyx::serialization::json::deserialize(serializedJSONValue);
        auto error = providerBackend->setModelPropertyValue(path, std::move(deserialized));

		if (error != basyx::object::error::None)
		{
			return EntityWrapper::build_from_error(error).dump(4);
		}
		else
		{
			return serializeSuccess();
		}
    }

    std::string processBaSysCreate(std::string const& path, std::string const& serializedJSONValue)
    {
        auto deserialized = basyx::serialization::json::deserialize(serializedJSONValue);
        auto error = providerBackend->createValue(path, std::move(deserialized));

		if (error != basyx::object::error::None)
		{
			return EntityWrapper::build_from_error(error).dump(4);
		}
		else
		{
			return serializeSuccess();
		}
	}

    std::string processBaSysDelete(std::string const& path, std::string const& serializedJSONValue)
    {
        auto deserialized = basyx::serialization::json::deserialize(serializedJSONValue);
        auto error = providerBackend->deleteValue(path, std::move(deserialized));

		if (error != basyx::object::error::None)
		{
			return EntityWrapper::build_from_error(error).dump(4);
		}
		else
		{
			return serializeSuccess();
		}
	}

    std::string processBaSysDelete(std::string const& path)
    {
        auto error = providerBackend->deleteValue(path);
		if (error != basyx::object::error::None)
		{
			return EntityWrapper::build_from_error(error).dump(4);
		}
		else
		{
			return serializeSuccess();
		}
	}

    std::string processBaSysInvoke(std::string const& path, std::string const& serializedJSONValue)
    {
		auto deserialized = basyx::serialization::json::deserialize(serializedJSONValue);
		auto res = providerBackend->invokeOperation(path, deserialized);
		return EntityWrapper::build_from_object(res).dump(4);
    }

private:
    Provider* providerBackend;

    std::string serializeSuccess()
    {
        return "";
	}
};

}
};

#endif /* VAB_VAB_BACKEND_CONNECTOR_JSONPROVIDER_H */
