/*
 * Identifiable.cpp
 *
 *      Author: wendel
 */

#include "Identifiable.h"

#include "AdministrativeInformation.h"
#include "impl/metamodel/hashmap/aas/identifier/Identifier.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace qualifier {

using namespace aas::qualifier;
using namespace aas::identifier;

Identifiable::Identifiable() :
  administration {new aas::qualifier::impl::AdministrativeInformation},
  identification {new aas::identifier::impl::Identifier}
{}

Identifiable::Identifiable(const std::string & version, const std::string & revision, const std::string & idShort, 
    const std::string & category, const aas::qualifier::impl::Description & description, const std::string & idType, const std::string & id) :
  Referable {idShort, category, description},
  administration {new aas::qualifier::impl::AdministrativeInformation {version, revision} },
  identification {new aas::identifier::impl::Identifier {id, idType} }
{}

std::shared_ptr<IAdministrativeInformation> Identifiable::getAdministration() const
{
  return this->administration;
}

std::shared_ptr<IIdentifier> Identifiable::getIdentification() const
{
  return this->identification;
}

void Identifiable::setAdministration(const std::shared_ptr<aas::qualifier::IAdministrativeInformation> & administration)
{
  this->administration = administration;
}

void Identifiable::setIdentification(const std::shared_ptr<aas::identifier::IIdentifier> & identification)
{
  this->identification = identification;
}

}
}
}
}
}
