/*
 * OperationVariable.h
 *
 *      Author: wendel
 */

#ifndef SUBMODEL_METAMODEL_SUBMODELEMENT_OPERATION_OPERATIONVARIABLE_H_
#define SUBMODEL_METAMODEL_SUBMODELEMENT_OPERATION_OPERATIONVARIABLE_H_

#include "aas/submodelelement/operation/IOperationVariable.h"
#include "impl/metamodel/hashmap/aas/submodelelement/SubmodelElement.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace submodelelement {
namespace operation {

class OperationVariable : public SubmodelElement, public aas::submodelelement::operation::IOperationVariable
{
public:
  ~OperationVariable() = default;
  // constructors
  OperationVariable();
  OperationVariable(const std::shared_ptr<aas::submodelelement::ISubmodelElement> & value);

  // Inherited via IOperationVariable
  virtual std::shared_ptr<aas::submodelelement::ISubmodelElement> getValue() const override;

  // not inherited
  void setValue(const std::shared_ptr<aas::submodelelement::ISubmodelElement> & value);

private:
  std::shared_ptr<aas::submodelelement::ISubmodelElement> value;
};

}
}
}
}
}
}

#endif
