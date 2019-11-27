/*
 * IReferable.h
 *
 *      Author: wendel
 */

#ifndef BASYX_METAMODEL_IREFERABLE_H_
#define BASYX_METAMODEL_IREFERABLE_H_

#include "submodel/api/reference/IReference.h"
#include "submodel/map/qualifier/Description.h"

#include <string>
#include <memory>

namespace basyx {
namespace aas {
namespace qualifier {

namespace ReferablePaths {
static constexpr char IDSHORT[] = "idShort";
static constexpr char CATEGORY[] = "category";
static constexpr char DESCRIPTION[] = "description";
static constexpr char PARENT[] = "parent";
}

class IReferable
{
public:
  virtual ~IReferable() = default;

  virtual std::string getIdShort() const = 0;
  virtual std::string getCategory() const = 0;
  virtual qualifier::impl::Description getDescription() const = 0;
  virtual std::shared_ptr<reference::IReference> getParent() const = 0;

};
}
}
}

#endif
