/*
 * IContainernProperty.h
 *
 *      Author: wendel
 */

#ifndef AAS_SUBMODELELEMENT_PROPERTY_ICONTAINERPROPERTY_
#define AAS_SUBMODELELEMENT_PROPERTY_ICONTAINERPROPERTY_


#include <BaSyx/submodel/api/submodelelement/property/IProperty.h>
#include <BaSyx/shared/types.h>
#include <BaSyx/aas/backend/vab/IVABElementContainer.h>


namespace basyx {
namespace submodel {
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
