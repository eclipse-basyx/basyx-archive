/*
 * ConnectedElement.cpp
 *
 *      Author: wendel
 */

#include "ConnectedElement.h"

ConnectedElement::ConnectedElement(const std::string & path, const std::shared_ptr<VABElementProxy> & proxy) :
  proxy(proxy),
  path(path)
{

}

std::shared_ptr<VABElementProxy> ConnectedElement::getProxy() const
{
  return this->proxy;
}

