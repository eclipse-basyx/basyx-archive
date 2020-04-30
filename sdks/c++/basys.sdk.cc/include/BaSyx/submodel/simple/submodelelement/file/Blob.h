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
	std::string data;
	std::string mimeType;
public:
	virtual ~Blob() = default;

	const std::string getValue() const override;
	void setValue(const std::string & value) override;

	const std::string getMimeType() const override;
	void setMimeType(const std::string & mimeType) override;
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_FILE_BLOB_H */
