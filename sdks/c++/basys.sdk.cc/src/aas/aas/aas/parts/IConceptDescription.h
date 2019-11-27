/*
 * IConceptDescription.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IConceptDescription_H_
#define BASYX_METAMODEL_IConceptDescription_H_

#include "submodel/api/qualifier/IHasDataSpecification.h"
#include "submodel/api/qualifier/IIdentifiable.h"

#include <string>
#include <vector>

namespace basyx {
namespace aas {

class IConceptDescription : 
	public virtual submodel::IHasDataSpecification,
	public virtual submodel::IIdentifiable
{
public:
	virtual ~IConceptDescription() = default;

	virtual std::vector<std::string> getIsCaseOf() const = 0;
	virtual void setIsCaseOf(const std::vector<std::string> & ref) = 0;
};

}
}

#endif
