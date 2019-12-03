/*
 * ConnectedElement.cpp
 *
 *      Author: wendel
 */

#include "ConnectedElement.h"
#include "submodel/api/qualifier/IReferable.h"

namespace basyx {
namespace submodel {

ConnectedElement::ConnectedElement(const std::shared_ptr<vab::core::proxy::IVABElementProxy> & proxy) :
  proxy(proxy),
  local_map(new basyx::object::object_map_t)
{}

ConnectedElement::ConnectedElement(const std::shared_ptr<vab::core::proxy::IVABElementProxy>& proxy, std::shared_ptr<basyx::object::object_map_t> & local_values) :
  proxy(proxy),
  local_map(local_values)
{}

std::shared_ptr<vab::core::proxy::IVABElementProxy> ConnectedElement::getProxy() const
{
  return this->proxy;
}

basyx::object ConnectedElement::getLocalValue(const std::string & path) const 
{
  auto element_ptr = this->local_map->find(path);
  if ( element_ptr != this->local_map->end() )
    return element_ptr->second;
  return basyx::object(nullptr);
}

void ConnectedElement::setLocalValue(const std::string & path, const basyx::object & value)
{
  this->local_map->emplace(path, value);
}

void ConnectedElement::setLocalValues(const basyx::object::object_map_t & values)
{
  for ( auto & val : values )
  {
    this->local_map->emplace(val.first, val.second);
  }
}

void ConnectedElement::updateLocalValue(const std::string & path, const basyx::object value)
{
  this->local_map->operator[](path) = value;
}


void ConnectedElement::setId(const std::string & id)
{
  this->setProxyValue(IReferable::Path::IdShort, id);
}

std::string ConnectedElement::getId() const
{
  return this->getProxyValue(IReferable::Path::IdShort);
}


void ConnectedElement::setProxyValue(const std::string & path, const basyx::object value) const
{
  this->getProxy()->updateElementValue(path, value);
}

std::string ConnectedElement::getProxyValue(const std::string & path) const
{
  auto value = getProxy()->readElementValue(path);
  return value.Get<std::string>();
}

std::shared_ptr<basyx::object::object_map_t> ConnectedElement::getProxyMap(const std::string & path) const
{
  auto value = getProxy()->readElementValue(path);
  return std::make_shared<basyx::object::object_map_t>(value.Get<basyx::object::object_map_t>());
}

}
}
