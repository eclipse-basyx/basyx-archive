#ifndef BASYX_SUBMODEL_API_V2_QUALIFIER_IQUALIFIABLE_H
#define BASYX_SUBMODEL_API_V2_QUALIFIER_IQUALIFIABLE_H

#include <BaSyx/submodel/api_v2/constraint/IFormula.h>

#include <BaSyx/submodel/simple/constraint/Formula.h>

#include <vector>

namespace basyx {
namespace submodel {

namespace simple { class Qualifier; }

namespace api {

class IQualifier;

class IQualifiable
{
public:
	virtual ~IQualifiable() = 0;

	virtual void addFormula(const IFormula & formula) = 0;
	virtual void addQualifier(const IQualifier & qualifier) = 0;

	virtual std::vector<simple::Formula> getFormulas() const = 0;
	virtual std::vector<simple::Qualifier> getQualifiers() const = 0;
};

inline IQualifiable::~IQualifiable() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_QUALIFIER_IQUALIFIABLE_H */