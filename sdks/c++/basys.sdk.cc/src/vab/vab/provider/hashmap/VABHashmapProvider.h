#ifndef VAB_PROVIDER_HASHMAP_VABHASHMAPPROVIDER_H
#define VAB_PROVIDER_HASHMAP_VABHASHMAPPROVIDER_H

#include <vab/core/IModelProvider.h>
#include <vab/core/util/VABPath.h>

#include <basyx/any.h>
#include <basyx/types.h>

#include <functional>
#include <iostream>
#include <unordered_map>

namespace basyx {
namespace vab {
namespace provider {
    //using objectCollection_t = std::vector<basyx::any>;
    //using objectMap_t = std::unordered_map<std::string, basyx::any>;

    class HashmapProvider : public vab::core::IModelProvider {
        // class types
    public:
        using objectMap_t = basyx::objectMap_t;
        // class members
    private:
        objectMap_t elements;

    public:
        // HashmapProvider structors
        explicit HashmapProvider() {};
        //	explicit HashmapProvider(const objectMap_t & objectMap) : elements{ objectMap } {};
        explicit HashmapProvider(objectMap_t&& objectMap)
            : elements { std::forward<objectMap_t>(objectMap) } {};

        HashmapProvider(const HashmapProvider&) = default;
        HashmapProvider& operator=(const HashmapProvider&) = default;

        HashmapProvider(HashmapProvider&&) = default;
        HashmapProvider& operator=(HashmapProvider&&) = default;

    public:
        objectMap_t* getParentElement(const std::string& path);

        /**
         * Get scope of a provided element.
         *
         * This is the namespace that is served by this model provider. E.g. iese.fraunhofer.de
         */
        virtual std::string getElementScope(const std::string& elementPath) override
        {
            return "";
        };

        /**
         * Get a sub model property value
         */
        basyx::any getModelPropertyValue(const std::string& path) override;

        /**
 		 * Set a sub model property value
 		 */
		virtual void setModelPropertyValue(const std::string& path, const basyx::any& newValue) override;

        /**
         * Create a new property under the given path
         */
        virtual void createValue(const std::string& path, const basyx::any& newValue) override;

        /**
         * Delete a value from a collection
         */
        virtual void deleteValue(const std::string& path, const basyx::any& deletedValue) override;

        /**
         * Delete a property, operation, event, submodel or aas under the given path
         *
         * @param path Path to the entity that should be deleted
         */
        virtual void deleteValue(const std::string& path) override;

        /**
         * Invoke an operation
         */
        virtual basyx::any invokeOperationImpl(const std::string& path, basyx::objectCollection_t& parameters) override;
    };

}
}
}

#endif /* VAB_PROVIDER_HASHMAP_VABHASHMAPPROVIDER_H */