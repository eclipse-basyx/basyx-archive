/*
 * ConnectedOperation.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_SUBMODELELEMENT_OPERATION_CONNECTEDOPERATION_H_
#define AAS_BACKEND_SUBMODELELEMENT_OPERATION_CONNECTEDOPERATION_H_

#include "aas/submodelelement/operation/IOperation.h"
#include "vab/core/proxy/IVABElementProxy.h"
#include "backend/connected/aas/submodelelement/ConnectedSubmodelElement.h"

namespace basyx { 
namespace aas {
namespace backend {
namespace connected { 

class ConnectedOperation : public ConnectedSubmodelElement, public submodelelement::operation::IOperation
{
public:
  ConnectedOperation(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
	~ConnectedOperation() = default;

  // Inherited via IOperation
  virtual void setId(const std::string & id) override;
  virtual std::string getId() const override;

  virtual operation_var_list getParameterTypes() const override;
  virtual operation_var_list getReturnTypes() const override;
  virtual basyx::object invoke(basyx::object & parameters) const override;
  virtual basyx::detail::functionWrapper getInvocable() const override;
};
 
}
}
}
}

#endif
