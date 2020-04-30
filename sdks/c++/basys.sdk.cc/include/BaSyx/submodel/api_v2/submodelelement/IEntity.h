#ifndef BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IENTITY_H
#define BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IENTITY_H

#include <BaSyx/submodel/api_v2/submodelelement/ISubmodelElement.h>
#include <BaSyx/submodel/api_v2/common/IElementContainer.h>


namespace basyx {
namespace submodel {
namespace api {


class IEntity : public virtual ISubmodelElement
{
public:
	virtual ~IEntity() = 0;

	virtual IElementContainer<ISubmodelElement> & getStatement() = 0;

	virtual int getEntityType() const = 0;

	virtual const IReference * const getAssetRef() const = 0;
	virtual void setAssetRef(const IReference & assetRef) = 0;
};

inline IEntity::~IEntity() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_IENTITY_H */
