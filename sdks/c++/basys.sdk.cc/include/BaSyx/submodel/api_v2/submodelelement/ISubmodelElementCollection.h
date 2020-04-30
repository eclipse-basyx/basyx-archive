#ifndef BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_ISUBMODELELEMENTCOLLECTION_H
#define BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_ISUBMODELELEMENTCOLLECTION_H

#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElement.h>
#include <BaSyx/submodel/api_v2/common/IElementContainer.h>


namespace basyx {
namespace submodel {
namespace api {


class ISubmodelElementCollection : public virtual ISubmodelElement
{
public:
	virtual ~ISubmodelElementCollection() = 0;

	virtual IElementContainer<ISubmodelElement> & getSubmodelElements() = 0;

	virtual bool isOrdered() const = 0;
	virtual bool isAllowDuplicates() const = 0;
};

inline ISubmodelElementCollection::~ISubmodelElementCollection() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_ISUBMODELELEMENTCOLLECTION_H */
