/*
 * VABElementProxy.cpp
 *
 *      Author: wendel
 */

#include "VABElementProxy.h"

namespace basyx {
namespace vab {
namespace core {
namespace proxy {

VABElementProxy::VABElementProxy(std::string address, std::shared_ptr<IModelProvider> provider) :
  address(address),
  provider(provider)
{
}

VABElementProxy::~VABElementProxy()
{
}

basyx::object VABElementProxy::readElementValue(const VABPath & elementPath)
{
  return this->provider->getModelPropertyValue(this->get_ablsolute_path(elementPath));
}

void VABElementProxy::updateElementValue(const VABPath & elementPath, const basyx::object & newValue)
{
  this->provider->setModelPropertyValue(this->get_ablsolute_path(elementPath), newValue);
}

void VABElementProxy::createElement(const VABPath & elementPath, const basyx::object & newValue)
{
  this->provider->createValue(this->get_ablsolute_path(elementPath), newValue);
}

void VABElementProxy::deleteElement(const VABPath & elementPath)
{
  this->provider->deleteValue(this->get_ablsolute_path(elementPath));
}

void VABElementProxy::deleteElement(const VABPath & elementPath, basyx::object & value)
{
  this->provider->deleteValue(this->get_ablsolute_path(elementPath), value);
}

basyx::object VABElementProxy::invoke(const VABPath & elementPath, basyx::object & parameter)
{
	return this->provider->invokeOperation(this->get_ablsolute_path(elementPath), parameter);
}

VABPath VABElementProxy::get_ablsolute_path(const VABPath & elementPath)
{
	return this->address + elementPath;
}

}
}
}
}