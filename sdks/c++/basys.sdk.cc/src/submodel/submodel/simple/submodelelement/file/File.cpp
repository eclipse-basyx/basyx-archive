#include <basyx/submodel/simple/submodelelement/file/File.h>

const std::string basyx::submodel::simple::File::getPath() const
{
	return this->path;
}

void basyx::submodel::simple::File::setPath(const std::string & path)
{
	this->path = path;
}

const std::string basyx::submodel::simple::File::getMimeType() const
{
	return this->mimeType;
}

void basyx::submodel::simple::File::setMimeType(const std::string & mimeType)
{
	this->mimeType = mimeType;
}
