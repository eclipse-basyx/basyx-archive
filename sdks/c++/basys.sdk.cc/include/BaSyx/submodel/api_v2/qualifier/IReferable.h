#ifndef BASYX_SUBMODEL_API_V2_QUALIFIER_IREFERABLE_H
#define BASYX_SUBMODEL_API_V2_QUALIFIER_IREFERABLE_H

#include <BaSyx/submodel/api_v2/reference/IReference.h>
#include <BaSyx/submodel/api_v2/common/ILangStringSet.h>
#include <BaSyx/submodel/simple/reference/Reference.h>

#include <string>
#include <memory>

namespace basyx {
namespace submodel {
namespace api {

/**
 * Mandatory members:
 *    idShort
 *	
 */
class IReferable
{
public:
	virtual ~IReferable() = 0;

	virtual const std::string & getIdShort() const = 0;
	virtual const std::string * const getCategory() const = 0;
	virtual void setCategory(const std::string & category) = 0;
	virtual ILangStringSet & getDescription() = 0;
	virtual const ILangStringSet & getDescription() const = 0;
	virtual IReferable * getParent() const = 0;
	virtual void setParent(IReferable * parent) = 0;
	virtual simple::Reference getReference() const = 0;
	virtual simple::Key getKey(bool local = true) const = 0;

	virtual KeyElements getKeyElementType() const = 0;

	virtual KeyType getKeyType() const {
		return KeyType::IdShort;
	};
};

inline IReferable::~IReferable() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_QUALIFIER_IREFERABLE_H */
