/*
 * IHasDataSpecification.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IHasDataSpecification_H_
#define BASYX_METAMODEL_IHasDataSpecification_H_


#include "aas/reference/IReference.h"
#include "basyx/types.h"

#include <memory>
#include <vector>

namespace basyx {
namespace aas {
namespace qualifier {

namespace HasDataSpecificationPaths {
  static constexpr char HASDATASPECIFICATION[] = "hasDataSpecification";
}

class IHasDataSpecification
{
public:
  virtual ~IHasDataSpecification() = default;

  virtual basyx::objectCollection_t getDataSpecificationReferences() const = 0;
};

}
}
}

#endif
