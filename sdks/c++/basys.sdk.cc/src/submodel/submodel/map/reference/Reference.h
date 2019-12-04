/*
 * Reference.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_REFERENCE_H_
#define AAS_IMPL_METAMODEL_HASHMAP_REFERENCE_H_

#include "submodel/api/reference/IReference.h"

#include "submodel/map/reference/Key.h"
#include "submodel/api/qualifier/IIdentifiable.h"

#include "basyx/object.h"

#include "vab/ElementMap.h"

namespace basyx {
namespace submodel {

class Reference : 
	public IReference,
	public vab::ElementMap
{
public:
	virtual ~Reference() = default;

	Reference();
	Reference(basyx::object obj);
	Reference(const basyx::specificCollection_t<IKey> & keys);
	Reference(const std::initializer_list<Key> keys);
  Reference(const std::shared_ptr<IReference> reference);
  Reference(const IReference &  reference);

	// Inherited via IReference
	virtual const basyx::specificCollection_t<IKey> getKeys() const override;
	virtual void setKeys(const basyx::specificCollection_t<IKey> & keys) override;
public:
	static Reference FromIdentifiable(const std::string & keyElementType, bool local, IIdentifiable & identifiable);
};

}
}

#endif
