#include <BaSyx/submodel/map_v2/submodelelement/file/Blob.h>


namespace basyx {
namespace submodel {
namespace map {

constexpr char Blob::constants::mimeType[];
constexpr char Blob::constants::value[];

Blob::Blob(const std::string & idShort, const std::string & mimeType)
  : SubmodelElement(idShort)
  , vab::ElementMap{}
{
	this->map.insertKey(constants::mimeType, mimeType);
	this->map.insertKey(constants::value, basyx::object::make_list<char>());
}

const Blob::BlobType & Blob::getValue() const
{
	auto & value = this->map.getProperty(constants::value).Get<std::vector<char>&>();
	return value;
};


void Blob::setValue(const Blob::BlobType & value)
{
	auto & blob = this->map.getProperty(constants::value).Get<std::vector<char>&>();
	blob = value;
};

void Blob::setValue(Blob::BlobType && value)
{
	auto & blob = this->map.getProperty(constants::value).Get<std::vector<char>&>();
	blob = std::move(value);
};

const std::string Blob::getMimeType() const
{
	return this->map.getProperty(constants::mimeType).Get<std::string&>();
};


void Blob::setMimeType(const std::string & mimeType)
{
	this->map.insertKey(constants::mimeType, mimeType);
};

}
}
}
