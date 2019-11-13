/*
 * Referable.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_REFERABLE_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_REFERABLE_H_

#include "aas/qualifier/IReferable.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace qualifier {

class Referable : public aas::qualifier::IReferable
{
public:
  ~Referable() = default;

  // Constructors
  Referable();
  Referable(const std::string & shortID, const std::string & category, const aas::qualifier::impl::Description & description);

  // Inherited via IReferable
  virtual std::string getIdShort() const override;
  virtual std::string getCategory() const override;
  virtual aas::qualifier::impl::Description getDescription() const override;
  virtual std::shared_ptr<aas::reference::IReference> getParent() const override;

  // not inherited
  void setIdShort(const std::string & shortID);
  void setCategory(const std::string & category);
  void setDescription(const aas::qualifier::impl::Description & description);
  void setParent(const std::shared_ptr<aas::reference::IReference> & parentReference);

private:
  std::string idShort, category;
  aas::qualifier::impl::Description description;
  std::shared_ptr<aas::reference::IReference> parentReference;
};

}
}
}
}
}

#endif
