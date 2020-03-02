/*
 * IConceptDescription.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IConceptDescription_H_
#define BASYX_METAMODEL_IConceptDescription_H_

#include "BaSyx/submodel/api/qualifier/IHasDataSpecification.h"
#include "BaSyx/submodel/api/qualifier/IIdentifiable.h"

#include <string>
#include <vector>

namespace basyx {
namespace submodel {

class IConceptDescription : 
	public virtual submodel::IHasDataSpecification,
	public virtual submodel::IIdentifiable
{
public:
  struct Path
  {
    static constexpr char ModelType[] = "ConceptDescription";
    static constexpr char IsCaseOf[] = "isCaseOf";
  };

public:
	virtual ~IConceptDescription() = default;

	virtual basyx::specificCollection_t<submodel::IReference> getIsCaseOf() const = 0;
	virtual void setIsCaseOf(const basyx::specificCollection_t<submodel::IReference> & ref) = 0;
};

}
}

#endif
