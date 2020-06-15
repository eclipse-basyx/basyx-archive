#ifndef BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_FILE_BLOB_H
#define BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_FILE_BLOB_H

#include <BaSyx/submodel/api_v2/submodelelement/file/IBlob.h>
#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/map_v2/common/ModelType.h>

#include <vector>
#include <cstdint>

namespace basyx {
namespace submodel {
namespace map {
	
class Blob : 
	public virtual api::IBlob, 
	public SubmodelElement,
	public ModelType<ModelTypes::Blob>
{
private:
	std::string data;
	MimeType mimeType;
public:
	virtual ~Blob() = default;

	const BlobType getValue() const override;
	void setValue(const BlobType & value) override;

	const std::string getMimeType() const override;
	void setMimeType(const std::string & mimeType) override;
};

}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_SUBMODELELEMENT_FILE_BLOB_H */
