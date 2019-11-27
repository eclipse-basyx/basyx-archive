/*
 * Referable.h
 *
 *      Author: wendel
 */

#ifndef BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_REFERABLE_H_
#define BASYX_SUBMODEL_METAMODEL_MAP_QUALIFIER_REFERABLE_H_

#include "submodel/api/qualifier/IReferable.h"

#include "basyx/object.h"

#include "vab/ElementMap.h"

namespace basyx {
namespace submodel {

class Referable : 
	public virtual IReferable,
	public virtual vab::ElementMap
{
public:
  ~Referable() = default;

  // Constructors
  Referable();
  Referable(basyx::object & obj);
  Referable(const std::string & shortID, const std::string & category, const Description & description);

  // Inherited via IReferable
  virtual std::string getIdShort() const override;
  virtual std::string getCategory() const override;
  virtual Description getDescription() const override;
  virtual std::shared_ptr<IReference> getParent() const override;

  // not inherited
  void setIdShort(const std::string & shortID);
  void setCategory(const std::string & category);
  void setDescription(const Description & description);
  void setParent(const std::shared_ptr<IReference> & parentReference);
};

}
}

#endif
