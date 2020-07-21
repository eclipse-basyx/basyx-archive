#ifndef BASYX_API_V2_SDK_IVIEW_H
#define BASYX_API_V2_SDK_IVIEW_H

#include <BaSyx/submodel/api_v2/qualifier/IReferable.h>
#include <BaSyx/submodel/api_v2/qualifier/IHasDataSpecification.h>
#include <BaSyx/submodel/api_v2/qualifier/IHasSemantics.h>
#include <BaSyx/submodel/api_v2/common/IElementContainer.h>

namespace basyx {
namespace submodel {
namespace api {

class IView
  : public virtual IHasDataSpecification
  , public virtual IReferable
  , public virtual IHasSemantics
{
public:
  virtual ~IView() = 0;

  virtual const IElementContainer<IReferable> & getContainedElements() const = 0;
};

inline IView::~IView() = default;

}
}
}
#endif //BASYX_API_V2_SDK_IVIEW_H
