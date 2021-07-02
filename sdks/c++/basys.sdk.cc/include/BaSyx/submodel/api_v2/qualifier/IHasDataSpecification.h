#ifndef BASYX_SUBMODEL_API_V2_QUALIFIER_IHASDATASPECIFICATION_H
#define BASYX_SUBMODEL_API_V2_QUALIFIER_IHASDATASPECIFICATION_H

#include <vector>
#include <BaSyx/submodel/simple/reference/Reference.h>

namespace basyx {
namespace submodel {
namespace api {

class IHasDataSpecification
{
public:
	virtual ~IHasDataSpecification() = default;

	virtual void addDataSpecification(const simple::Reference & reference) = 0;
	virtual const std::vector<simple::Reference> getDataSpecificationReference() const = 0;
};

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_QUALIFIER_IHASDATASPECIFICATION_H */
