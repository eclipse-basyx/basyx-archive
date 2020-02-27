#include <BaSyx/submodel/map/submodelelement/property/File.h>

namespace basyx {
namespace submodel {

const std::string & File::getValue() const
{
	return map.getProperty(IFile::Path::Value).Get<std::string&>();
}

const std::string & File::getMimeType() const
{
	return map.getProperty(IFile::Path::MIMEType).Get<std::string&>();
}

void File::setValue(const std::string & bytes)
{
	this->map.insertKey(IFile::Path::Value, bytes);
}

void File::setMimeType(const std::string & mimeType)
{
	this->map.insertKey(IFile::Path::MIMEType, mimeType, true);
}


}
};