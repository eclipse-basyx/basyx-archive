/*
 * IHasDataSpecification.h
 *
 *      Author: wendel
 */

#ifndef BASYX_METAMODEL_IHasDataSpecification_H_
#define BASYX_METAMODEL_IHasDataSpecification_H_


#include <BaSyx/submodel/api/reference/IReference.h>
#include <BaSyx/submodel/api/identifier/IIdentifier.h>
#include <BaSyx/shared/types.h>

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
