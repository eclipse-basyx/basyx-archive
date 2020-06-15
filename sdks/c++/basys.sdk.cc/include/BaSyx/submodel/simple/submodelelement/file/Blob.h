#ifndef BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_FILE_BLOB_H
#define BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_FILE_BLOB_H

#include <BaSyx/submodel/api_v2/submodelelement/file/IBlob.h>

#include <vector>
#include <cstdint>

namespace basyx {
namespace submodel {
namespace simple {
	
class Blob : public virtual api::IBlob
{
private:
	BlobType data;
	MimeType mimeType;
public:
	virtual ~Blob() = default;

	const BlobType getValue() const override;
	void setValue(const BlobType & value) override;

	const MimeType getMimeType() const override;
	void setMimeType(const MimeType & mimeType) override;
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_FILE_BLOB_H */
