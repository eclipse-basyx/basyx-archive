/*
 * ConnectedOperation.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_SUBMODELELEMENT_OPERATION_CONNECTEDOPERATION_H_
#define AAS_BACKEND_SUBMODELELEMENT_OPERATION_CONNECTEDOPERATION_H_

#include <BaSyx/submodel/api/submodelelement/operation/IOperation.h>
#include <BaSyx/vab/core/proxy/IVABElementProxy.h>
#include <BaSyx/submodel/connected/submodelelement/ConnectedSubmodelElement.h>

namespace basyx { 
namespace submodel {

class ConnectedOperation : public ConnectedSubmodelElement, public IOperation
{
public:
  ConnectedOperation(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
	~ConnectedOperation() = default;

  // Inherited via IOperation
  virtual void setId(const std::string & id) override;
  virtual std::string getId() const override;

  virtual basyx::specificCollection_t<IOperationVariable> getParameterTypes() const override;
  virtual std::shared_ptr<IOperationVariable> getReturnType() const override;
  virtual basyx::object invoke(basyx::object & parameters) const override;
  virtual basyx::object getInvocable() const override;
};
 
}
}

#endif
