#include <BaSyx/submodel/map_v2/dataspecification/DataSpecification.h>
#include <BaSyx/vab/ElementMap.h>

namespace basyx {
namespace submodel {
namespace map{

using namespace basyx::submodel::api;

DataSpecification::DataSpecification(const std::string & idShort, const simple::Identifier & identifier, std::unique_ptr<DataSpecificationContent> content)
  : Identifiable(idShort, identifier)
  , vab::ElementMap()
{
  this->setContent(std::move(content));
}

DataSpecification::DataSpecification(const std::string &idShort, const simple::Identifier &identifier)
    : Identifiable(idShort, identifier)
    , vab::ElementMap{}
{}

void DataSpecification::setContent(std::unique_ptr<DataSpecificationContent> dataSpecificationContent)
{
  this->content = std::move(dataSpecificationContent);
  auto element_map = dynamic_cast<vab::ElementMap*>(this->content.get());
  this->map.insertKey("dataSpecificationContent", element_map->getMap());
}

api::IDataSpecificationContent& DataSpecification::getContent()
{
  return *this->content;
}

}
}
}
