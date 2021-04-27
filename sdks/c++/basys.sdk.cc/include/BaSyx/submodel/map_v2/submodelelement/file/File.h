#ifndef BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_FILE_FILE_H
#define BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_FILE_FILE_H

#include <BaSyx/submodel/api_v2/submodelelement/file/IFile.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/map_v2/common/ModelType.h>

#include <vector>
#include <cstdint>

namespace basyx {
namespace submodel {
namespace map {
	
class File : 
	public virtual api::IFile, 
	public SubmodelElement,
	public ModelType<ModelTypes::File>
{
public:
	struct Path {
		static constexpr char MimeType[] = "mimeType";
		static constexpr char Value[] = "value";
	};
private:
	std::string path;
	std::string mimeType;
public:
	File(const std::string & idShort, const std::string & mimeType);
	File(const File & other);

	virtual ~File() = default;

	const std::string getPath() const override;
	void setPath(const std::string & value) override;

	const std::string getMimeType() const override;
	void setMimeType(const std::string & mimeType) override;

	KeyElements getKeyElementType() const override { return KeyElements::File; };
};

}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_FILE_FILE_H */
