#ifndef BASYX_SUBMODEL_SIMPLE_REFERENCE_REFERENCE_H
#define BASYX_SUBMODEL_SIMPLE_REFERENCE_REFERENCE_H

#include <BaSyx/submodel/api_v2/reference/IReference.h>

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

	Reference(const api::IReference & other);
	Reference(const Reference & other) = default;
	Reference(Reference && other) noexcept = default;

	Reference & operator=(const Reference & other) = default;
	Reference & operator=(Reference && other) noexcept = default;

	Reference(const Key & key);
	Reference(const std::vector<Key> & keys);
	Reference(std::vector<Key> && keys);
	Reference(const std::initializer_list<Key> keys);

	Reference() = default;
public:
	bool operator!=(const Reference & other) const;
	inline bool operator==(const Reference & other) const { return !(*this != other); };

	bool operator!=(const api::IReference & other) const;
	inline bool operator==(const api::IReference & other) const { return !(*this != other); };
public:
	std::vector<Key> getKeys() const override;
	void addKey(const Key & key) override;

	bool empty() const override;
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_REFERENCE_REFERENCE_H */
