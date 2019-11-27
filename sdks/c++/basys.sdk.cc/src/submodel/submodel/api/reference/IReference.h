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
namespace submodel {

class IReference
{
public:
	struct Paths {
		static constexpr char DataSpecifications[] = "dataSpecificationReferences";
		static constexpr char Parents[] = "parentReferences";
		static constexpr char SemanticIds[] = "semanticIdReferences";
		static constexpr char Qualifiers[] = "qualifierReferences";
		static constexpr char ReferencePath[] = "semanticIdentifier";
		static constexpr char Key[] = "keys";
	};
public:
  virtual ~IReference() = default;

  virtual const basyx::specificCollection_t<IKey> getKeys() const = 0;
  virtual void setKeys(const basyx::specificCollection_t<IKey> & keys) = 0;
};

}
}

#endif

