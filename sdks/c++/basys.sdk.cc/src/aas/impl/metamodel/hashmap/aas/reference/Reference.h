/*
 * Reference.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_REFERENCE_H_
#define AAS_IMPL_METAMODEL_HASHMAP_REFERENCE_H_

#include "aas/reference/IReference.h"
#include "basyx/object.h"

namespace basyx {
namespace aas {
namespace reference {
namespace impl {

class Reference : public reference::IReference
{
public:
	~Reference() = default;

  Reference();
  Reference(const basyx::specificCollection_t<IKey> & keys);
  Reference(const basyx::object::object_map_t & reference);

  // Inherited via IReference
  virtual basyx::specificCollection_t<IKey> getKeys() const override;
  virtual void setKeys(const basyx::specificCollection_t<IKey> & keys) override;

private:
  basyx::specificCollection_t<IKey> keys;
};

}
}
}
}

#endif
