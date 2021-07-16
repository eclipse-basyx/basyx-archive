#ifndef BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_FILE_FILE_H
#define BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_FILE_FILE_H

#include <BaSyx/submodel/api_v2/submodelelement/file/IFile.h>
#include <BaSyx/submodel/map_v2/submodelelement/DataElement.h>
#include <BaSyx/submodel/map_v2/common/ModelType.h>

#include <vector>
#include <cstdint>

namespace basyx {
namespace submodel {
namespace map {
	
class File : 
	public virtual api::IFile, 
	public DataElement,
	public ModelType<ModelTypes::File>
{
public:
	struct Path {
		static constexpr char MimeType[] = "mimeType";
		static constexpr char Value[] = "value";
	};
public:
	File(const std::string & idShort, const std::string & mimeType);
	File(basyx::object);
	File(const File & other);

	virtual ~File() = default;

	const PathType getPath() const override;
	void setPath(const PathType & value) override;

	const std::string getMimeType() const override;
	void setMimeType(const std::string & mimeType) override;

	KeyElements getKeyElementType() const override { return KeyElements::File; };
};

}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_FILE_FILE_H */
