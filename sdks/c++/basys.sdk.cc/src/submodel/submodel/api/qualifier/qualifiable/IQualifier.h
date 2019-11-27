/*
 * IQualifier.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IQualifier_H_
#define BASYX_METAMODEL_IQualifier_H_


#include "submodel/api/reference/IReference.h"
#include "submodel/api/qualifier/IHasSemantics.h"
#include "basyx/types.h"
#include "basyx/object.h"

#include <string>

namespace basyx {
namespace aas {
namespace qualifier {
namespace qualifiable {

class IQualifier : public IHasSemantics
{
public:
  virtual ~IQualifier() = default;

  virtual std::string getQualifierType() const = 0;
  virtual basyx::object getQualifierValue() const = 0;
  virtual std::shared_ptr<reference::IReference> getQualifierValueId() const = 0;
};

}
}
}
}

#endif

