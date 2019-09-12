/*
 * ConnectedSubmodelElement.cpp
 *
 *      Author: wendel
 */

#include "ConnectedSubmodelElement.h"
#include "aas/backend/connected/ConnectedElement.h"

basyx::aas::submodelelement::connected::ConnectedSubmodelElement::ConnectedSubmodelElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  backend::ConnectedElement(backend::ConnectedElement(proxy))
{}
