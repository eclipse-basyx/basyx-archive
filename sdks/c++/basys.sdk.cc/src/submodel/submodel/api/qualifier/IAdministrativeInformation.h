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
namespace submodel {


class IAdministrativeInformation : 
  public virtual IHasDataSpecification
{
public:
struct Path {
	static constexpr char AdministrationPath[] = "administrationPath";
	static constexpr char Version[] = "version";
	static constexpr char Revision[] = "revision";
};
public:
  virtual ~IAdministrativeInformation() = default;

  virtual std::string getVersion() const = 0;
  virtual std::string getRevision() const = 0;
};

}
}

#endif
