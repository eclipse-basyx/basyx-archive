#ifndef BASYX_SUBMODEL_API_V2_DATASPECIFICATION_IVALUELIST_H
#define BASYX_SUBMODEL_API_V2_DATASPECIFICATION_IVALUELIST_H

#include <BaSyx/submodel/simple/dataspecification/ValueReferencePair.h>

namespace basyx {
namespace submodel {
namespace api {

class IValueList
{
public:
	virtual ~IValueList() = 0;

	virtual void addValueReferencePair(const simple::ValueReferencePair & valueRefPair) = 0;
	virtual std::vector<simple::ValueReferencePair> getValueReferencePairs() = 0;
};

inline IValueList::~IValueList() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_DATASPECIFICATION_IVALUELIST_H */
