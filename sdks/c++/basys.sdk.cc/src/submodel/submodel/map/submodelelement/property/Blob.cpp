#include <BaSyx/submodel/map/submodelelement/property/Blob.h>

namespace basyx {
namespace submodel {

const std::string & Blob::getValue() const
{
	return map.getProperty(Blob::Path::Value).Get<std::string&>();
}

const std::string & Blob::getMimeType() const
{
	return map.getProperty(Blob::Path::MIMEType).Get<std::string&>();
}

void Blob::setValue(const std::string & bytes)
{
	this->map.insertKey(Blob::Path::Value, bytes);
}

void Blob::setValue(const char * c, std::size_t length)
{
// TODO: basyx::object for blob types
//	this->map.insertKey(Blob::Path::Value, basyx::object::make_blob)
}

void Blob::setMimeType(const std::string & mimeType)
{
	this->map.insertKey(IBlob::Path::MIMEType, mimeType, true);
}


}
};