/*
 * IDataSpecification.h
 *
 *      Author: wendel
 */

#ifndef BASYX_METAMODEL_IDATASPECIFICATION_H_
#define BASYX_METAMODEL_IDATASPECIFICATION_H_

#include <BaSyx/submodel/api/dataspecification/IDataSpecificationContent.h>

#include <BaSyx/submodel/api/qualifier/IIdentifiable.h>
#include <BaSyx/submodel/api/qualifier/IReferable.h>

#include <string>

namespace basyx {
namespace submodel {

class IDataSpecification
  : public virtual submodel::IIdentifiable
  , public virtual submodel::IReferable
{
public:
  struct Path
  {
    static constexpr char Content[] = "content";
  };
public:
  virtual ~IDataSpecification() = default;

  virtual std::shared_ptr<IDataSpecificationContent> getContent() const = 0;
};

}
}

#endif
