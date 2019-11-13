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

  virtual void setValue(const basyx::specificCollection_t<aas::submodelelement::ISubmodelElement> & value) = 0;
  virtual basyx::specificCollection_t<aas::submodelelement::ISubmodelElement> getValue() const = 0;

  virtual void setOrdered(const bool & value) = 0;
  virtual bool isOrdered() const = 0;

  virtual void setAllowDuplicates(const bool & value) = 0;
  virtual bool isAllowDuplicates() const = 0;

  virtual void setElements(const basyx::specificMap_t<aas::submodelelement::ISubmodelElement> & value) = 0;
  virtual basyx::specificMap_t<aas::submodelelement::ISubmodelElement> getElements() const = 0;
};

}
}
}

#endif

