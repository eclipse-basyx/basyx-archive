/*
 * Reference.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_REFERENCE_H_
#define AAS_IMPL_METAMODEL_HASHMAP_REFERENCE_H_

#include <BaSyx/submodel/api/reference/IReference.h>

#include <BaSyx/submodel/map/reference/Key.h>
#include <BaSyx/submodel/api/qualifier/IIdentifiable.h>

#include <BaSyx/shared/object.h>

#include <BaSyx/vab/ElementMap.h>

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
	Reference(const IReference & reference);

	// Inherited via IReference
	virtual const basyx::specificCollection_t<IKey> getKeys() const override;
	virtual void setKeys(const basyx::specificCollection_t<IKey> & keys) override;
public:
	static Reference FromIdentifiable(const std::string & keyElementType, bool local, const IIdentifiable & identifiable);
};

}
}

#endif
