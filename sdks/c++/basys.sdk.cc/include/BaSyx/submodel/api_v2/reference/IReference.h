#ifndef BASYX_SUBMODEL_API_V2_REFERENCE_IREFERENCE_H
#define BASYX_SUBMODEL_API_V2_REFERENCE_IREFERENCE_H

#include <BaSyx/submodel/simple/reference/Key.h>

#include <vector>

namespace basyx {
namespace submodel {
namespace api {


class IReference
{
public:
	virtual ~IReference() = 0;

	virtual std::vector<simple::Key> getKeys() const = 0;
	virtual void addKey(const simple::Key & key) = 0;

	virtual bool empty() const = 0;
};

inline IReference::~IReference() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_REFERENCE_IREFERENCE_H */
