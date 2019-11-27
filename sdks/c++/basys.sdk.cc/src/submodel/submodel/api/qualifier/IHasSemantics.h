/*
 * IHasSemantics.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IHasSemantics_H_
#define BASYX_METAMODEL_IHasSemantics_H_

#include <memory>
#include "submodel/api/reference/IReference.h"
#include "basyx/types.h"

namespace basyx {
namespace aas {
namespace qualifier {

class IHasSemantics
{
public:
  virtual ~IHasSemantics() = default;

  virtual std::shared_ptr<reference::IReference> getSemanticId() const = 0;
};

}
}
}

#endif
