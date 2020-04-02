/*
 * Security.cpp
 *
 *      Author: wendel
 */

#include "BaSyx/aas/map/security/Security.h"

namespace basyx {
namespace aas {

Security::Security(ISecurity & other)
  : vab::ElementMap()
{}

Security::Security(basyx::object obj)
  : vab::ElementMap(obj)
{}

basyx::object Security::getAccessControlPolicyPoints() const
{
  return this->map.getProperty(ISecurity::Path::AccessControlPolicyPoints);
}

basyx::object Security::getTrustAnchor() const
{
  return this->map.getProperty(ISecurity::Path::TrustAnchor);
}

void Security::setAccessControlPolicyPoints(const basyx::object & obj)
{
  this->map.insertKey(ISecurity::Path::AccessControlPolicyPoints, obj);
}

void Security::setTrustAnchor(const basyx::object & obj)
{
  this->map.insertKey(ISecurity::Path::TrustAnchor, obj);
}

}
}