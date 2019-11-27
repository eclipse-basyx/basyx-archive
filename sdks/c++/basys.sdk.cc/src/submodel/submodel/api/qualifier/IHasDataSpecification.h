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
namespace submodel {

class IHasDataSpecification
{
public:
	struct Path {
		static constexpr char HasDataSpecification[] = "hasDataSpecification";
	};
public:
  virtual ~IHasDataSpecification() = default;

  virtual basyx::specificCollection_t<IReference> getDataSpecificationReferences() const = 0;
};

}
}

#endif