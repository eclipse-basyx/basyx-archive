#include <BaSyx/submodel/map_v2/dataspecification/DataSpecification.h>
#include <BaSyx/vab/ElementMap.h>

namespace basyx {
namespace submodel {
namespace map{

using namespace basyx::submodel::api;

DataSpecification::DataSpecification(const std::string & idShort, const simple::Identifier & identifier, std::unique_ptr<api::IDataSpecificationContent> content)
  : Identifiable(idShort, identifier)
  , vab::ElementMap()
{
  this->setContent(std::move(content));
}

void DataSpecification::setContent(std::unique_ptr<api::IDataSpecificationContent> content)
{
	// Assume content is map::IDataSpecificationContent
	auto map_content = dynamic_cast<map::DataSpecificationContent*>(content.get());
	if ( map_content != nullptr) {
		content.release();
		this->content = std::unique_ptr<map::DataSpecificationContent>(map_content);
		this->map.insertKey("dataSpecificationContent", map_content->getMap());
	};
}

api::IDataSpecificationContent& DataSpecification::getContent()
{
  return *this->content;
}

}
}
}
