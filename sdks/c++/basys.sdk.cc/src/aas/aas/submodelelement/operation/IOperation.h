/*
 * IOperation.h
 *
 *      Author: wendel
 */

#ifndef AAS_SUBMODELELEMENT_OPERATION_IOPERATION
#define AAS_SUBMODELELEMENT_OPERATION_IOPERATION

#include "aas/submodelelement/IElement.h"
#include "aas/submodelelement/operation/IOperationVariable.h"

#include "basyx/types.h"

#include <vector>

namespace basyx {
namespace aas {
namespace submodelelement {
namespace operation {

namespace OperationPaths {
  static constexpr char IN[] = "in";
  static constexpr char OUT[] = "out";
  static constexpr char INVOKABLE[] = "invokable";
}


class IOperation : public IElement
{
public:
  virtual basyx::objectCollection_t getParameterTypes() const = 0;

  virtual void setReturnTypes(const basyx::objectCollection_t & out) = 0;
  virtual basyx::objectCollection_t getReturnTypes() const = 0;

  virtual basyx::objectMap_t getInvocable() const = 0;

  virtual basyx::any invoke(const basyx::objectCollection_t & parameters) const = 0;
};


}
}
}
}

#endif
