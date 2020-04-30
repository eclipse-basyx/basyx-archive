#ifndef BASYX_SUBMODEL_SIMPLE_REFERENCE_REFERENCE_H
#define BASYX_SUBMODEL_SIMPLE_REFERENCE_REFERENCE_H

#include <BaSyx/submodel/api_v2/reference/IReference.h>
#include <BaSyx/submodel/api_v2/qualifier/IIdentifiable.h>

#include <BaSyx/submodel/simple/reference/Key.h>

#include <vector>

namespace basyx {
namespace submodel {
namespace simple {

class Reference : public api::IReference
{
private:
	std::vector<Key> keyList;
public:
	virtual ~Reference() = default;

	Reference() = default;

	Reference(const Reference & other) = default;
	Reference(Reference && other) noexcept = default;

	Reference & operator=(const Reference & other) = default;
	Reference & operator=(Reference && other) noexcept = default;

	Reference(const Key & key);
	Reference(const std::vector<Key> & keys);
	Reference(const std::initializer_list<Key> keys);

	const std::vector<Key> getKeys() const override;
	void addKey(const Key & key) override;
public:
	static Reference FromIdentifiable(KeyElements keyElementType, const api::IIdentifiable & identifiable);
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_REFERENCE_REFERENCE_H */
