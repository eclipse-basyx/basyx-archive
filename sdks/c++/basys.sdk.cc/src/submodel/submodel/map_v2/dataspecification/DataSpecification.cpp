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
  DataSpecificationContent* dataSpecificationContent;
  if (content == nullptr)
    dataSpecificationContent = new DataSpecificationContent();
  else
  {
    dataSpecificationContent = dynamic_cast<DataSpecificationContent*>(content.release());
    if (!dataSpecificationContent)
      std::__throw_bad_cast();
  }
  this->content = dataSpecificationContent;
  this->map.insertKey("dataSpecificationContent", dataSpecificationContent->getMap());
}

api::IDataSpecificationContent& DataSpecification::getContent()
{
  return *this->content;
}

}
}
}
