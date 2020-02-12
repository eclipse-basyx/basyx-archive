/*
 * ISubmodelElementCollection.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_ISubmodelElementCollection_H_
#define BASYX_METAMODEL_ISubmodelElementCollection_H_


#include <BaSyx/submodel/api/submodelelement/ISubmodelElement.h>
#include <BaSyx/shared/types.h>

namespace basyx {
namespace submodel {

namespace SubmodelElementCollectionPaths {
  static constexpr long serialVersionUID = 1L;
  static constexpr char ORDERED[] = "ordered";
  static constexpr char ALLOWDUPLICATES[] = "allowDuplicates";
}

class ISubmodelElementCollection
{
public:
  virtual ~ISubmodelElementCollection() = default;

  virtual void setValue(const basyx::specificCollection_t<ISubmodelElement> & value) = 0;
  virtual basyx::specificCollection_t<ISubmodelElement> getValue() const = 0;

  virtual void setOrdered(const bool & value) = 0;
  virtual bool isOrdered() const = 0;

  virtual void setAllowDuplicates(const bool & value) = 0;
  virtual bool isAllowDuplicates() const = 0;

  virtual void setElements(const basyx::specificMap_t<ISubmodelElement> & value) = 0;
  virtual basyx::specificMap_t<ISubmodelElement> getElements() const = 0;
};

}
}

#endif

