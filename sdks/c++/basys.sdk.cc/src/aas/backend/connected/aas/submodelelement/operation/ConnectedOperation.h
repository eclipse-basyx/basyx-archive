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

class ConnectedOperation : ConnectedSubmodelElement, submodelelement::operation::IOperation
{
public:
  ConnectedOperation(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
	~ConnectedOperation() = default;

  // Inherited via IOperation
  virtual void setId(const std::string & id) override;
  virtual std::string getId() const override;

  virtual basyx::objectCollection_t getParameterTypes() const override;
  virtual basyx::objectCollection_t getReturnTypes() const override;
  virtual basyx::objectMap_t getInvocable() const override;
  virtual basyx::any invoke(basyx::objectCollection_t & parameters) const override;
};
 
}
}
}
}

#endif
