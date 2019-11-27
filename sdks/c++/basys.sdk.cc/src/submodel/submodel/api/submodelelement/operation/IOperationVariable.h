/*
 * IOperationVariable.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IOperationVariable_H_
#define BASYX_METAMODEL_IOperationVariable_H_


#include "submodel/api/submodelelement/ISubmodelElement.h"

#include <memory>

namespace basyx {
namespace aas {
namespace submodelelement {
namespace operation {

class IOperationVariable
{
public:
  virtual ~IOperationVariable() = default;

  virtual std::shared_ptr<ISubmodelElement> getValue() const = 0;
};

}
}
}
}

#endif

