/*
 * SubmodelElementCollection.h
 *
 *      Author: wendel
 */

#ifndef IMPL_METAMODEL_MAP_AAS_SUBMODELELEMENT_SUBMODELELEMENTCOLLECTION_H_
#define IMPL_METAMODEL_MAP_AAS_SUBMODELELEMENT_SUBMODELELEMENTCOLLECTION_H_

#include "SubmodelElement.h"
#include "aas/submodelelement/ISubmodelElementCollection.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace submodelelement {

class SubmodelElementCollection : public SubmodelElement, public aas::submodelelement::ISubmodelElementCollection
{
public:
  ~SubmodelElementCollection() = default;

  // constructors
  /**
   * Default constructor, sets ordered and allowDuplicates to false.
   */
  SubmodelElementCollection();

  /**
   * @param value
   *            Submodel element contained in the collection
   * @param ordered
   *            If ordered=false then the elements in the property collection are
   *            not ordered. If ordered=true then the elements in the collection
   *            are ordered.
   * @param allowDuplicates
   *            If allowDuplicates=true then it is allowed that the collection
   *            contains the same element several times
   */
  SubmodelElementCollection(const basyx::specificCollection_t<aas::submodelelement::ISubmodelElement> & value, const bool ordered, const bool allowDuplicates);

  // Inherited via ISubmodelElementCollection
  virtual void setValue(const basyx::specificCollection_t<aas::submodelelement::ISubmodelElement> & value) override;
  virtual basyx::specificCollection_t<aas::submodelelement::ISubmodelElement> getValue() const override;
  virtual void setOrdered(const bool & value) override;
  virtual bool isOrdered() const override;
  virtual void setAllowDuplicates(const bool & value) override;
  virtual bool isAllowDuplicates() const override;
  virtual void setElements(const basyx::specificMap_t<aas::submodelelement::ISubmodelElement> & value) override;
  virtual basyx::specificMap_t<aas::submodelelement::ISubmodelElement> getElements() const override;

private:
  bool ordered, allowDuplicates;
  basyx::specificCollection_t<aas::submodelelement::ISubmodelElement> value;
  basyx::specificMap_t<aas::submodelelement::ISubmodelElement> elements;

  void orderElements();
};

}
}
}
}
}

#endif
