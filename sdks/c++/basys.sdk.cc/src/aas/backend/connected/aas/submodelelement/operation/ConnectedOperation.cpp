/*
 * ConnectedOperation.cpp
 *
 *      Author: wendel
 */

#include "ConnectedOperation.h"

namespace basyx {
namespace aas {
namespace backend {
namespace connected {

ConnectedOperation::ConnectedOperation(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy) :
  ConnectedSubmodelElement(proxy)
{}

void ConnectedOperation::setId(const std::string & id)
{
  this->setIdWithLocalCheck(id);
}

std::string ConnectedOperation::getId() const
{
  return this->getIdWithLocalCheck();
}

basyx::objectCollection_t ConnectedOperation::getParameterTypes() const
{
  return this->getProxyCollection(std::string(submodelelement::operation::OperationPaths::INPUT));
}

basyx::objectCollection_t ConnectedOperation::getReturnTypes() const
{
  return this->getProxyCollection(std::string(submodelelement::operation::OperationPaths::OUTPUT));
}

basyx::objectMap_t ConnectedOperation::getInvocable() const
{
  return basyx::objectMap_t();
}

basyx::any ConnectedOperation::invoke(basyx::objectCollection_t & parameters) const
{
  return this->getProxy()->invoke("", parameters);

}

}
}
}
}

