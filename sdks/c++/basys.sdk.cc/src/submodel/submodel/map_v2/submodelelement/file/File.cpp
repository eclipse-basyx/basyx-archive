#include <BaSyx/submodel/map_v2/submodelelement/file/File.h>


namespace basyx {
namespace submodel {
namespace map {

constexpr char File::Path::MimeType[];
constexpr char File::Path::Value[];

File::File(const std::string & idShort, const std::string & mimeType)
  : SubmodelElement(idShort)
  , vab::ElementMap{}
{
	this->map.insertKey(Path::MimeType, mimeType);
	this->map.insertKey(Path::Value, std::string(""));
}

File::File(basyx::object obj)
  : vab::ElementMap{}
  , SubmodelElement{obj}
{
  this->map.insertKey(Path::Value, obj.getProperty(Path::Value).GetStringContent());
  this->map.insertKey(Path::MimeType, obj.getProperty(Path::MimeType).GetStringContent());
}

File::File(const File & other)
  : SubmodelElement(other.getIdShort(), other.getKind())
  , vab::ElementMap(other.getMap())
{}

const PathType File::getPath() const
{
	return this->map.getProperty(Path::Value).Get<std::string&>();
}

void File::setPath(const PathType & path)
{
	this->map.insertKey(Path::Value, path);
}

const std::string File::getMimeType() const
{
	return this->map.getProperty(Path::MimeType).Get<std::string&>();
}

void File::setMimeType(const std::string & mimeType)
{
	this->map.insertKey(Path::MimeType, mimeType);
}

}
}
}
