#include <BaSyx/vab/provider/VABModelProvider.h>


using namespace basyx::vab::provider;

VABModelProvider::VABModelProvider(const basyx::object& any) 
	: elements(any) 
{
};

basyx::object VABModelProvider::getParentElement(const std::string& path)
{
	core::VABPath vabPath{ path };

	auto currentElement = elements;

	// ignore the leaf element, only return the leaf's parent element
	const auto & pathElements = vabPath.getElements();
	for(std::size_t i=0; i < pathElements.size()-1; ++i)
	{
		if (currentElement.IsNull())
			return basyx::object::make_null();

		currentElement = currentElement.getProperty(pathElements[i]); //  handler.GetElementProperty(currentElement, pathElements[i]);
	}

	return currentElement;
};

basyx::object VABModelProvider::getTargetElement(const std::string& path)
{
	core::VABPath vabPath{ path };
	if (vabPath.isEmpty()) {
		return elements;
	}
	else
	{
		auto parentElement = getParentElement(path);
		auto operationName = vabPath.getLastElement();
		if (!parentElement.IsNull() && !operationName.empty()) {
			//return handler.GetElementProperty(parentElement, operationName);
			return parentElement.getProperty(operationName);
		}
	};

	return basyx::object::make_null();
};

basyx::object VABModelProvider::getModelPropertyValue(const std::string& path)
{
	core::VABPath vabPath{ path };
	auto & lastPathElement = vabPath.getLastElement();

	if (vabPath.isEmpty()) {
		//return handler.PostProcessObject(elements);
		return elements;
	}

	auto element = getTargetElement(path);
//			return handler.PostProcessObject(element);
	return element;
}

void VABModelProvider::setModelPropertyValue(const std::string& path, const basyx::object newValue)
{
	core::VABPath vabPath{ path };

	// Check empty paths
	if (vabPath.isEmpty()) {
		// Empty path => parent element == null => replace root, if it exists
		if (!elements.IsNull()) {
			elements = newValue;
		}
		return;
	}

	auto parentElement = this->getParentElement(path);
	auto & propertyName = vabPath.getLastElement();

	// Only write values, that already exist
	auto thisElement = parentElement.getProperty(propertyName);

	if (!parentElement.IsNull() && !thisElement.IsNull()) {
		parentElement.insertKey(propertyName, newValue, true);
	}
};

void VABModelProvider::createValue(const std::string& path, const basyx::object newValue)
{
	core::VABPath vabPath{ path };

	// Check empty paths
	if (vabPath.isEmpty()) {
		// The complete model should be replaced if it does not exist
		if (elements.IsNull()) {
			elements = newValue;
		}
		return;
	}

	// Find parent & name of new element
	auto parentElement = this->getParentElement(path);
	auto & propertyName = vabPath.getLastElement();

	// Only create new, never replace existing elements
	if(!parentElement.IsNull()) {
		auto childElement = parentElement.getProperty(propertyName);
		if (childElement.IsNull()) {
			parentElement.insertKey(propertyName, newValue);
		}
		else {
			childElement.insert(newValue);
		}
		return;
	}

	log.warn("Could not create element, parent element does not exist for path '{}'", path);
};

void VABModelProvider::deleteValue(const std::string& path, basyx::object deletedValue)
{
	core::VABPath vabPath{ path };

	if (vabPath.isEmpty())
		return;

	// Find parent & name of element
	auto parentElement = this->getParentElement(path);
	auto & propertyName = vabPath.getLastElement();

	if (!parentElement.IsNull()) {
		//auto & childElement = handler.GetElementProperty(parentElement, propertyName);
		auto childElement = parentElement.getProperty(propertyName);


		if (!childElement.IsNull()) {
			//handler.DeleteValue(childElement, deletedValue);
			childElement.remove(deletedValue);
		}
	}
};

void VABModelProvider::deleteValue(const std::string& path)
{
	core::VABPath vabPath{ path };

	// Find parent & name of element
	auto parentElement = this->getParentElement(path);
	auto & propertyName = vabPath.getLastElement();

	if (!parentElement.IsNull()) {
		//handler.DeleteProperty(parentElement, propertyName);
		parentElement.removeProperty(propertyName);
	}
};

basyx::object VABModelProvider::invokeOperation(const std::string& path, basyx::object parameters)
{
	log.trace("invokeOperationImpl called");
	log.trace("    path: \"{}\"", path);
	log.trace("    parameters: <not impl>");

	auto element = this->getModelPropertyValue(path);

	if (element.IsNull()) {
		log.error("Function not found!");
		log.trace("Returning basyx::object::null");

		return basyx::object{ nullptr };
	}

	if (!element.IsInvokable()) {
		log.error("Found object is not invokable!");
		log.trace("Returning basyx::object::null");

		return basyx::object{ nullptr };
	};

	log.trace("Function found. Invoking...");

	return element.invoke(parameters);
};