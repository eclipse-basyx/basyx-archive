/*
 * IReference.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IReference_H_
#define BASYX_METAMODEL_IReference_H_


#include "IKey.h"

#include <vector>

namespace basyx {
namespace aas {
namespace reference {

namespace paths {
  static constexpr char DATASPECIFICATIONS[] = "dataSpecificationReferences";
  static constexpr char PARENTS[] = "parentReferences";
  static constexpr char SEMANTICIDS[] = "semanticIdReferences";
  static constexpr char QUALIFIERS[] = "qualifierReferences";
  static constexpr char REFERENCEPATH[] = "semanticIdentifier";
}


class IReference
{
public:
  virtual ~IReference() = default;

  virtual std::vector<IKey> getKeys() const = 0;
  virtual void setKeys(const std::vector<IKey> & keys) = 0;
};

}
}
}

#endif

