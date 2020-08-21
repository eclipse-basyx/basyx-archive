#ifndef BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_FILE_IBLOB_H
#define BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_FILE_IBLOB_H

#include <BaSyx/submodel/api_v2/submodelelement/IDataElement.h>
#include <BaSyx/submodel/api_v2/reference/IReference.h>

#include <BaSyx/shared/types.h>

#include <cstdint>
#include <vector>

namespace basyx {
namespace submodel {

using MimeType = std::string;
using BlobType = unsigned char *;

namespace api {
	
class IBlob : public virtual IDataElement
{
public:
	virtual ~IBlob() = 0;

	virtual const BlobType getValue() const = 0;
	virtual void setValue(const BlobType & value) = 0;

	virtual const MimeType getMimeType() const = 0;
	virtual void setMimeType(const MimeType & mimeType) = 0;

	virtual KeyElements getKeyElementType() const override { return KeyElements::Blob; };
};

inline IBlob::~IBlob() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_FILE_IBLOB_H */
