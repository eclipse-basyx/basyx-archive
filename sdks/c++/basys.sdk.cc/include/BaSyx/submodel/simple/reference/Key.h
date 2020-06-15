#ifndef BASYX_SUBMODEL_SIMPLE_REFERENCE_KEY_H
#define BASYX_SUBMODEL_SIMPLE_REFERENCE_KEY_H

#include <BaSyx/submodel/enumerations/KeyType.h>
#include <BaSyx/submodel/enumerations/KeyElements.h>
#include <BaSyx/submodel/api_v2/reference/IKey.h>

#include <string>

namespace basyx {
namespace submodel {
namespace simple {

class Key : public api::IKey
{
private:
	KeyElements type;
	KeyType idType;
	bool local;
	std::string value;
public:
	Key(KeyElements type, bool local, KeyType idType, const std::string & value);
public:
	bool operator!=(const Key & other) const;
	inline bool operator==(const Key & other) const { return !(*this != other); };
public:
	KeyElements getType() const noexcept override;
	KeyType getIdType() const noexcept override;
	bool isLocal() const noexcept override;
	std::string getValue() const noexcept override;
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_REFERENCE_KEY_H */
