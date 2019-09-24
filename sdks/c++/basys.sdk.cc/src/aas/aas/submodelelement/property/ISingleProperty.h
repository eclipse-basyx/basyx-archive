/*
 * ISingleProperty.h
 *
 *      Author: wendel
 */

#ifndef AAS_SUBMODELELEMENT_PROPERTY_ISINGLEPROPERTY_
#define AAS_SUBMODELELEMENT_PROPERTY_ISINGLEPROPERTY_


#include "IProperty.h"
#include "basyx/types.h"


namespace basyx {
namespace aas {
namespace submodelelement {
namespace property {

class ISingleProperty : public IProperty
{

public:
  virtual ~ISingleProperty() = default;

  virtual basyx::any get() = 0;
  virtual void set(basyx::any newValue) = 0;

};

}
}
}
}

#endif