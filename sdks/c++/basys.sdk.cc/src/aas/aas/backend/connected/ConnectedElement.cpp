/*
 * ConnectedElement.cpp
 *
 *      Author: wendel
 */

#include "ConnectedElement.h"
#include "aas/qualifier/IReferable.h"

namespace basyx {
namespace aas {
namespace backend {

ConnectedElement::ConnectedElement(const std::shared_ptr<vab::core::proxy::IVABElementProxy> & proxy) :
  proxy(proxy),
  local_map(new basyx::objectMap_t)
{}

ConnectedElement::ConnectedElement(const std::shared_ptr<vab::core::proxy::IVABElementProxy>& proxy, std::shared_ptr<basyx::objectMap_t> & local_values) :
  proxy(proxy),
  local_map(local_values)
{}

std::shared_ptr<vab::core::proxy::IVABElementProxy> ConnectedElement::getProxy() const
{
  return this->proxy;
}

basyx::any ConnectedElement::getLocalValue(const std::string & path) const 
{
  auto element_ptr = this->local_map->find(path);
  if ( element_ptr != this->local_map->end() )
    return element_ptr->second;
  return basyx::any(nullptr);
}

void ConnectedElement::setLocalValue(const std::string & path, const basyx::any value)
{
  this->local_map->emplace(path, value);
}

void ConnectedElement::updateLocalValue(const std::string & path, const basyx::any value)
{
  this->local_map->operator[](path) = value;
}


void ConnectedElement::setId(const std::string & id)
{
  this->setProxyValue(qualifier::ReferablePaths::IDSHORT, id);
}

std::string ConnectedElement::getId() const
{
  return this->getProxyValue(qualifier::ReferablePaths::IDSHORT);
}


void ConnectedElement::setProxyValue(const std::string & path, const basyx::any value) const
{
  this->getProxy()->updateElementValue(path, value);
}

std::string ConnectedElement::getProxyValue(const std::string & path) const
{
  auto value = getProxy()->readElementValue(path);
  return value.Get<std::string>();
}

}
}
}