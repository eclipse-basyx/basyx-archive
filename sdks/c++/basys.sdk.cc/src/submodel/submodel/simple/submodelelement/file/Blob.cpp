#include <BaSyx/submodel/simple/submodelelement/file/Blob.h>

const std::string basyx::submodel::simple::Blob::getValue() const
{
	return this->data;
}

void basyx::submodel::simple::Blob::setValue(const std::string & value)
{
	this->data = value;
}

const std::string basyx::submodel::simple::Blob::getMimeType() const
{
	return this->mimeType;
}

void basyx::submodel::simple::Blob::setMimeType(const std::string & mimeType)
{
	this->mimeType = mimeType;
}
