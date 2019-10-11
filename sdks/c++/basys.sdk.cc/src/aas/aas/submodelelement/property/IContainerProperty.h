/*
 * IContainernProperty.h
 *
 *      Author: wendel
 */

#ifndef AAS_SUBMODELELEMENT_PROPERTY_ICONTAINERPROPERTY_
#define AAS_SUBMODELELEMENT_PROPERTY_ICONTAINERPROPERTY_


#include "IProperty.h"
#include "basyx/types.h"
#include "aas/backend/vab/IVABElementContainer.h"


namespace basyx {
namespace aas {
namespace submodelelement {
namespace property {

class IContainerProperty : public IProperty, vab::IVABElementContainer
{

public:
  virtual ~IContainerProperty() = default;

};

}
}
}
}

#endif