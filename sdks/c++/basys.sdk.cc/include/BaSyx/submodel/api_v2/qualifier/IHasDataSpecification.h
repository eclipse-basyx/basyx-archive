#ifndef BASYX_SUBMODEL_API_V2_QUALIFIER_IHASDATASPECIFICATION_H
#define BASYX_SUBMODEL_API_V2_QUALIFIER_IHASDATASPECIFICATION_H

#include <vector>

namespace basyx {
namespace submodel {

namespace simple { class Reference; }

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
