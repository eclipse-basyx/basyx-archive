#include "VABHashmapProvider.h"

#include <util/printer.h>

#include <basyx/function.h>

#include <log/log.h>

#include <algorithm>
#include <cassert>

namespace basyx {
namespace vab {
    namespace provider {

        HashmapProvider::objectMap_t* HashmapProvider::getParentElement(const std::string& path)
        {
            core::VABPath vabPath { path };

            if (vabPath.getElements().size() == 0)
                return nullptr;

            objectMap_t* currentScope = &elements;

            for (std::size_t i = 0; i < vabPath.getElements().size() - 1; ++i) {
                auto& pathElement = vabPath.getElements().at(i);
                basyx::any* element = nullptr;

                // Try to find parent, despite not knowing if leading "/" must be added
                if (currentScope->find(pathElement) != currentScope->end()) {
                    element = &currentScope->at(pathElement);
                    currentScope = &element->Get<basyx::objectMap_t&>();
                }

                if (element == nullptr)
                    return nullptr;
                if (!element->InstanceOf<objectMap_t>())
                    return nullptr;
            }

            return currentScope;
        };

        void HashmapProvider::setModelPropertyValue(const std::string& path, const basyx::any& newValue)
        {
            core::VABPath vabPath { path };
            auto& lastPathElement = vabPath.getLastElement();

            auto parentElement = getParentElement(path);

            if (parentElement->find(lastPathElement) != parentElement->end()) {
                parentElement->at(lastPathElement) = newValue;
            } else {
                parentElement->emplace(lastPathElement, newValue);
            };
        };

        basyx::any HashmapProvider::getModelPropertyValue(const std::string& path)
        {
            // Split path into its elements, separated by '/'
            core::VABPath pathElements { path };

            // Get parent of element
            auto* parentElement = getParentElement(path);

            // Return parent element if path was empty, in this case the whole map was requested
            // TODO: Resolve return type mismatch here
            if (pathElements.getElements().size() == 0) {
                return elements;
            }

            // if path not found, return null
            if (parentElement == nullptr)
                return basyx::any { nullptr };

            // Return element, if in map
            if (parentElement->find(pathElements.getLastElement()) != parentElement->end())
                return parentElement->at(pathElements.getLastElement());

            // Return null
            return basyx::any { nullptr };
        };

        void HashmapProvider::createValue(const std::string& path, const basyx::any& newValue)
        {
            core::VABPath vabPath { path };
            auto elementName = vabPath.getLastElement();

            // Get parent of element
            auto parentElement = getParentElement(path);

            // Check if element is present and a collection, in this case add new element collection
            if (parentElement != nullptr && parentElement->find(elementName) != parentElement->end()) {
                auto& element = parentElement->at(elementName);

                if (element.InstanceOf<basyx::objectCollection_t>()) {
                    // add new value to collection
                    auto& objectCollection = element.Get<basyx::objectCollection_t&>();
                    objectCollection.emplace_back(std::move(newValue));
                    return;
                }
                //else if (element.InstanceOf<basyx::objectSet_t>()) {
                //	// add new value to set
                //	auto & objectSet = element.Get<basyx::objectSet_t&>();
                //	objectSet.emplace(std::move(newValue));
                //	return;
                //}
            }

            //// if target not known, do nothing
            if (parentElement == nullptr)
                return;

            // Target is map, put key and element value into map
            parentElement->emplace(vabPath.getLastElement(), std::move(newValue));
        };

        void HashmapProvider::deleteValue(const std::string& path)
        {
            // Split path into its elements, separated by '/'
            core::VABPath vabPath { path };
            // - Element name
            const auto& elementName = vabPath.getLastElement();
            // Get parent of element
            auto parentElement = getParentElement(path);

            // Remove named element
            parentElement->erase(parentElement->find(elementName));
        };

        void basyx::vab::provider::HashmapProvider::deleteValue(const std::string& path, const basyx::any& deletedValue)
        {
            // Split path into its elements, separated by '/'
            core::VABPath vabPath { path };
            const auto& elementName = vabPath.getLastElement();

            // Get parent of element
            auto parentElement = getParentElement(path);

            if (parentElement == nullptr)
                return;

            // - Get element
            basyx::any& element = parentElement->at(elementName);

            // Check if element is a list
            if (element.InstanceOf<basyx::objectCollection_t>()) {
                auto& collection = element.Get<basyx::objectCollection_t&>();
                // ToDo: fix
                auto it = std::find_if(collection.begin(), collection.end(), [&](const basyx::any& any) -> bool { return any == deletedValue; });
                if (it != collection.end())
                    collection.erase(it);
            }
            // Check if element is a set
            //else if (element.InstanceOf<basyx::objectSet_t>())
            //{
            //	auto & collection = element.Get<basyx::objectSet_t&>();
            //	auto it = std::find_if(collection.begin(), collection.end(), [&](const basyx::any & any) -> bool { return any == deletedValue; });
            //	if (it != collection.end())
            //		collection.erase(it);
            //}
            else if (element.InstanceOf<basyx::objectMap_t>()) {
                auto& objectMap = element.Get<basyx::objectMap_t&>();
                for (auto it = objectMap.begin(); it != objectMap.end(); ++it) {
                    if (it->second == deletedValue) {
                        objectMap.erase(it);
                        return;
                    }
                }
            }
        }

        basyx::any basyx::vab::provider::HashmapProvider::invokeOperationImpl(const std::string& path, basyx::objectCollection_t& parameters)
        {
            basyx::log::topic("VABHashMap").trace("invokeOperationImpl called");
            basyx::log::topic("VABHashMap").trace("    path: \"{}\"", path);
            basyx::log::topic("VABHashMap").trace("    parameters: <not impl>");

            auto element = this->getModelPropertyValue(path);

            if (element.IsNull()) {
                basyx::log::topic("VABHashMap").error("Function not found!");
                basyx::log::topic("VABHashMap").trace("Returning basyx::any::null");

                return basyx::any { nullptr };
            }

            if (!element.IsInvokable()) {
                basyx::log::topic("VABHashMap").error("Found object not invokable!");
                basyx::log::topic("VABHashMap").trace("Returning basyx::any::null");

                return basyx::any { nullptr };
            };

            auto function = element.GetPtr<function_base>();

            basyx::log::topic("VABHashMap").trace("Function found. Invoking...");

            return function->invoke_any(parameters);
        }
    }
}
}
