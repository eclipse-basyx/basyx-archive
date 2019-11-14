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

operation_var_list ConnectedOperation::getParameterTypes() const
{
  // todo
  return operation_var_list(); // this->getProxyCollection(std::string(submodelelement::operation::OperationPaths::INPUT));
}

operation_var_list ConnectedOperation::getReturnTypes() const
{
  // todo
  return operation_var_list(); // this->getProxyCollection(std::string(submodelelement::operation::OperationPaths::INPUT));
}

basyx::any ConnectedOperation::invoke(basyx::objectCollection_t & parameters) const
{
  return this->getProxy()->invoke("", parameters);

}

std::shared_ptr<basyx::function_base> ConnectedOperation::getInvocable() const
{
  // Not supported on remote side.
  return nullptr;
}

}
}
}
}

