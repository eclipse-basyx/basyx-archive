/*
 * ISubmodelElement.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_ISUBMODELELEMENT_H_
#define BASYX_METAMODEL_ISUBMODELELEMENT_H_


#include "submodel/api/IElement.h"
#include "submodel/api/qualifier/IHasDataSpecification.h"
#include "submodel/api/qualifier/IReferable.h"
#include "submodel/api/qualifier/qualifiable/IQualifiable.h"
#include "submodel/api/qualifier/IHasSemantics.h"
#include "submodel/api/qualifier/haskind/IHasKind.h"

namespace basyx {
namespace aas {
namespace submodelelement {

class ISubmodelElement : public qualifier::IHasDataSpecification, qualifier::IReferable, qualifier::qualifiable::IQualifiable, qualifier::IHasSemantics, qualifier::haskind::IHasKind
{
private:
  basyx::object::object_map_t hashMap;
public:
  virtual ~ISubmodelElement() = default;
};

}
}
}

#endif
