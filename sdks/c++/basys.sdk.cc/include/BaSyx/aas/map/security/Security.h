/*
 * Security.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_SECURITY_SECURITY_H_
#define AAS_IMPL_METAMODEL_HASHMAP_SECURITY_SECURITY_H_

#include <BaSyx/aas/api/security/ISecurity.h>

#include <BaSyx/shared/types.h>

#include <BaSyx/vab/ElementMap.h>

namespace basyx {
namespace aas {

class Security 
  : public ISecurity
  , public virtual vab::ElementMap
{
public:
  ~Security() = default;

  Security(ISecurity & other);
  Security(basyx::object obj);

  // Inherited via ISecurity
  virtual basyx::object getAccessControlPolicyPoints() const override;
  virtual basyx::object getTrustAnchor() const override;

  void setAccessControlPolicyPoints(const basyx::object & obj);
  void setTrustAnchor(const basyx::object & obj);
};

}
}

#endif
