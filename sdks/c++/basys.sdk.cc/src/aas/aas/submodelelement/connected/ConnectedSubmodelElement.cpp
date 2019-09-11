/*
 * ConnectedSubmodelElement.cpp
 *
 *      Author: wendel
 */

#include "ConnectedSubmodelElement.h"
#include "aas/backend/connected/ConnectedElement.h"

basyx::aas::submodelelement::connected::ConnectedSubmodelElement::ConnectedSubmodelElement(std::shared_ptr<vab::core::proxy::VABElementProxy> proxy) :
  backend::ConnectedElement(backend::ConnectedElement(proxy))
{}
