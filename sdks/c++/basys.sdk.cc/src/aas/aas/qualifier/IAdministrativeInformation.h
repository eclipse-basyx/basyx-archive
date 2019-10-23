/*
 * IAdministrativeInformation.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IAdministrativeInformation_H_
#define BASYX_METAMODEL_IAdministrativeInformation_H_


#include "IHasDataSpecification.h"

#include <string>

namespace basyx {
namespace aas {
namespace qualifier {

namespace administrationPaths {
  static constexpr char administrationPath[] = "administrationPath";
}

class IAdministrativeInformation : public IHasDataSpecification
{
public:
  virtual ~IAdministrativeInformation() = default;

  virtual std::string getVersion() const = 0;
  virtual std::string getRevision() const = 0;
};

}
}
}

#endif
