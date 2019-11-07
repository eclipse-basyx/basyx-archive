/*
 * IReference.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IReference_H_
#define BASYX_METAMODEL_IReference_H_


#include "IKey.h"
#include "basyx/types.h"

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

namespace internalReferencePaths {

  static constexpr char KEY[] = "keys";
}

class IReference
{
public:
  virtual ~IReference() = default;

  virtual basyx::specificCollection_t<IKey> getKeys() const = 0;
  virtual void setKeys(const basyx::specificCollection_t<IKey> & keys) = 0;
};

}
}
}

#endif

