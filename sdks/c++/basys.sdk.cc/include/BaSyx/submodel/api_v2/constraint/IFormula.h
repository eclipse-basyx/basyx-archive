#ifndef BASYX_SUBMODEL_API_V2_CONSTRAINT_IFORMULA_H
#define BASYX_SUBMODEL_API_V2_CONSTRAINT_IFORMULA_H

#include <BaSyx/submodel/api_v2/constraint/IConstraint.h>
#include <BaSyx/submodel/simple/reference/Reference.h>

#include <vector>

namespace basyx {
namespace submodel {
namespace api {


class IFormula : public IConstraint
{
public:
	virtual ~IFormula() = 0;

	virtual std::vector<simple::Reference> getDependencies() const = 0;
	virtual void addDependency(const IReference & reference) = 0;
};

inline IFormula::~IFormula() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_CONSTRAINT_IFORMULA_H */
