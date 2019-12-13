/*
 * ConnectedOperation.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/connected/submodelelement/operation/ConnectedOperation.h>

namespace basyx {
namespace submodel {

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

basyx::specificCollection_t<IOperationVariable> ConnectedOperation::getParameterTypes() const
{
  // todo
  return basyx::specificCollection_t<IOperationVariable>(); // this->getProxyCollection(std::string(submodelelement::operation::OperationPaths::INPUT));
}

std::shared_ptr<IOperationVariable>ConnectedOperation::getReturnType() const
{
  // todo
  return std::shared_ptr<IOperationVariable>(); // this->getProxyCollection(std::string(submodelelement::operation::OperationPaths::INPUT));
}

basyx::object ConnectedOperation::invoke(basyx::object & parameters) const
{
  return this->getProxy()->invoke("", parameters);

}

basyx::object ConnectedOperation::getInvocable() const
{
  // Not supported on remote side.
  return basyx::detail::functionWrapper();
}

}
}
