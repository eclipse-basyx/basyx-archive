/*
 * SubmodelElement.h
 *
 *      Author: wendel
 */

#ifndef IMPL_METAMODEL_MAP_AAS_SUBMODELELEMENT_SUBMODELELEMENT_H_
#define IMPL_METAMODEL_MAP_AAS_SUBMODELELEMENT_SUBMODELELEMENT_H_

#include "submodel/api/submodelelement/ISubmodelElement.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace submodelelement {

class SubmodelElement : public aas::submodelelement::ISubmodelElement
{
public:
  ~SubmodelElement() = default;

  // constructors
  SubmodelElement();

  /**
   * Constructs an SubmodelElement object from a map given that the map contains all required elements.
   * 
   * @param submodelElementMap the map representig the submodel.
   */
  SubmodelElement(const basyx::object::object_map_t & submodelElementMap);

  // Inherited via ISubmodelElement
  virtual basyx::specificCollection_t<aas::reference::IReference> getDataSpecificationReferences() const override;
  virtual std::string getIdShort() const override;
  virtual std::string getCategory() const override;
  virtual aas::qualifier::impl::Description getDescription() const override;
  virtual std::shared_ptr<aas::reference::IReference> getParent() const override;
  virtual basyx::specificCollection_t<aas::qualifier::qualifiable::IConstraint> getQualifier() const override;
  virtual std::shared_ptr<aas::reference::IReference> getSemanticId() const override;
  virtual qualifier::haskind::Kind getHasKindReference() const override;

  // not inherited
  void setDataSpecificationReferences(const basyx::specificCollection_t<aas::reference::IReference> & dataSpecificationReferences);
  void setIdShort(const std::string & idShort);
  void setCategory(const std::string & category);
  void setDescription(const aas::qualifier::impl::Description & description);
  void setParent(const std::shared_ptr<aas::reference::IReference> & parent);
  void setQualifier(const basyx::specificCollection_t<aas::qualifier::qualifiable::IConstraint> & qualifier);
  void setSemanticId(const std::shared_ptr<aas::reference::IReference> & semanticId);
  void setHasKindReference(const qualifier::haskind::Kind & hasKindReference);

private:
  basyx::specificCollection_t<aas::reference::IReference> dataSpecificationReferences;
  std::string idShort, category;
  qualifier::haskind::Kind hasKindReference;
  aas::qualifier::impl::Description description;
  std::shared_ptr<aas::reference::IReference> parent, semanticId;
  basyx::specificCollection_t<aas::qualifier::qualifiable::IConstraint> qualifier;
};

}
}
}
}
}

#endif
