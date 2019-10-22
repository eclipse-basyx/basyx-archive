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

class ICollectionProperty
{

public:
  virtual ~ICollectionProperty() = default;

  virtual void set(const basyx::objectCollection_t & collection) const = 0;
  virtual void add(const basyx::any & newValue) = 0;
  virtual void remove(const basyx::any & objectRef) = 0;
  virtual basyx::objectCollection_t getElements() const = 0;
  virtual int getElementCount() const = 0;
};

}
}
}
}

#endif