/*
 * ConnectedElement.cpp
 *
 *      Author: wendel
 */

#include "ConnectedElement.h"

namespace basyx {
namespace aas {
namespace backend {

using namespace vab::core;

ConnectedElement::ConnectedElement(const std::string & path, const std::shared_ptr<proxy::VABElementProxy> & proxy) :
  proxy(proxy),
  path(path)
{

}

std::shared_ptr<proxy::VABElementProxy> ConnectedElement::getProxy() const
{
  return this->proxy;
}

}
}
}