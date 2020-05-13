#ifndef BASYX_SUBMODEL_API_V2_QUALIFIER_IQUALIFIER_H
#define BASYX_SUBMODEL_API_V2_QUALIFIER_IQUALIFIER_H

#include <BaSyx/submodel/api_v2/constraint/IConstraint.h>
#include <BaSyx/submodel/api_v2/qualifier/IHasSemantics.h>
#include <BaSyx/submodel/api_v2/reference/IReference.h>

#include <string>

namespace basyx {
namespace submodel {
namespace api {


class IQualifier : public IConstraint, public virtual IHasSemantics
{
public:
	virtual ~IQualifier() = 0;

	virtual const std::string getQualifierType() const = 0;
	virtual const std::string getValueType() const = 0;

	virtual const std::string * const getValueDataType() const = 0;
	virtual void setValueDataType(const std::string & valueDataType) = 0;

	virtual const IReference * const getValueId() const = 0;
	virtual void setValueId(const IReference & reference) = 0;
};

inline IQualifier::~IQualifier() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_QUALIFIER_IQUALIFIER_H */