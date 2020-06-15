#include <BaSyx/submodel/simple/submodelelement/file/Blob.h>

const basyx::submodel::BlobType basyx::submodel::simple::Blob::getValue() const
{
	return this->data;
}

void basyx::submodel::simple::Blob::setValue(const BlobType & value)
{
	this->data = value;
}

const basyx::submodel::MimeType basyx::submodel::simple::Blob::getMimeType() const
{
	return this->mimeType;
}

void basyx::submodel::simple::Blob::setMimeType(const MimeType & mimeType)
{
	this->mimeType = mimeType;
}
