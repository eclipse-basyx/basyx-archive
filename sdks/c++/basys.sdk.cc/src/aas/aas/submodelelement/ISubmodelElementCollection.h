/*
 * ISubmodelElementCollection.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_ISubmodelElementCollection_H_
#define BASYX_METAMODEL_ISubmodelElementCollection_H_


#include "ISubmodelElement.h"
#include "basyx/types.h"

namespace basyx {
namespace aas {
namespace submodelelement {

namespace SubmodelElementCollectionPaths {
  static constexpr long serialVersionUID = 1L;
  static constexpr char ORDERED[] = "ordered";
  static constexpr char ALLOWDUPLICATES[] = "allowDuplicates";
}

class ISubmodelElementCollection
{
public:
  virtual ~ISubmodelElementCollection() = default;

  virtual void setValue(const basyx::objectCollection_t & value) = 0;
  virtual basyx::objectCollection_t getValue() const = 0;

  virtual void setOrdered(const bool & value) = 0;
  virtual bool isOrdered() const = 0;

  virtual void setAllowDuplicates(const bool & value) = 0;
  virtual bool isAllowDuplicates() const = 0;

  virtual void setElements(const basyx::objectMap_t & elements) = 0;
  virtual basyx::objectMap_t getElements() const = 0;
};

}
}
}

#endif

