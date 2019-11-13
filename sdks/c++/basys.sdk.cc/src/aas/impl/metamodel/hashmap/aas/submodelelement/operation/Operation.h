/*
 * Operation.h
 *
 *      Author: wendel
 */

#ifndef SUBMODEL_METAMODEL_SUBMODELEMENT_OPERATION_OPERATION_H_
#define SUBMODEL_METAMODEL_SUBMODELEMENT_OPERATION_OPERATION_H_

#include "basyx/types.h"
#include "OperationVariable.h"
#include "aas/submodelelement/operation/IOperationVariable.h"
#include "impl/metamodel/hashmap/aas/submodelelement/SubmodelElement.h"
#include "aas/submodelelement/operation/IOperation.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace submodelelement {
namespace operation {

class Operation : public SubmodelElement, public aas::submodelelement::operation::IOperation
{
public:
  ~Operation() = default;

  // constructors
  Operation();
  Operation(operation_var_list in, operation_var_list out, std::shared_ptr<basyx::function_base> invocable);

  // Inherited via IOperation
  virtual operation_var_list getParameterTypes() const override;
  virtual operation_var_list getReturnTypes() const override;
  virtual std::shared_ptr<basyx::function_base> getInvocable() const override;
  virtual basyx::any invoke(basyx::objectCollection_t & parameters) const override;

  // not inherited
  void setParameterTypes(const operation_var_list & parameterTypes);
  void setReturnTypes(const operation_var_list & returnTypes);
  void setInvocable(const std::shared_ptr<basyx::function_base> & invocable);
  
private:
  operation_var_list in_variables, out_variables;
  std::shared_ptr<basyx::function_base> invocable;
};

}
}
}
}
}
}

#endif
