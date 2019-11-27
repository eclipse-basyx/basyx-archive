/*
 * IConceptDescription.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_ICONCEPTDESCRIPTION_H_
#define BASYX_METAMODEL_ICONCEPTDESCRIPTION_H_

#include "aas/qualifier/IHasDataSpecification.h"
#include "aas/qualifier/IIdentifiable.h"

#include <string>
#include <vector>

namespace basyx {
namespace aas {
namespace parts {

class IConceptDescription : public qualifier::IHasDataSpecification, qualifier::IIdentifiable
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
