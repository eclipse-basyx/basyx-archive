#ifndef BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_FILE_IBLOB_H
#define BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_FILE_IBLOB_H

#include <BaSyx/submodel/api_v2/submodelelement/IDataElement.h>
#include <BaSyx/submodel/api_v2/reference/IReference.h>

#include <BaSyx/shared/types.h>

#include <cstdint>
#include <vector>

namespace basyx {
namespace submodel {
namespace api {
	
class IBlob : public virtual IDataElement
{
public:
	virtual ~IBlob() = 0;

	virtual const std::string getValue() const = 0;
	virtual void setValue(const std::string & value) = 0;

	virtual const std::string getMimeType() const = 0;
	virtual void setMimeType(const std::string & mimeType) = 0;
};

inline IBlob::~IBlob() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_FILE_IBLOB_H */
