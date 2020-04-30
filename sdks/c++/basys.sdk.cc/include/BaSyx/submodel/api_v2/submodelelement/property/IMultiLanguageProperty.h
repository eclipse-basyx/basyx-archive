#ifndef BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_PROPERTY_IMULTILANGUAGEPROPERTY_H
#define BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_PROPERTY_IMULTILANGUAGEPROPERTY_H

#include <BaSyx/submodel/api_v2/submodelelement/IDataElement.h>
#include <BaSyx/submodel/api_v2/reference/IReference.h>
#include <BaSyx/submodel/api_v2/common/ILangStringSet.h>

namespace basyx {
namespace submodel {
namespace api {


class IMultiLanguageProperty : public virtual IDataElement
{
public:
	virtual ~IMultiLanguageProperty() = 0;

	virtual ILangStringSet & getValue() = 0;

	virtual const IReference * const getValueId() const = 0;
	virtual void setValueId(const IReference & valueId) = 0;
};

inline IMultiLanguageProperty::~IMultiLanguageProperty() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_PROPERTY_IMULTILANGUAGEPROPERTY_H */
