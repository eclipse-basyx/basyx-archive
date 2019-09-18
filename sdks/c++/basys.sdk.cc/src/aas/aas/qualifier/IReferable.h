/*
 * IReferable.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IREFERABLE_H_
#define BASYX_METAMODEL_IREFERABLE_H_


#include "aas/reference/IReference.h"

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

  virtual std::string getIdshort() const = 0;
  virtual void setIdshort(const std::string & idShort) = 0;

  virtual std::string getCategory() const = 0;
  virtual void setCategory(const std::string & category) = 0;

  virtual std::string getDescription() const = 0;
  virtual void setDescription(const std::string & description) = 0;

  virtual std::shared_ptr<IReference> getParent() const = 0;
  virtual void setParent(const std::shared_ptr<IReference> & obj) = 0;

};
}
}
}

#endif