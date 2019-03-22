#ifndef BACKENDS_PROVIDER_VAB_HASHMAPPROVIDER_H_
#define BACKENDS_PROVIDER_VAB_HASHMAPPROVIDER_H_


#include "api/IModelProvider.h"
#include "ref/BRef.h"
#include "types/BType.h"

#include <backends/provider/vab/VABPath.h>

#include <util/any.h>

#include <functional>
#include <unordered_map>
#include <iostream>


namespace basyx {

	using objectCollection_t = std::vector<basyx::any>;
	using objectMap_t = std::unordered_map<std::string, basyx::any>;

	namespace provider {

		class HashmapProvider : public IModelProvider
		{
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
			explicit HashmapProvider(objectMap_t && objectMap) : elements{ std::forward<objectMap_t>(objectMap) } { };

			HashmapProvider(const HashmapProvider &) = default;
			HashmapProvider & operator=(const HashmapProvider &) = default;

			HashmapProvider(HashmapProvider &&) = default;
			HashmapProvider & operator=(HashmapProvider &&) = default;
		public:
			objectMap_t * getParentElement(const std::string & path);

			/**
			* Get scope of a provided element.
			*
			* This is the namespace that is served by this model provider. E.g. iese.fraunhofer.de
			*/
			virtual std::string getElementScope(const std::string & elementPath) override
			{
				return "";
			};

			/**
			 * Get a sub model property value
			 */
			virtual BRef<BType> getModelPropertyValue(const std::string & path) override { return{};  };

			basyx::any & getModelPropertyValue2(const std::string & path);

			/**
			 * Set a sub model property value
			 */
			virtual void setModelPropertyValue(const std::string & path, basyx::any && newValue) override;

			/**
			 * Create a new property under the given path
			 */
			virtual void createValue(const std::string & path, basyx::any && newValue) override;

			/**
			 * Delete a value from a collection
			 */
			virtual void deleteValue(const std::string & path, basyx::any && deletedValue) override;


			/**
			 * Delete a property, operation, event, submodel or aas under the given path
			 *
			 * @param path Path to the entity that should be deleted
			 */
			virtual void deleteValue(const std::string & path) override;


			/**
			 * Invoke an operation
			 */
			virtual BRef<BType> invokeOperation(const std::string & path, BRef<BObjectCollection> parameter) override { return {}; };
		};
	}
}


#endif