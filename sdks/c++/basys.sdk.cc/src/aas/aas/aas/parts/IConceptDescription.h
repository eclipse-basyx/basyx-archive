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
namespace parts {

class IConceptDescription : virtual qualifier::IHasDataSpecification, qualifier::IIdentifiable
{
public:


  virtual ~IConceptDescription() = default;

  virtual std::vector<std::string> getisCaseOf() const = 0;
  virtual void setIscaseOf(const std::vector<std::string> & ref) = 0;
};

}
}
}

#endif
