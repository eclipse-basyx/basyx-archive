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

namespace basyx {
namespace aas {
namespace submodelelement {
namespace operation {

namespace OperationPaths {
  static constexpr char INPUT[] = "in";
  static constexpr char OUTPUT[] = "out";
  static constexpr char INVOKABLE[] = "invokable";
}


class IOperation : public IElement
{
public:
  virtual basyx::objectCollection_t getParameterTypes() const = 0;
  virtual basyx::objectCollection_t getReturnTypes() const = 0;
  virtual basyx::objectMap_t getInvocable() const = 0;
  virtual basyx::any invoke(basyx::objectCollection_t & parameters) const = 0;
};


}
}
}
}

#endif
