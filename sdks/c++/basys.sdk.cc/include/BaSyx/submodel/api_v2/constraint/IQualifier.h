#ifndef BASYX_SUBMODEL_API_V2_CONSTRAINT_IQUALIFIER_H
#define BASYX_SUBMODEL_API_V2_CONSTRAINT_IQUALIFIER_H

#include <BaSyx/submodel/api_v2/constraint/IConstraint.h>
#include <BaSyx/submodel/api_v2/qualifier/IHasSemantics.h>
#include <BaSyx/submodel/api_v2/reference/IReference.h>
#include <BaSyx/submodel/api_v2/submodelelement/IRange.h>

#include <string>

namespace basyx {
namespace submodel {

using QualifierType = std::string;

namespace api {

class IQualifier : public IConstraint, public virtual IHasSemantics
{
public:
	virtual ~IQualifier() = 0;

	virtual const QualifierType getQualifierType() const = 0;
	virtual const DataTypeDef getValueType() const = 0;

	virtual const ValueDataType * const getValueDataType() const = 0;
	virtual void setValueDataType(const ValueDataType & valueDataType) = 0;

	virtual const IReference * const getValueId() const = 0;
	virtual void setValueId(const IReference & reference) = 0;
};

inline IQualifier::~IQualifier() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_CONSTRAINT_IQUALIFIER_H */
