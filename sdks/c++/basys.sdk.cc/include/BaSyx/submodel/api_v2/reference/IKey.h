#ifndef BASYX_SUBMODEL_API_V2_REFERENCE_IKEY_H
#define BASYX_SUBMODEL_API_V2_REFERENCE_IKEY_H

#include <string>

#include <BaSyx/submodel/enumerations/KeyType.h>
#include <BaSyx/submodel/enumerations/KeyElements.h>

namespace basyx {
namespace submodel {
namespace api {

class IKey
{
public:
  struct Path
  {
    static constexpr char Type[] = "type";
    static constexpr char Local[] = "local";
    static constexpr char Value[] = "value";
    static constexpr char IdType[] = "idType";
  };
public:
	virtual ~IKey() = 0;

	virtual KeyElements getType() const = 0;
	virtual bool isLocal() const = 0;
	virtual std::string getValue() const = 0;
	virtual KeyType getIdType() const = 0;
};

inline IKey::~IKey() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_REFERENCE_IKEY_H */
