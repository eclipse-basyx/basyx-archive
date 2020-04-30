#ifndef BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_FILE_FILE_H
#define BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_FILE_FILE_H

#include <BaSyx/submodel/api_v2/submodelelement/file/IFile.h>

#include <vector>
#include <cstdint>

namespace basyx {
namespace submodel {
namespace simple {
	
class File : public virtual api::IFile
{
private:
	std::string path;
	std::string mimeType;
public:
	virtual ~File() = default;

	const std::string getPath() const override;
	void setPath(const std::string & value) override;

	const std::string getMimeType() const override;
	void setMimeType(const std::string & mimeType) override;
};

}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_FILE_FILE_H */
