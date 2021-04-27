#include <BaSyx/submodel/simple/submodelelement/file/Blob.h>

using namespace basyx::submodel::simple;

Blob::Blob(const std::string & idShort, const std::string & mimeType)
	: SubmodelElement(idShort)
	, mimeType(mimeType)
{
};

const Blob::BlobType & Blob::getValue() const
{
	return this->data;
}

void Blob::setValue(const BlobType & value)
{
	this->data = value;
}

void Blob::setValue(BlobType && value)
{
	this->data = std::move(value);
}


const Blob::MimeType Blob::getMimeType() const
{
	return this->mimeType;
}

void Blob::setMimeType(const Blob::MimeType & mimeType)
{
	this->mimeType = mimeType;
}
