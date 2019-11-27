/*
 * IOperation.h
 *
 *      Author: wendel
 */

#ifndef AAS_SUBMODELELEMENT_OPERATION_IOPERATION_
#define AAS_SUBMODELELEMENT_OPERATION_IOPERATION_

#include "submodel/api/IElement.h"
#include "submodel/api/submodelelement/operation/IOperationVariable.h"

#include "basyx/object.h"
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
  virtual basyx::detail::functionWrapper getInvocable() const = 0;
  virtual basyx::object invoke(basyx::object & parameters) const = 0;
};


}
}
}
}

#endif
