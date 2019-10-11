/*
 * ICollectionProperty.h
 *
 *      Author: wendel
 */

#ifndef AAS_SUBMODELELEMENT_PROPERTY_ICOLLECTIONPROPERTY_
#define AAS_SUBMODELELEMENT_PROPERTY_ICOLLECTIONPROPERTY_

#include "IProperty.h"
#include "basyx/types.h"


namespace basyx {
namespace aas {
namespace submodelelement {
namespace property {

class ICollectionProperty : public IProperty
{

public:
  virtual ~ICollectionProperty() = default;

  virtual void set(basyx::objectCollection_t collection) = 0;
  virtual void add(basyx::any newValue) = 0;
  virtual void remove(basyx::any objectRef) = 0;
  virtual basyx::objectCollection_t getElements() = 0;
  virtual int getElementCount() = 0;
};

}
}
}
}

#endif