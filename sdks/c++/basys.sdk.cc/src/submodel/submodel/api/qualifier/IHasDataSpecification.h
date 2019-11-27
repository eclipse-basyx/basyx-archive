/*
 * IHasDataSpecification.h
 *
 *      Author: wendel
 */

#ifndef BASYX_METAMODEL_IHasDataSpecification_H_
#define BASYX_METAMODEL_IHasDataSpecification_H_


#include "submodel/api/reference/IReference.h"
#include "submodel/api/identifier/IIdentifier.h"
#include "basyx/types.h"

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

  virtual basyx::specificCollection_t<reference::IReference> getDataSpecificationReferences() const = 0;
};

}
}
}

#endif
