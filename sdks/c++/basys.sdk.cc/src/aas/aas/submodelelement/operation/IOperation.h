/*
 * IOperation.h
 *
 *      Author: wendel
 */

#ifndef AAS_SUBMODELELEMENT_OPERATION_IOPERATION_
#define AAS_SUBMODELELEMENT_OPERATION_IOPERATION_

#include "aas/submodelelement/IElement.h"
#include "aas/submodelelement/operation/IOperationVariable.h"

#include "basyx/types.h"

#include <vector>

using operation_var_list = basyx::specificCollection_t<basyx::aas::submodelelement::operation::IOperationVariable>;

namespace basyx {
namespace aas {
namespace submodelelement {
namespace operation {

namespace OperationPaths {
  static constexpr char INPUT[] = "in";
  static constexpr char OUTPUT[] = "out";
  static constexpr char INVOKABLE[] = "invokable";
}

class IOperation
{
public:
  virtual operation_var_list getParameterTypes() const = 0;
  virtual operation_var_list getReturnTypes() const = 0;
  virtual std::shared_ptr<basyx::function_base> getInvocable() const = 0;
  virtual basyx::any invoke(basyx::objectCollection_t & parameters) const = 0;
};


}
}
}
}

#endif
