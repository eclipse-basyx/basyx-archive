#ifndef BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_FILE_IFILE_H
#define BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_FILE_IFILE_H

#include <BaSyx/submodel/api_v2/submodelelement/IDataElement.h>
#include <BaSyx/submodel/api_v2/reference/IReference.h>
#include <BaSyx/submodel/api_v2/submodelelement/file/IBlob.h>

#include <BaSyx/shared/types.h>

namespace basyx {
namespace submodel {

using PathType = std::string;

namespace api {

class IFile : public virtual IDataElement
{
public:
	virtual ~IFile() = 0;

	virtual const PathType getPath() const = 0;
	virtual void setPath(const PathType & path) = 0;

	virtual const MimeType getMimeType() const = 0;
	virtual void setMimeType(const MimeType & mimeType) = 0;

	virtual KeyElements getKeyElementType() const override { return KeyElements::File; };
};

inline IFile::~IFile() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_FILE_IFILE_H */
