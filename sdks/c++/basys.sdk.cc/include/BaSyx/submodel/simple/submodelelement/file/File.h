#ifndef BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_FILE_FILE_H
#define BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_FILE_FILE_H

#include <BaSyx/submodel/api_v2/submodelelement/file/IFile.h>
#include <BaSyx/submodel/simple/submodelelement/SubmodelElement.h>

#include <vector>
#include <cstdint>

namespace basyx {
namespace submodel {
namespace simple {
	
class File 
	: public virtual api::IFile
	, SubmodelElement
{
private:
	std::string path;
	std::string mimeType;
public:
	File(const std::string & idShort, const std::string & mimeType);
	virtual ~File() = default;

	const std::string getPath() const override;
	void setPath(const std::string & value) override;

	const std::string getMimeType() const override;
	void setMimeType(const std::string & mimeType) override;
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_SUBMODELELEMENT_FILE_FILE_H */
