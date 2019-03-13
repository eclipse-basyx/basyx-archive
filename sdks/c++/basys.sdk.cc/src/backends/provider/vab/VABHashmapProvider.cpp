#include "VABHashmapProvider.h"

#include <util/printer.h>

#include <algorithm>
#include <cassert>


namespace basyx {
namespace provider {

    HashmapProvider::objectMap_t * HashmapProvider::getParentElement(const std::string & path)
    {
        VABPath vabPath { path };
		 
        objectMap_t * currentScope = &elements;

		for(std::size_t i = 0; i < vabPath.getElements().size() - 1; ++i)
		{
			auto & pathElement = vabPath.getElements().at(i);
            basyx::any * element = nullptr;

            // TODO: skip null entry?

          //  std::cout << "Get Parent Element: " << pathElement << " -> " << *currentScope << std::endl;

            // Try to find parent, despite not knowing if leading "/" must be added
            if (currentScope->find(pathElement) != currentScope->end()) {
                element = &currentScope->at(pathElement);
				currentScope = &element->Get<basyx::objectMap_t&>();
            }

      //      std::cout << "  - Element: "  "[]" << std::endl;

            if (element == nullptr)
                return nullptr;
            if (!element->InstanceOf<objectMap_t>())
                return nullptr;
	    }

	//	std::cout << "Get Parent Element: returned parent of " << *currentScope << std::endl;

		return currentScope;
	};


    void HashmapProvider::setModelPropertyValue(const std::string & path, basyx::any && newValue)
    {
        std::cout << "SetModelPropertyValue1:" + path << std::endl;

        VABPath vabPath { path };
		auto & lastPathElement = vabPath.getLastElement();

        auto parentElement = getParentElement(path);

		if (parentElement->find(lastPathElement) != parentElement->end())
		{
			parentElement->at(lastPathElement) = std::move(newValue);
		}
		else
		{
			parentElement->emplace(lastPathElement, std::move(newValue));
		};
    };

	basyx::any & HashmapProvider::getModelPropertyValue2(const std::string & path)
	{
		std::cout << "GetPropertyValue: " << path << std::endl;

		// Split path into its elements, separated by '/'
		VABPath pathElements{ path };

		// Get parent of element
		auto * parentElement = getParentElement(path);

		// Return parent element if path was empty, in this case the whole map was requested
		// TODO: Resolve return type mismatch here
		//if (path.empty()) 
		//	return parentElement;

		// Get element from scope
		auto & result = parentElement->at(pathElements.getLastElement());

		// Return element
		return result;
	};

    void HashmapProvider::createValue(const std::string & path, basyx::any && newValue)
    {
        std::cout << "CreateValue1:" << path << " ("
                  << "newValue"
                  << ")" << std::endl;

        VABPath vabPath { path };
        auto elementName = vabPath.getLastElement();

        // Get parent of element
        auto parentElement = getParentElement(path);

        // Check if element is present and a collection, in this case add new element collection
        if (parentElement != nullptr && parentElement->find(elementName) != parentElement->end()) 
		{
            auto & element = parentElement->at(elementName);

            if (element.InstanceOf<basyx::objectCollection_t>()) {
				// add new value to collection
				auto & objectCollection = element.Get<basyx::objectCollection_t&>();
				objectCollection.emplace_back(std::move(newValue));
                return;
            }
        }

        // Target is map, put key and element value into map
        parentElement->emplace(vabPath.getLastElement(), std::move(newValue));
    };

	void HashmapProvider::deleteValue(const std::string & path)
	{
		std::cout << "DeleteValue1:" << path;

		// Split path into its elements, separated by '/'
		VABPath vabPath{ path };
		// - Element name
		const auto & elementName = vabPath.getLastElement();
		// Get parent of element
		auto parentElement = getParentElement(path);

		// Remove named element
		parentElement->erase(parentElement->find(elementName));
	};

	void basyx::provider::HashmapProvider::deleteValue(const std::string & path, basyx::any && deletedValue)
	{
		std::cout << "DeleteValue2: " << path;

		// Split path into its elements, separated by '/'
		VABPath vabPath{ path };
		const auto & elementName = vabPath.getLastElement();

		// Get parent of element
		auto parentElement = getParentElement(path);
		// - Get element
		basyx::any & element = parentElement->at(elementName);

		// Check if element is a collection
		if (element.InstanceOf<basyx::objectCollection_t>())
		{
			auto & collection = element.Get<basyx::objectCollection_t&>();
			auto it = std::find_if(collection.begin(), collection.end(), [&](const basyx::any & any) -> bool { return any == deletedValue; });
			if (it != collection.end())
				collection.erase(it);
		}
		else if (element.InstanceOf<basyx::objectMap_t>())
		{
			auto & objectMap = element.Get<basyx::objectMap_t&>();
			for (auto it = objectMap.begin(); it != objectMap.end(); ++it)
			{
				if (it->second == deletedValue)
				{
					objectMap.erase(it);
					return;
				}
			}

		}
	}

}
}
