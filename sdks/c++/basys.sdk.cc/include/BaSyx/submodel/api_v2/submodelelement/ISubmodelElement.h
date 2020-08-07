#ifndef BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_ISUBMODELELEMENT_H
#define BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_ISUBMODELELEMENT_H

#include <BaSyx/submodel/api_v2/qualifier/IQualifiable.h>
#include <BaSyx/submodel/api_v2/qualifier/IHasDataSpecification.h>
#include <BaSyx/submodel/api_v2/qualifier/IHasSemantics.h>
#include <BaSyx/submodel/api_v2/qualifier/IReferable.h>
#include <BaSyx/submodel/api_v2/qualifier/IHasKind.h>
#include <BaSyx/submodel/api_v2/common/IModelType.h>



namespace basyx {
namespace submodel {
namespace api {

class ISubmodelElement
  : public virtual IHasDataSpecification
  , public virtual IReferable
  , public virtual IHasSemantics
  , public virtual IQualifiable
  , public virtual IHasKind
  ,	public virtual IModelType
{
public:
  virtual ~ISubmodelElement() = 0;
};

inline ISubmodelElement::~ISubmodelElement() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_ISUBMODELELEMENT_H */
