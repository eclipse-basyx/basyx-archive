#include <BaSyx/submodel/simple/submodelelement/file/File.h>

using namespace basyx::submodel::simple;

File::File(const std::string & idShort, const std::string & mimeType)
	: SubmodelElement(idShort)
	, mimeType(mimeType)
{
};

const std::string File::getPath() const
{
	return this->path;
}

void File::setPath(const std::string & path)
{
	this->path = path;
}

const std::string File::getMimeType() const
{
	return this->mimeType;
}

void File::setMimeType(const std::string & mimeType)
{
	this->mimeType = mimeType;
}
