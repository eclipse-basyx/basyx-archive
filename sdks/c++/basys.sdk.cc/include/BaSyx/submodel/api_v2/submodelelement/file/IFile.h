#ifndef BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_FILE_IFILE_H
#define BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_FILE_IFILE_H

#include <BaSyx/submodel/api_v2/submodelelement/IDataElement.h>
#include <BaSyx/submodel/api_v2/reference/IReference.h>

#include <BaSyx/shared/types.h>

namespace basyx {
namespace submodel {
namespace api {

class IFile : public virtual IDataElement
{
public:
	virtual ~IFile() = 0;

	virtual const std::string getPath() const = 0;
	virtual void setPath(const std::string & path) = 0;

	virtual const std::string getMimeType() const = 0;
	virtual void setMimeType(const std::string & mimeType) = 0;
};

inline IFile::~IFile() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_SUBMODELELEMENT_FILE_IFILE_H */
