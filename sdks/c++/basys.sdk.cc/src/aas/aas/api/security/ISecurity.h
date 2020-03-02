/*
 * ISecurity.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_ISecurity_H_
#define BASYX_METAMODEL_ISecurity_H_

#include "BaSyx/types.h"
#include "BaSyx/object.h"

namespace basyx {
namespace aas {

class ISecurity
{
public:
  struct Path
  {
    static constexpr char AccessControlPolicyPoints[] = "accessControlPolicyPoints";
    static constexpr char TrustAnchor[] = "trustAnchor";
  };
public:
  virtual ~ISecurity() = default;

  virtual basyx::object getAccessControlPolicyPoints() const = 0;
  virtual basyx::object getTrustAnchor() const = 0;
};

}
}

#endif

