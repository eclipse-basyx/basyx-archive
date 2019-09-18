/*
 * IModelProvider.h
 *
 *  Created on: 28.04.2018
 *      Author: kuhn
 */

#ifndef VAB_CORE_IMODELPROVIDER_H
#define VAB_CORE_IMODELPROVIDER_H

/* ******************************************
 * Includes
 * ******************************************/

// BaSyx includes
#include <basyx/types.h>
#include <basyx/any.h>

// Std C++ includes
#include <map>
#include <string>

/* ******************************************
 * Basic model provider backend interface
 * ******************************************/

namespace basyx {
namespace vab {
namespace core {

    class IModelProvider {

    public:
        virtual ~IModelProvider()
        {
        }

        /* ***********************************************
     * Public members
     * ***********************************************/
    public:
        /**
     * Get scope of a provided element.
     *
     * This is the namespace that is served by this model provider. E.g. iese.fraunhofer.de
     */
        virtual std::string getElementScope(const std::string& elementPath) = 0;

        /**
     * Get a sub model property value
     */
        virtual basyx::any getModelPropertyValue(const std::string& path) = 0;

        /**
     * Set a sub model property value
     */
        virtual void setModelPropertyValue(const std::string& path, const basyx::any& newValue) = 0;

        /**
     * Create a new property under the given path
     */
        virtual void createValue(const std::string& path, const basyx::any& addedValue) = 0;

        /**
     * Delete a value from a collection
     */
        virtual void deleteValue(const std::string& path, const basyx::any& deletedValue) = 0;

        /**
     * Delete a property, operation, event, submodel or aas under the given path
     *
     * @param path Path to the entity that should be deleted
     */
        virtual void deleteValue(const std::string& path) = 0;

        /**
     * Invoke an operation
     */
        virtual basyx::any invokeOperationImpl(const std::string& path, basyx::objectCollection_t& parameters) = 0;

		template<typename... Args>
		basyx::any invokeOperation(const std::string& path, const Args&... args)
		{
			auto argList = basyx::objectCollection_t{ args... };
			return this->invokeOperationImpl(path, argList);
		}

        /**
     * Get contained elements
     *
     * Contained sub model elements are returned as Map of key/value pairs. Keys are Strings, values are either primitive values or
     * ElementRef objects that contain a reference to a complex object instance.
     */
        //virtual std::map<std::string, IElementReference> getContainedElements(std::string path) = 0;
    };


}
}
};


#endif /* VAB_CORE_IMODELPROVIDER_H */
