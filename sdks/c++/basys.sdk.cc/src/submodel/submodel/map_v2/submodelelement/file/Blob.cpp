#include <BaSyx/submodel/map_v2/submodelelement/file/Blob.h>


namespace basyx {
namespace submodel {
namespace map {

constexpr char Blob::Path::mimeType[];
constexpr char Blob::Path::value[];

Blob::Blob(const std::string & idShort, const std::string & mimeType)
  : vab::ElementMap{}
  , DataElement(idShort)
{
	this->map.insertKey(Path::mimeType, mimeType);
	this->map.insertKey(Path::value, basyx::object::make_list<char>());
}

Blob::Blob(basyx::object obj)
  : vab::ElementMap{}
  , DataElement{obj}
{
  this->map.insertKey(Path::value, obj.getProperty(Path::value).Get<object::list_t<char>&>());
  this->map.insertKey(Path::mimeType, obj.getProperty(Path::mimeType).GetStringContent());
}

const Blob::BlobType & Blob::getValue() const
{
	auto & value = this->map.getProperty(Path::value).Get<std::vector<char>&>();
	return value;
}

void Blob::setValue(const Blob::BlobType & value)
{
	auto & blob = this->map.getProperty(Path::value).Get<std::vector<char>&>();
	blob = value;
}

void Blob::setValue(Blob::BlobType && value)
{
	auto & blob = this->map.getProperty(Path::value).Get<std::vector<char>&>();
	blob = std::move(value);
}

const std::string Blob::getMimeType() const
{
	return this->map.getProperty(Path::mimeType).Get<std::string&>();
}

void Blob::setMimeType(const std::string & mimeType)
{
	this->map.insertKey(Path::mimeType, mimeType);
}

}
}
}
