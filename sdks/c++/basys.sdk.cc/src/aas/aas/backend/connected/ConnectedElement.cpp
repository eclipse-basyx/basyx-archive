/*
 * ConnectedElement.cpp
 *
 *      Author: wendel
 */

#include "ConnectedElement.h"

namespace basyx {
namespace aas {
namespace backend {

ConnectedElement::ConnectedElement(const std::shared_ptr<vab::core::proxy::IVABElementProxy> & proxy) :
  proxy(std::move(proxy))
{

}

ConnectedElement::ConnectedElement(const std::shared_ptr<vab::core::proxy::IVABElementProxy>& proxy, basyx::objectMap_t & local_values) :
  proxy(proxy),
  local_map(local_values)
{}

std::shared_ptr<vab::core::proxy::IVABElementProxy> ConnectedElement::getProxy() const
{
  return this->proxy;
}

basyx::any ConnectedElement::getLocalValue(const std::string & path) const 
{
  auto element_ptr = this->local_map.find(path);
  if ( element_ptr != this->local_map.end() )
    return *element_ptr;
  return nullptr;
}

void ConnectedElement::setLocalValue(const std::string & path, const basyx::any value)
{
  this->local_map.emplace(path, value);
}

void ConnectedElement::updateLocalValue(const std::string & path, const basyx::any value)
{
  this->local_map.emplace(path, value);
}

}
}
}